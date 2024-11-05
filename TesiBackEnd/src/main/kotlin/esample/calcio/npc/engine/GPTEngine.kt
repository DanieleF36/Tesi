package esample.calcio.npc.engine

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.event.LocalEvent
import conceptualMap2.exceptions.NPCNotStartedException
import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC
import conceptualMap2.npc.NPCEngine
import conceptualMap2.npc.knowledge.Knowledge
import conceptualMap2.npc.Personality
import esample.calcio.conceptualMap.CommonThoughtImpl
import esample.calcio.event.impl.FootballEI
import esample.calcio.event.impl.FootballET
import esample.calcio.npc.footballer.personality.FootballerPersonality
import esample.calcio.npc.footballer.personality.SimpleMood
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import org.w3c.dom.Document
import java.beans.Encoder
import java.io.StringWriter
import java.util.concurrent.TimeUnit
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


class GPTEngine: NPCEngine {
    private val URL = "https://api.openai.com/v1/chat/completions"
    private val OPENAI_API_KEY = "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"
    private val messages = mutableListOf<Message>()
    private var started = false
    private val events = mutableListOf<Event>()
    private var npc: NPC? = null

    private fun readFile(filePath: String): String {
        return File(filePath).readText()
    }

    private fun sendRequest(msg: List<Message>, model: String = "gpt-4o-mini"): String{
        try{
            val client = OkHttpClient().newBuilder().readTimeout(30, TimeUnit.SECONDS).build()
            val mediaType = "application/json".toMediaType()
            val body = Json.encodeToString(PostBody("gpt-4o-mini", msg)).toRequestBody(mediaType)
            val request = Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer $OPENAI_API_KEY")
                .build()

            val res = client.newCall(request).execute()

            if(!res.isSuccessful){
                throw RuntimeException("Error with GPTAPI ${res.message}")
            } else {
                val responseBody = res.body?.string()
                val json = Json {
                    ignoreUnknownKeys = true
                }
                return json.decodeFromString<ApiResponse>(responseBody!!).choices[0].message.content
            }
        } catch (e: Exception) {
            throw RuntimeException("Error with GPTAPI $e")
        }
    }

    private fun sendSingleText(input: String, role: Role = Role.user): String = sendRequest(listOf(Message(role ,input)))

    private fun removeOutsideBraces(input: String): String {
        val result = StringBuilder() // Usando StringBuilder per costruire la nuova stringa
        var insideBraces = false // Flag per tenere traccia se siamo dentro le parentesi graffe

        for (char in input) {
            when {
                char == '{' -> {
                    insideBraces = true // Iniziamo a raccogliere caratteri
                    result.append(char) // Aggiungiamo '{'
                }
                char == '}' -> {
                    insideBraces = false // Uscendo dalle parentesi graffe
                    result.append(char) // Aggiungiamo '}'
                }
                insideBraces -> {
                    result.append(char) // Aggiungiamo solo se siamo dentro le parentesi
                }
            }
        }

        return result.toString() // Restituiamo la stringa risultante
    }

    private fun generateNameAndAge(): Pair<String, Int> {
        val input = "generami un nome, cognome e un età di una persona e restituiscimi una stringa così formattata: nome cognome, età. Un esempio è Lorenzo Guarnieri, 30"
        return Pair(input.substringBefore(","), input.substringAfter(",").toInt())
    }

    private fun generateStory(name: String, age: Int, context: Knowledge, groupName: String, groupDescription: String): String {
        val input = "Sapendo che il mondo di gioco è descritto da questo contesto ${context.toMap()}, e sapendo che il gruppo a cui appartiene questo personaggio è $groupName, "+
                    "che i personaggi che appartengono a questo gruppo sono così descritti: \"$groupDescription\", "+
                    "creami una storia di questo personaggio,che si chiama $name ed ha $age anni e restituiscimi solo quella e nient'altro"
        return sendSingleText(input)
    }

    private fun generatePersonality(context: Knowledge, groupName: String, groupDescription: String, story: String): Personality {
        val input = "Sapendo che il mondo di gioco è descritto da questo contesto ${context.toMap()}, e sapendo che il gruppo a cui appartiene questo personaggio è $groupName "+
                ", che i personaggi che appartengono a questo gruppo sono così descritti: \"$groupDescription\" e che la storia del giocatore è \"$story\", creami una personalità "+
                "di questo personaggio basandoti su questo json schema: "+readFile("C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\npc\\jsonSchema\\footballerPersonality.json")+" senza aggiungere nessun'altra parola"
        val json = removeOutsideBraces(sendSingleText(input))
        return Json.decodeFromString<FootballerPersonality>(json)
    }

    private fun generateMood(context: Knowledge, groupName: String, groupDescription: String, story: String, personality: Personality, events: List<Event>): Mood {
        val input = "Sapendo che il mondo di gioco è descritto da questo contesto ${context.toMap()}, e sapendo che il gruppo a cui appartiene questo personaggio è $groupName "+
                ", che i personaggi che appartengono a questo gruppo sono così descritti: \"$groupDescription\", che la storia del giocatore è \"$story\", che la sua personalità "+
                "è descritta da questo oggetto: \"${personality.toMap()}\" e che gli eventi accaduti nel corso del tempo sono questo così descritti \"$events\", creami un mood di quel momento "+
                "di questo personaggio, che è un oggetto json che è descritto dallo schema: "+readFile("C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\npc\\jsonSchema\\simpleMood.json")+
                ". Senza aggiungere nient'altro, voglio solo l'oggetto json: {..} "
        val json = removeOutsideBraces(sendSingleText(input))
        //println("MOOD: $json")
        return Json.decodeFromString<SimpleMood>(json)
    }

    private fun generateThoughtOnPlayer(eventHistory: List<Event>, commonThought: CommonThought): CommonThought {
        val input = "sapendo che il pensiero comune sul giocatore da parte del gruppo è il seguente: $commonThought, e che la lista degli eventi generati del giocatore è : $eventHistory, "+
                    "generami un pensiero del personaggio sul giocatore che segue in formato json, descritto da: {_affection: {type: \"float\", min=-1, max=1}, _stress: {type: \"float\", min=-1, max=1}, _anger: {type: \"float\", min=-1, max=1}}"+
                    "Senza aggiungere nient'altro, voglio solo l'oggetto json: {..}"
        val json = removeOutsideBraces(sendSingleText(input))
        //println("MOOD: $json")
        return Json.decodeFromString<CommonThoughtImpl>(json)
    }

    override fun startNPC(npc: NPC) {
        if(npc.name == null || npc.age == null) {
            val ret = generateNameAndAge()
            if(npc.name == null) npc.setName(ret.first)
            if(npc.age == null) npc.setAge(ret.second)
        }
        if(npc.story == null) npc.setStory(generateStory(npc.name!!, npc.age!!, npc.context, npc.group.name, npc.group.description))
        if(npc.personality == null) npc.setPersonality(generatePersonality(npc.context, npc.group.name, npc.group.description, npc.story!!))
        if(npc.mood == null) npc.setMood(generateMood(npc.context, npc.group.name, npc.group.description, npc.story!!, npc.personality!!, npc.group.getEventHistory()))
        if(npc.thoughtOnPlayer == null) npc.setThoughtOnPlayer(generateThoughtOnPlayer(npc.group.getEventHistory(), npc.group.commonThought))

        val map = mutableMapOf<String, Any>()
        val character = mutableMapOf<String, Any>()
        character["details"] = mapOf(
            "name" to npc.name,
            "age" to npc.age,
            "group" to mapOf("name" to npc.group.name, "description" to npc.group.description),
            "personalStory" to npc.story,
        )
        character["tasks"] = npc.tasks.map { task -> task.toMap() }
        character["personality"] = npc.personality!!.toMap()
        character["mood"] = npc.mood!!.toMap()
        character["thoughtOnPlayer"] = npc.thoughtOnPlayer!!.toMap()
        map["character"] = character
        map["context"] = npc.context.toMap()
        map["events"] = events.map { event -> event.toMap() }
        map["prompt"] = "Da qui in avanti tu interpreterai il personaggio descritto in character, che vive in un mondo definito in context e deve non solo reagire, coerentemente alla sua personalità, a tutti gli eventi ma anche parlarne pro attivamente in base alla tipologia e all'importanza e risponderai a me che sono l'allenatore della squadra"
        val comments = mapOf<String, String>(
            "tasks" to "i task definiscono i compiti dell'NPC che dovrà svolgere",
            "personality" to "su una scala da 1 a 10",
            "mood" to "su una scala da 0 a 1",
            "group" to "il group a cui appartiene un NPC è una astrazione di un gruppo sociale, ad esempio: Calciatori, Staff Tecnico, Dirigenti e Staff di Supporto",
            "personalStory" to "qui si va a descrivere la storia dell'NPC",
            "globalContext" to "global definisce il contesto globale del mondo del calcio",
            "localContext" to "local definisce il contesto della squadra del giocatore",
            "actualContext" to "actual definisce il contesto in cui il giocatore inizia a giocare",
            "metaContext" to "queste sono delle informazioni sul role play che tu devi rispettare sempre e utilizzare, se ad esempio c'è scritto che l'utente è l'allenatore allora ogni volta che stai parlando con lui e trovi la scritta allenatore dentro la descrizione di un evento allora è stato l'utente a generare l'evento ",
            "events" to "Interpreta la descrizione e, ad esempio, se un evento riguarda l'allenatore e stai parlando con l'allenatore comportati di conseguenza e fai domande sull'evento",
            "action" to "Sono delle decisioni o azioni che si deve prendere in determinati casi. Esempio: se il presidente ha un obiettivo a breve termine che deve decidere se esonerare o meno l'allenatore dopo una partita allora si deve seguire il valore di action, se c'è che viene esonerato dopo sconfitta allora il presidente lo farà",
            "thoughtOnPlayer" to "è il pensiero di questo NPC sul giocatore"
        )
        val initialize = createXml("request", map, comments)
        messages.add(Message(Role.system, initialize))
        started = true
        this.events.addAll(events)

        this.npc = npc
    }

    private fun createXml(rootTag: String, map: Map<String, Any>, comments: Map<String, String>): String {
        val document: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
        val root = document.createElement(rootTag)
        document.appendChild(root)

        fun appendMapToXml(map: Map<String, Any>, parent: org.w3c.dom.Element) {
            for ((key, value) in map) {
                // Aggiungere commento sopra il tag se presente
                comments[key]?.let {
                    val comment = document.createComment(it)
                    parent.appendChild(comment)
                }
                val element = document.createElement(key)

                when (value) {
                    is String -> element.appendChild(document.createTextNode(value))
                    is Map<*, *> -> {
                        appendMapToXml(value as Map<String, Any>, element) // Ricorsione per mappe
                    }
                    is List<*> -> {
                        for (item in value) {
                            if (item is Map<*, *>) {
                                val listElement = document.createElement(key)
                                appendMapToXml(item as Map<String, Any>, listElement)
                                element.appendChild(listElement)
                            } else {
                                val listItem = document.createElement(key)
                                listItem.appendChild(document.createTextNode(item.toString()))
                                element.appendChild(listItem)
                            }
                        }
                    }
                    else -> {
                        element.appendChild(document.createTextNode(value.toString()))
                    }
                }

                parent.appendChild(element)
            }
        }

        appendMapToXml(map, root)

        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        val writer = StringWriter()
        transformer.transform(DOMSource(document), StreamResult(writer))

        return writer.toString()

    }

    override fun generateEvent(): LocalEvent {
        val msg = Message(
            Role.system,
            """
             Sei un analista di conversazioni sportive. 
             Analizza la seguente conversazione e restituisci un JSON con i seguenti campi: 
            'evento', 'importanza', 'felicità', 'rabbia', 'stress' e 'description'. 
             L'evento deve essere scelto tra: Fiducia o Incoraggiamento; Perdita di fiducia o Autostima; Critica, Rabbia; Frustazione; 
             L'importanza deve essere scelta tra: Banale, Normale, Importante, Cruciale. 
             I valori di 'felicità', 'rabbia' e 'stress' devono rispettare i seguenti vincoli numerici: 
             ogni valore deve essere compreso tra -3.0 e 3.0. 
             La somma dei valori assoluti di 'felicità', 'rabbia' e 'stress' deve rispettare i seguenti limiti: 
             se l'importanza è 'Banale', la somma deve essere ≤ 2; 
             se l'importanza è 'Normale', la somma deve essere tra > 1 e ≤ 4.5; 
             se l'importanza è 'Importante', la somma deve essere tra > 4.5 e ≤ 7; 
             se l'importanza è 'Cruciale', la somma deve essere tra > 7 e ≤ 9. 
             Genera sempre una breve descrizione dell'evento. 
             Inoltre, se il 'sender' è l'allenatore e fa un commento negativo su un membro della squadra elencato nel Local Context, 
             includi 'Commento Negativo su Compagno: nome' nella descrizione e il è nome quello del compagno. "
             Restituisci solo il JSON come risultato."
            """.trimIndent()
        )
        val messagesList = listOf(msg)+messages.filter { it.role == Role.user || it.role == Role.assistant }
        val map = npc!!.context.toMap().toMutableMap()
        val dm = mutableMapOf<String, Any>()
        messagesList.forEach{ dm["Sender role=${if(it.role == Role.user) "Allenatore" else npc!!.name}"] = it.content}
        map["dialog"] = dm
        val m = Message(Role.user, createXml("Conversation", map, mapOf()))
        val event = sendRequest(listOf(msg, m), "gpt-4")
        val json = Json {
            serializersModule = SerializersModule {
                polymorphic(EventType::class) {
                    subclass(FootballET::class, FootballETSerializer)
                }
                polymorphic(EventImportance::class){
                    subclass(FootballEI::class, FootballEISerializer)
                }
            }
        }

        return json.decodeFromString(event)
    }

    private object FootballETSerializer : KSerializer<FootballET> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("FootballET", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): FootballET {
            return when(decoder.decodeString()){
                "Critica" -> FootballET.CRITICA
                "Rabbia" -> FootballET.RABBIA
                "Frustazione" -> FootballET.FRUSTAZIONE
                "Perdita di fiducia o Autostima" -> FootballET.SFIDUCIA
                "Fiducia o Incoraggiamento" -> FootballET.FIDUCIA
                else -> FootballET.NESSUNA
            }
        }
        override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: FootballET) {
            encoder.encodeString(value.name)
        }
    }
    private object FootballEISerializer: KSerializer<FootballEI>{
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("FootballEI", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): FootballEI {
            return when(decoder.decodeString()){
                "Banale" -> FootballEI.BANALE
                "Normale" -> FootballEI.NORMALE
                "Importante" -> FootballEI.IMPORTANTE
                else -> FootballEI.CRUCIALE
            }
        }

        override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: FootballEI) {
            encoder.encodeString(value.name)
        }

    }
    override fun talk(input: String): String {
        if (!started)
            throw NPCNotStartedException()
        messages.add(Message(Role.user,input))
        val ret = sendRequest(messages)
        messages.add(Message(Role.assistant, ret))
        return ret
    }

    override fun addDetails(detail: Map<String, Any>, comments: Map<String, String>) {
        val map = detail.toMutableMap()
        map["prompt"] = "Ci sono alcune informazioni da aggiungere alla conoscenza di questo personaggio"
        messages.add(Message(Role.system, createXml("request", detail, comments)))
    }

    override fun receiveEvent(event: Event, newMood: Mood, thoughtOnPlayer: CommonThought) {
        messages.add(Message(Role.system, "Un nuovo evento, così descritto: \"${event.toMap()}\", il pensiero sul giocatore è così cambiato: \"$thoughtOnPlayer\"  e lui deve comportarsi coerentemente al suo nuovo mood ${newMood.toMap()} e alla sua personalità in risposta ad esso"))
    }
}
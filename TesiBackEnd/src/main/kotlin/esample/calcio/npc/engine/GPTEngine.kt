package esample.calcio.npc.engine

import conceptualMap2.clock.Clock
import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.event.LocalEvent
import conceptualMap2.exceptions.NPCNotStartedException
import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC
import conceptualMap2.npc.NPCEngine
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
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import org.w3c.dom.Document
import java.io.StringWriter
import java.util.concurrent.TimeUnit
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


@Suppress("UNCHECKED_CAST")
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
            val body = Json.encodeToString(PostBody(model, msg)).toRequestBody(mediaType)
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

    private fun generateStory(characterInfo: Map<String, Any>, comments: Map<String, String>): String {
        val m = characterInfo.toMutableMap()
        m["prompt"] = "date tutte queste informazioni riguardanti il personaggio generami una sua storia personale e restituiscimi solo quella, senza aggiungere altro"
        return sendSingleText(createXml("request",m, comments))
    }

    private fun generatePersonality(characterInfo: Map<String, Any>, comments: Map<String, String>): Personality {
        val m = characterInfo.toMutableMap()
        m["prompt"] = "date tutte queste informazioni riguardanti il personaggio generami una sua personalità e restituisci solo il jason che è compliant a questo schema "+readFile("..\\jsonSchema\\footballerPersonality.json")

        val json = removeOutsideBraces(sendSingleText(createXml("request",m, comments)))
        return Json.decodeFromString<FootballerPersonality>(json)
    }

    private fun generateMood(characterInfo: Map<String, Any>, comments: Map<String, String>): Mood {
        val m = characterInfo.toMutableMap()
        m["prompt"] = "date tutte queste informazioni riguardanti il personaggio generami una suo mood e restituisci solo il jason che è compliant a questo schema "+readFile("..\\jsonSchema\\simpleMood.json")

        val json = removeOutsideBraces(sendSingleText(createXml("request", m, comments)))
        //println("MOOD: $json")
        return Json.decodeFromString<SimpleMood>(json)
    }

    private fun generateThoughtOnPlayer(eventHistory: List<Event>, commonThought: CommonThought): CommonThought {
        val input = """
            <request>
                <commonThought>${commonThought.toMap()}</commonThought>
                <eventHistory>${eventHistory.map { it.toMap() }}</eventHistory>
                <responseFormat>
                    <json>
                        <field name="_respect" type="float" min="-1" max="1"/>
                        <field name="_affection" type="float" min="-1" max="1"/>
                        <field name="_anger" type="float" min="-1" max="1"/>
                    </json>
                </responseFormat>
            </request>

        """.trimIndent()
        val json = removeOutsideBraces(sendSingleText(input))
        //println("MOOD: $json")
        return Json.decodeFromString<CommonThoughtImpl>(json)
    }

    override fun startNPC(map: MutableMap<String, Any>, comments: Map<String, String>, npc: NPC) {
        val character: MutableMap<String, Any?> = map["character"] as MutableMap<String, Any?>
        val d = (character["details"]as MutableMap<String, Any?>)
        if(d["name"] == null || d["age"] == null) {
            val ret = generateNameAndAge()
            if(d["name"] == null){ d["name"] = ret.first; npc.setName(ret.first)}
            if(d["age"] == null) {d["age"] = ret.second; npc.setAge(ret.second)}
        }
        if(d["personalStory"] == null) {
            val story = generateStory(map, comments)
            d["personalStory"] = story
            npc.setStory(story)
        }
        if(character["personality"] == null){
            val p = generatePersonality(map, comments)
            npc.setPersonality(p)
            character["personality"] = p

        }
        if(character["mood"] == null) {
            val m = generateMood(map, comments)
            npc.setMood(m)
            character["mood"] = m
        }
        if(character["thoughtOnPlayer"] == null) {
            val t = generateThoughtOnPlayer(npc.group.getEventHistory(), npc.group.commonThought)
            npc.setThoughtOnPlayer(t)
            character["thoughtOnPlayer"] = t
        }

        map["prompt"] = "Da qui in avanti tu interpreterai il personaggio descritto in character, che vive in un mondo definito in context e deve non solo reagire, coerentemente alla sua personalità, a tutti gli eventi ma anche parlarne pro attivamente in base alla tipologia e all'importanza e risponderai a me che sono l'allenatore della squadra"

        val initialize = createXml("request", map, comments)
        messages.add(Message(Role.system, initialize))
        started = true
        this.events.addAll(events)
        this.npc = npc
    }
    //TODO migliorare
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
            'evento', 'importanza', '_satisfaction', '_anger', '_stress' e 'description'. 
             L'evento deve essere scelto tra: Fiducia o Incoraggiamento; Perdita di fiducia o Autostima; Critica, Rabbia; Frustazione; 
             L'importanza deve essere scelta tra: Banale, Normale, Importante, Cruciale. 
             I valori di '_satisfaction', '_anger' e '_stress' devono rispettare i seguenti vincoli numerici: 
             ogni valore deve essere compreso tra -3.0 e 3.0. 
             La somma dei valori assoluti di '_satisfaction', '_anger' e 'stress' deve rispettare i seguenti limiti: 
             se l'importanza è 'Banale', la somma deve essere ≤ 2; 
             se l'importanza è 'Normale', la somma deve essere tra > 1 e ≤ 4.5; 
             se l'importanza è 'Importante', la somma deve essere tra > 4.5 e ≤ 7; 
             se l'importanza è 'Cruciale', la somma deve essere tra > 7 e ≤ 9. 
             Genera sempre una breve descrizione dell'evento. 
             Inoltre, se il 'sender' è l'allenatore e fa un commento negativo su un membro della squadra elencato nel Local Context, 
             includi 'Commento Negativo su Compagno: nome' nella descrizione e il è nome quello del compagno. 
             Restituisci solo il JSON come risultato.
            """.trimIndent()
        )
        val messagesList = listOf(msg)+messages.filter { it.role == Role.user || it.role == Role.assistant }
        val map = npc!!.context.toMap().toMutableMap()
        val dm = mutableMapOf<String, Any>()
        messagesList.forEach{ dm["Sender role=${if(it.role == Role.user) "Allenatore" else npc!!.name}"] = it.content}
        map["dialog"] = dm
        val m = Message(Role.user, createXml("Conversation", map, mapOf()))
        val event = sendRequest(listOf(msg, m), "gpt-4")
        val obj = Json.parseToJsonElement(event).jsonObject
        return LocalEvent(
            type = convertType(obj["type"]!!.jsonPrimitive.content),
            importance = convertImportance(obj["importance"]!!.jsonPrimitive.content),
            statistic = SimpleMood(obj["_satisfaction"]!!.jsonPrimitive.float, obj["_stress"]!!.jsonPrimitive.float, obj["_anger"]!!.jsonPrimitive.float),
            description = obj["description"]!!.jsonPrimitive.content,
            generatedTime = Clock.getCurrentDateTime(),
            personGenerated = npc!!
        )
    }

    override fun generateRandomEvent(map: MutableMap<String, Any>, comments: Map<String, String>): LocalEvent {
        map["eventGeneration"] = """
             Sei un analista di conversazioni sportive. 
             Analizza la seguente conversazione e restituisci un JSON con i seguenti campi: 
            'evento', 'importanza', '_satisfaction', '_anger', '_stress' e 'description'. 
             L'evento deve essere scelto tra: Fiducia o Incoraggiamento; Perdita di fiducia o Autostima; Critica, Rabbia; Frustazione; 
             L'importanza deve essere scelta tra: Banale, Normale, Importante, Cruciale. 
             I valori di '_satisfaction', '_anger' e '_stress' devono rispettare i seguenti vincoli numerici: 
             ogni valore deve essere compreso tra -3.0 e 3.0. 
             La somma dei valori assoluti di '_satisfaction', '_anger' e 'stress' deve rispettare i seguenti limiti: 
             se l'importanza è 'Banale', la somma deve essere ≤ 2; 
             se l'importanza è 'Normale', la somma deve essere tra > 1 e ≤ 4.5; 
             se l'importanza è 'Importante', la somma deve essere tra > 4.5 e ≤ 7; 
             se l'importanza è 'Cruciale', la somma deve essere tra > 7 e ≤ 9. 
             Genera sempre una breve descrizione dell'evento. 
             Inoltre, se il 'sender' è l'allenatore e fa un commento negativo su un membro della squadra elencato nel Local Context, 
             includi 'Commento Negativo su Compagno: nome' nella descrizione e il è nome quello del compagno. 
             Restituisci solo il JSON come risultato.
        """.trimIndent()
        map["prompt"] = "Genera un evento che coinvolga character1 e character2. L'evento dovrebbe riflettere il loro rapporto professionale e personale, descritto in thoughtsOnOthers, tenendo conto delle loro personalità e del contesto attuale della squadra. Scegli un tipo di evento da quelli elencati (Fiducia, Incoraggiamento, Critica, etc.) che meglio si adatta alla situazione descritta nel contesto attuale e passato. L'evento deve avere un impatto emotivo significativo, con una descrizione che evidenzia le immediate conseguenze e le potenziali ramificazioni future."
        val event = sendRequest(listOf(Message(Role.user, createXml("request", map, comments))), "gpt-4")
        val obj = Json.parseToJsonElement(event).jsonObject
        return LocalEvent(
            type = convertType(obj["type"]!!.jsonPrimitive.content),
            importance = convertImportance(obj["importance"]!!.jsonPrimitive.content),
            statistic = SimpleMood(obj["_satisfaction"]!!.jsonPrimitive.float, obj["_stress"]!!.jsonPrimitive.float, obj["_anger"]!!.jsonPrimitive.float),
            description = obj["description"]!!.jsonPrimitive.content,
            generatedTime = Clock.getCurrentDateTime(),
            personGenerated = npc!!
        )
    }

    private fun convertType(type: String): FootballET{
        return when(type.lowercase()){
            "rabbia" -> FootballET.RABBIA
            "fiducia o incoraggiamento" -> FootballET.FIDUCIA
            "perdita di fiducia o autostima" -> FootballET.SFIDUCIA
            "critica" -> FootballET.CRITICA
            "frustazione" -> FootballET.FRUSTAZIONE
            else -> TODO("Not implemented yet")
        }
    }

    private fun convertImportance(importance: String): FootballEI{
        return when(importance.lowercase()){
            "banale" -> FootballEI.BANALE
            "normale" -> FootballEI.NORMALE
            "importante" -> FootballEI.IMPORTANTE
            "cruciale" -> FootballEI.CRUCIALE
            else -> TODO("Not implemented yet")
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
        messages.add(Message(Role.system, createXml("request", detail, comments)))
    }

    override fun receiveEvent(event: Event, newMood: Mood, newThoughtOnPlayer: CommonThought) {
        messages.add(Message(
            Role.system,
            createXml("event ",event.toMap(), mapOf())
            )
        )
    }
}
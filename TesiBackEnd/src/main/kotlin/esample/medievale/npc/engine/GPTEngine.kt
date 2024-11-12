package esample.medievale.npc.engine

import conceptualMap2.clock.Clock
import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.event.*
import conceptualMap2.exceptions.NPCNotStartedException
import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC
import conceptualMap2.npc.NPCEngine
import conceptualMap2.npc.Personality
import esample.medievale.SimpleMood
import esample.medievale.actualPosition
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.event.ConfidentialityLevelImpl
import esample.medievale.event.pureEvent.MedievalEventImportance
import esample.medievale.event.pureEvent.MedievalEventType
import esample.medievale.npc.personality.SimplePersonality
import esample.medievale.player
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
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
import io.github.cdimascio.dotenv.dotenv


@Suppress("UNCHECKED_CAST")
class GPTEngine: NPCEngine {
    val dotenv = dotenv()
    private val URL = "https://api.openai.com/v1/chat/completions"
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
                .addHeader("Authorization", "Bearer "+ dotenv["OPENAI_API_KEY"])
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

    private fun generateStory(characterInfo: Map<String, Any>): String {
        val m = characterInfo.toMutableMap()
        m["prompt"] = "date tutte queste informazioni riguardanti il personaggio generami una sua storia personale e restituiscimi solo quella, senza aggiungere altro"
        return sendSingleText(createXml("request",m, mapOf()))
    }

    private fun generatePersonality(characterInfo: Map<String, Any>): Personality {
        val m = characterInfo.toMutableMap()
        m["prompt"] = "date tutte queste informazioni riguardanti il personaggio generami una sua personalità e restituisci solo il jason che è compliant a questo schema "+readFile("..\\jsonSchema\\simplePersonality.json")

        val json = removeOutsideBraces(sendSingleText(createXml("request",m, mapOf())))
        return Json.decodeFromString<SimplePersonality>(json)
    }

    private fun generateMood(characterInfo: Map<String, Any>): Mood {
        val m = characterInfo.toMutableMap()
        m["prompt"] = "date tutte queste informazioni riguardanti il personaggio generami una suo mood e restituisci solo il jason che è compliant a questo schema "+readFile("..\\jsonSchema\\simpleMood.json")

        val json = removeOutsideBraces(sendSingleText(createXml("request", m, mapOf())))
        //println("MOOD: $json")
        return Json.decodeFromString<SimpleMood>(json)
    }

    private fun generateThoughtOnPlayer(characterInfo: Map<String, Any>): CommonThought {
        val m = characterInfo.toMutableMap()
        m["prompt"] = "date tutte queste informazioni riguardanti il personaggio e il pensiero comune del gruppo sul personaggio, commonThoughtOnPlayer, restituisci solo il jason che è compliant a questo schema "+readFile("..\\jsonSchema\\commonThoughtImpl.json")
        val json = removeOutsideBraces(sendSingleText(createXml("request", m, mapOf())))
        //println("MOOD: $json")
        return Json.decodeFromString<CommonThoughtImpl>(json)
    }

    private fun generateThoughtOnOtherGroups(characterInfo: Map<String, Any>): MutableMap<String, CommonThought>{
        val m = characterInfo.toMutableMap()
        m["prompt"] = "date tutte queste informazioni riguardanti il personaggio e il pensiero comune del gruppo sugli altri gruppi, commonThoughtOnGroups, crea dei pensieri personali del personaggio descritto e restituisci solo il jason che è compliant a questo schema "+readFile("..\\jsonSchema\\commonThoughtMap.json")
        val json = removeOutsideBraces(sendSingleText(createXml("request", m, mapOf())))
        //println("MOOD: $json")
        val j = Json{
            serializersModule = SerializersModule {
                polymorphic(CommonThought::class){
                    subclass(CommonThoughtImpl::class)
                }
            }
        }
        return j.decodeFromString<MutableMap<String, CommonThought>>(json)
    }

    private fun removeNull(map: MutableMap<String, Any?>): MutableMap<String, Any>{
        val ret = mutableMapOf<String, Any>()
        map.forEach { (k, v) ->
            if(v != null)
                ret[k] = v
        }
        return ret
    }
    //TODO da controllare
    override fun startNPC(map: MutableMap<String, Any?>, comments: Map<String, String>, npc: NPC) {
        val character: MutableMap<String, Any?> = map["character"] as MutableMap<String, Any?>
        val d = (character["details"]as MutableMap<String, Any?>)
        if(d["name"] == null || d["age"] == null) {
            val ret = generateNameAndAge()
            if(d["name"] == null){ d["name"] = ret.first; npc.setName(ret.first)}
            if(d["age"] == null) {d["age"] = ret.second; npc.setAge(ret.second)}
        }
        if(d["personalStory"] == null) {
            val m = mutableMapOf<String, Any>()
            val details = removeNull(((map["character"] as Map<String, Any?>)["details"] as Map<String, Any?>).toMutableMap())
            val char = removeNull((map["character"]!! as Map<String, Any?>).toMutableMap())
            char["details"] = details
            m["character"] = char
            m["context"] = map["context"]!!
            m["events"] = map["events"]!!
            val story = generateStory(m)
            d["personalStory"] = story
            npc.setStory(story)
        }
        if(character["personality"] == null){
            val m = mutableMapOf<String, Any>()
            m["character"] = removeNull((map["character"]!! as Map<String, Any?>).toMutableMap())
            m["context"] = map["context"]!!
            m["events"] = map["events"]!!
            val p = generatePersonality(m)
            npc.setPersonality(p)
            character["personality"] = p

        }
        if(character["mood"] == null) {
            val m = mutableMapOf<String, Any>()
            m["character"] = removeNull((map["character"]!! as Map<String, Any?>).toMutableMap())
            m["context"] = map["context"]!!
            m["events"] = map["events"]!!
            val mood = generateMood(m)
            npc.setMood(mood)
            character["mood"] = mood
        }
        if(character["thoughtOnPlayer"] == null) {
            val m = mutableMapOf<String, Any>()
            m["character"] = removeNull((map["character"]!! as Map<String, Any?>).toMutableMap())
            m["context"] = map["context"]!!
            m["events"] = map["events"]!!
            m["commonThoughtOnPlayer"] = npc.group.commonThoughtOnPlayer.toMap()
            val t = generateThoughtOnPlayer(m)
            npc.setThoughtOnPlayer(t)
            character["thoughtOnPlayer"] = t
        }
        if(character["thoughtOnOtherGroups"] == null){
            val m = mutableMapOf<String, Any>()
            m["character"] = removeNull((map["character"]!! as Map<String, Any?>).toMutableMap())
            m["context"] = map["context"]!!
            m["events"] = map["events"]!!
            m["commonThoughtOnGroups"] = npc.group.commonThoughtOnGroups
            val t = generateThoughtOnOtherGroups(m)
            npc.setThoughtOnOtherGroups(t)
            character["thoughtOnOtherGroups"] = t
        }

        map["prompt"] = "Da qui in avanti tu interpreterai il personaggio descritto in character, che vive in un mondo definito in context e deve non solo reagire, coerentemente alla sua personalità, a tutti gli eventi ma anche parlarne pro attivamente in base alla tipologia e all'importanza e risponderai a me che sono l'allenatore della squadra"

        val initialize = createXml("request", map.toMap() as Map<String, Any>, comments)
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

    override fun generateEvent(): PureEvent {
        val msg = Message(
            Role.system,
            """
             Sei un analista di conversazioni. 
             Analizza la seguente conversazione e restituisci un JSON con i seguenti campi: 
            'evento', 'importanza', 'felicità', 'rabbia', 'stress', 'description' e 'confidentiality'. 
             L'evento deve essere scelto tra: Amicizia; Minaccia; Sospetto; Rabbia; Collaborazione; Tradimento; Sfida, Aiuto; Omicidio; 
             L'importanza deve essere scelta tra: Banale, Normale, Importante. 
             I valori di 'felicità', 'rabbia' e 'stress' devono rispettare i seguenti vincoli numerici: 
             ogni valore deve essere compreso tra -3.0 e 3.0. 
             La somma dei valori assoluti di 'felicità', 'rabbia' e 'stress' deve rispettare i seguenti limiti: 
             se l'importanza è 'Banale', la somma deve essere ≤ 2; 
             se l'importanza è 'Normale', la somma deve essere tra > 1 e ≤ 4.5; 
             se l'importanza è 'Importante', la somma deve essere tra > 4.5 e ≤ 7; 
             Genera sempre una breve descrizione dell'evento. 
             La confidentiality può essere: segreto, neutrale o pubblico. Se dalla conversazione non riesci a distinguere allora è neutrale
             Restituisci solo il JSON come risultato.
            """.trimIndent()
        )
        val messagesList = listOf(msg)+messages.filter { it.role == Role.user || it.role == Role.assistant }
        val map = npc!!.context.toMap().toMutableMap()
        val dm = mutableMapOf<String, Any>()
        messagesList.forEach{ dm["Sender role=${if(it.role == Role.user) "player" else npc!!.name}"] = it.content}
        map["dialog"] = dm
        val m = Message(Role.user, createXml("Conversation", map, mapOf()))
        val event = sendRequest(listOf(msg, m), "gpt-4")
        val obj = Json.parseToJsonElement(event).jsonObject
        return PureEvent(
            type = convertType(obj["type"]!!.jsonPrimitive.content),
            importance = convertImportance(obj["importance"]!!.jsonPrimitive.content),
            statistic = SimpleMood(obj["_satisfaction"]!!.jsonPrimitive.float, obj["_stress"]!!.jsonPrimitive.float, obj["_anger"]!!.jsonPrimitive.float),
            description = obj["description"]!!.jsonPrimitive.content,
            generatedTime = Clock.getCurrentDateTime(),
            confidentiality = convertConfidentiality(obj["confidentiality"]!!.jsonPrimitive.content),
            personGenerated = Pair(npc!!, player),
            generationPlace = actualPosition
        )
    }

    private fun convertConfidentiality(lvl: String): ConfidentialityLevel{
        return when(lvl){
            "segreto" -> ConfidentialityLevelImpl.SEGRETO
            "neutrale" -> ConfidentialityLevelImpl.NEUTRALE
            "pubblico" -> ConfidentialityLevelImpl.PUBBLICO
            else -> throw IllegalStateException("Error in GPT response")
        }
    }

    override fun generateRandomEvent(map: MutableMap<String, Any>, comments: Map<String, String>): PureEvent {
        val eventGeneration = map["eventGeneration"] as MutableMap<String, Any>
        eventGeneration["prompt"] = """
            Utilizzando le informazioni fornite sui due personaggi e le regole specificate, genera un evento casuale. Il risultato deve essere restituito in formato JSON e includere i seguenti attributi:
            - type: scegli uno tra 'Amicizia', 'Minaccia', 'Sospetto', 'Rabbia', 'Collaborazione', 'Tradimento', 'Sfida', 'Aiuto', 'Omicidio'.
            - importance: scegli uno tra 'Banale', 'Normale', 'Importante'.
            - confidentiality: scegli uno tra 'segreto', 'neutrale', 'pubblico'
            - _satisfaction, _anger, _stress: valori numerici ciascuno compreso tra -3.0 e 3.0, rispettando i seguenti vincoli:
             - La somma dei valori assoluti di '_satisfaction', '_anger' e '_stress' deve essere:
              - ≤ 2 se l'importanza è 'Banale';
              - > 1 e ≤ 4.5 se l'importanza è 'Normale';
              - > 4.5 e ≤ 7 se l'importanza è 'Importante'.
            - description: un breve riassunto dell'evento generato.
            Assicurati che il JSON sia ben formattato e che tutti i valori rispettino le regole specificate.
        """.trimIndent()
        val event = sendRequest(listOf(Message(Role.user, createXml("request", map, comments))), "gpt-4")
        val obj = Json.parseToJsonElement(event).jsonObject
        return PureEvent(
            type = convertType(obj["type"]!!.jsonPrimitive.content),
            importance = convertImportance(obj["importance"]!!.jsonPrimitive.content),
            statistic = SimpleMood(obj["_satisfaction"]!!.jsonPrimitive.float, obj["_stress"]!!.jsonPrimitive.float, obj["_anger"]!!.jsonPrimitive.float),
            description = obj["description"]!!.jsonPrimitive.content,
            generatedTime = Clock.getCurrentDateTime(),
            confidentiality = convertConfidentiality(obj["confidentiality"]!!.jsonPrimitive.content),
            personGenerated = null,
            generationPlace = null
        )
    }

    private fun convertType(type: String): MedievalEventType{
        return when(type){
            "AMICIZIA" ->  MedievalEventType.AMICIZIA
            "MINACCIA" -> MedievalEventType.MINACCIA
            "TRADIMENTO" -> MedievalEventType.TRADIMENTO
            "SFIDA" -> MedievalEventType.SFIDA
            "SOSPETTO" -> MedievalEventType.SOSPETTO
            "SUPPORTO" -> MedievalEventType.SUPPORTO
            "COLLABORAZIONE" -> MedievalEventType.COLLABORAZIONE
            "AIUTO" -> MedievalEventType.AIUTO
            "OMICIDIO" -> MedievalEventType.OMICIDIO
            "RABBIA" -> MedievalEventType.RABBIA
            else -> TODO("Not implemented yet")
        }
    }

    private fun convertImportance(importance: String): MedievalEventImportance{
        return when(importance.lowercase()){
            "banale" -> MedievalEventImportance.BANALE
            "normale" -> MedievalEventImportance.NORMALE
            "importante" -> MedievalEventImportance.IMPORTANTE
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

    override fun addDetails(name: String, detail: Map<String, Any>, comments: Map<String, String>) {
        messages.add(Message(Role.system, createXml(name, detail, comments)))
    }

    override fun receiveEvent(event: AbstractEvent, newMood: Mood, newThoughtOnPlayer: CommonThought) {
        messages.add(Message(
            Role.system,
            createXml("event",event.toMap(), mapOf())
            )
        )
    }
}
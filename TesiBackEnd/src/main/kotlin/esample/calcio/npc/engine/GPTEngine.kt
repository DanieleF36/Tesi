package esample.calcio.npc.engine

import conceptualMap2.Statistic
import conceptualMap2.event.Event
import conceptualMap2.exceptions.NPCNotStartedException
import conceptualMap2.npc.NPCEngine
import conceptualMap2.npc.task.Task
import conceptualMap2.npc.knowledge.Knowledge
import conceptualMap2.npc.Personality
import esample.calcio.npc.footballer.personality.FootballerPersonality
import esample.calcio.npc.footballer.personality.SimpleMood
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.reflect.Field
import org.w3c.dom.Document
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

    override fun generateNameAndAge(): Pair<String, Int> {
        val input = "generami un nome, cognome e un età di una persona e restituiscimi una stringa così formattata: nome cognome, età. Un esempio è Lorenzo Guarnieri, 30"
        return Pair(input.substringBefore(","), input.substringAfter(",").toInt())
    }

    override fun generateStory(name: String, age: Int, context: Knowledge, groupName: String, groupDescription: String): String {
        val input = "Sapendo che il mondo di gioco è descritto da questo contesto ${context.toMap()}, e sapendo che il gruppo a cui appartiene questo personaggio è $groupName, "+
                    "che i personaggi che appartengono a questo gruppo sono così descritti: \"$groupDescription\", "+
                    "creami una storia di questo personaggio,che si chiama $name ed ha $age anni e restituiscimi solo quella e nient'altro"
        return sendSingleText(input)
    }

    override fun generatePersonality(context: Knowledge, groupName: String, groupDescription: String, story: String): Personality {
        val input = "Sapendo che il mondo di gioco è descritto da questo contesto ${context.toMap()}, e sapendo che il gruppo a cui appartiene questo personaggio è $groupName "+
                ", che i personaggi che appartengono a questo gruppo sono così descritti: \"$groupDescription\" e che la storia del giocatore è \"$story\", creami una personalità "+
                "di questo personaggio basandoti su questo json schema: "+readFile("C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\npc\\jsonSchema\\footballerPersonality.json")+" senza aggiungere nessun'altra parola"
        val json = removeOutsideBraces(sendSingleText(input))
        return Json.decodeFromString<FootballerPersonality>(json)
    }

    override fun generateMood(context: Knowledge, groupName: String, groupDescription: String, story: String, personality: Personality, events: List<Event>): Statistic {
        val input = "Sapendo che il mondo di gioco è descritto da questo contesto ${context.toMap()}, e sapendo che il gruppo a cui appartiene questo personaggio è $groupName "+
                ", che i personaggi che appartengono a questo gruppo sono così descritti: \"$groupDescription\", che la storia del giocatore è \"$story\", che la sua personalità "+
                "è descritta da questo oggetto: \"${personality.toMap()}\" e che gli eventi accaduti nel corso del tempo sono questo così descritti \"$events\", creami un mood di quel momento "+
                "di questo personaggio, che è un oggetto json che è descritto dallo schema: "+readFile("C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\npc\\jsonSchema\\simpleMood.json")+
                ". Senza aggiungere nient'altro, voglio solo l'oggetto json: {..} "
        val json = removeOutsideBraces(sendSingleText(input))
        //println("MOOD: $json")
        return Json.decodeFromString<SimpleMood>(json)
    }

    override fun startNPC(
        name: String,
        age: Int,
        context: Knowledge,
        groupName: String,
        groupDescription: String,
        story: String,
        personality: Personality,
        mood: Statistic,
        thoughtOnPlayer: Statistic,
        events: List<Event>,
        tasks: List<Task>
    ) {
        /*val initialize = "Da adesso tu interpreterai $name, che ha $age anni, e appartiene al gruppo di NPC chiamato $groupName, dove gli NPC sono generalmente così descritti: $groupDescription. "+
                         "Il contesto su cui ti devi basare è questo: \"${context.toMap()}\", la storia personale di $name è così descritta: \"$story\", "+
                         "i suoi obbiettivi sono: \"$goals\", e quelli a breve termine portano ad una decisione che deve essere presa dal personaggio. "+
                         "La sua personalità è descritta da questo oggetto(con valori da 1 a 10): "+
                         "\"${personality.toMap()}\", il suo mood attuale è descritto dal seguente(con valori da -1 a 1): \"${mood.toMap()}\" e gli eventi che deve conoscere sono descritti in questa lista: $events. Interpreta la descrizione e, ad esempio, se un evento riguarda l'allenatore e stai parlando con l'allenatore comportati di conseguenza e fai domande sull'evento"+
                         "Voglio che tu basi il tuo comportamento in base a questi valori e segui alla lettera il suo mood, se il valori di angry è alto allora sii arrabbiato, e così con tutti gli altri valori"+
                         "Un'altra cosa che devi fare è quella di parlare degli eventi spontaneamente e non solo quando interpellata. Questo non deve avvenire sempre o per ogni evento ma in base alla sua importanza per il gruppo"
        */
        val map = mutableMapOf<String, Any>()
        val character = mutableMapOf<String, Any>()
        character["details"] = mapOf(
            "name" to name,
            "age" to age,
            "group" to mapOf("name" to groupName, "description" to groupDescription),
            "personalStory" to story,
        )
        character["tasks"] = tasks.map { task -> task.toMap() }
        character["personality"] = personality.toMap()
        character["mood"] = mood.toMap()
        character["thoughtOnPlayer"] = thoughtOnPlayer.toMap()
        map["character"] = character
        map["context"] = context.toMap()
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

    override fun generateEvent(): Event? {
        val msg = Message(Role.system, "Adesso tu devi leggere tutta la conversazione che segue fino alla fine e riconoscere il possibile evento che potrebbe aver generato a partire dai seguenti "+readFile("C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\event\\allEvents.txt")+". Devi restituirmi solo eventName e nient'altro")
        val list = listOf(msg)+messages.filter { it.role == Role.user || it.role == Role.assistant }
        val eventName: String = sendRequest(list, model = "gpt-4").removePrefix("eventName: ")

        println("L'evento riconosciuto dopo la conversazione è questo: $eventName")
        val event = recognizeEvent(eventName)
        if(event != null)
            messages.add(Message(Role.system, "Da una precedente conversazione con l'allenatore è stato generato questo evento ${event.toMap()}"))
        return event
    }

    private fun recognizeEvent(eventName: String): Event?{
        if(eventName=="Nessuno")
            return null
        val eventClass = Class.forName("esample.calcio.event.EventObjectKt")
        val field: Field = eventClass.getDeclaredField(eventName)
        field.isAccessible = true
        return field.get(null) as Event
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

    override fun receiveEvent(event: Event, newMood: Statistic) {
        messages.add(Message(Role.system, "Un nuovo evento, così descritto: \"${event.toMap()}\", è stato ricevuto dal personaggio e lui deve comportarsi coerentemente al suo nuovo mood ${newMood.toMap()} e alla sua personalità in risposta ad esso"))
    }
}
package esample.calcio

import conceptualMap2.ConceptualMap
import conceptualMap2.event.Event
import conceptualMap2.npc.NPC
import esample.calcio.conceptualMap.*
import esample.calcio.event.*
import esample.calcio.npc.npcs.presidente
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import io.ktor.server.plugins.cors.CORS
import kotlinx.serialization.Serializable
import java.lang.reflect.Field

@Serializable
private data class PlayerInput(
    val msg: String,
)

@Serializable
private data class OptionForMatch(
    val changeGuarnieri: Boolean,
    val changeRomero: Boolean,
    val changeNatan: Boolean,
    val playBad: Boolean,
    val badTactic: Boolean,
)
@Serializable
private data class MatchResult(val teamA: Int, val teamB: Int)

private data class Match(val id: Int, val team: String, val value: Int, val isHome:Boolean, var result: MatchResult? = null)

private data class Couple<T, V> (val first: T, val second: V)

@Serializable
private data class EventFront(val name: String, val description: String, val groupName: String, val idNPC: Int)

fun main() {
    initMap()
    val npcs = esample.calcio.npc.getNPCs()
    presidente.addEvent(convocazioneMancata)
    //val npcs = listOf<NPC>()
    val matches = listOf(Match(0, "Inter", 10, true))
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            anyHost()
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)
            allowMethod(HttpMethod.Post)
            allowHeader(HttpHeaders.ContentType)
            allowCredentials = true
        }
        routing {
            route("/npcs") {
                get {
                    println("GET /npcs")
                    if (npcs.isEmpty())
                        call.respond("Nessun NPC")
                    else
                        call.respondText(Json.encodeToString(ListSerializer(CustomSerializer), npcs.toList()),ContentType.Application.Json)

                }
                put("/{id}/conversation"){
                    val id = call.parameters["id"]?.toIntOrNull()
                    println("PUT /$id/conversation")
                    val npc = npcs.find { it.id == id }
                    if (npc != null) {
                        lateinit var input: String
                        lateinit var inputPlayer: PlayerInput
                        try{
                            input = call.receive<String>()
                            inputPlayer = Json.decodeFromString<PlayerInput>(input)
                        }
                        catch (e: Exception){
                            println(e)
                            call.respond(500)
                        }
                        val response = npc.insertTextInput(inputPlayer.msg)
                        call.respond(response)
                    }else{
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
                delete("/{id}/conversation"){
                    val id = call.parameters["id"]?.toIntOrNull()
                    println("DELETE /npc/$id/conversation")
                    val npc = npcs.find { it.id == id }
                    if (npc != null) {
                        try{
                            npc.endConversation()
                        }catch (e: Exception){
                            println(e.printStackTrace())
                            call.respond(HttpStatusCode.InternalServerError)
                        }
                    } else{
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
            }
            route("/match"){
                get{
                    //return next match
                }
                put("/{id}"){
                    //option for match
                    val id = call.parameters["id"]?.toIntOrNull()
                    println("PUT /match/$id")

                    val options = call.receive<OptionForMatch>()
                    val res = computeNextMatch(matches[0], options)

                    val evt = res.second
                    generateGlobalEvent(evt)

                    if (options.changeRomero && res.first.teamA < res.first.teamB) {
                        conflittoSostituzione.personGenerated = npcs.find { it.name.contains("Romero") }
                        val e = conflittoSostituzione
                        calciatori.generateEvent(e)
                    }
                    if (options.changeGuarnieri) {
                        conflittoSostituzione.personGenerated = npcs.find { it.name.contains("Guarnieri") }
                        val e = conflittoSostituzione
                        calciatori.generateEvent(e)
                    }
                    if (!options.changeNatan){
                        val e = Event(
                            type = conflittoSostituzione.type,
                            importance = conflittoSostituzione.importance,
                            statistic = conflittoSostituzione.statistic,
                            description = "Un giocatore è infuriato per non essere stato fatto entrare durante il suo ultimo Juventus Catanzaro"
                        )
                        calciatori.generateEvent(e)
                    }
                    call.respond(res.first)

                }
            }
            route("/events"){
                post {
                    println("POST /events")
                    try {
                        val evt = call.receive<EventFront>()
                        val eventClass = Class.forName("esample.calcio.event.EventObjectKt")
                        val field: Field = eventClass.getDeclaredField(evt.name)
                        field.isAccessible = true
                        val event = field.get(null) as Event
                        val new = Event(
                            type = event.type ,
                            importance = event.importance ,
                            statistic = event.statistic,
                            description = evt.description,
                            personGenerated = npcs.find { it.id == evt.idNPC }
                        )
                        val mapClass = Class.forName("esample.calcio.conceptualMap.ConceptualMapObjectKt")
                        val f: Field = mapClass.getDeclaredField(evt.groupName)
                        f.isAccessible = true
                        val group = f.get(null) as ConceptualMap
                        group.generateEvent(new)
                        call.respond(HttpStatusCode.OK)
                    } catch (e: Exception) {
                        println("/events error ${e.printStackTrace()}")
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                }
                post("/global"){
                    println("POST /events/global")
                    try {
                        @Serializable
                        data class GEFront(val name: String)
                        val evt = call.receive<GEFront>()
                        val eventClass = Class.forName("esample.calcio.event.EventObjectKt")
                        val field: Field = eventClass.getDeclaredField(evt.name)
                        field.isAccessible = true
                        val event = field.get(null) as Event
                        generateGlobalEvent(event)
                        call.respond(HttpStatusCode.OK)
                    } catch (e: Exception) {
                        println("/events/global error ${e.printStackTrace()}")
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                }
            }
        }
    }.start(wait = true)
}

private fun computeNextMatch(match: Match, options: OptionForMatch): Couple<MatchResult, Event> {
    var res = match.value
    if(options.playBad) res-=1 else res+=1
    if(options.badTactic) res-=2 else res+=1
    if(options.changeNatan) res-=1
    if(options.changeRomero) res-=2
    if (options.changeGuarnieri) res-=2
    if(match.isHome) res+=1
    val matchRes = if(res < 3) MatchResult(0, 3) else if(res <6) MatchResult(0, 1)  else if(res < 9) MatchResult(0,0) else MatchResult(1,0)
    if(matchRes.teamA<matchRes.teamB)
        return Couple(matchRes, sconfitta)
    if(matchRes.teamA == matchRes.teamB)
        return Couple(matchRes, pareggio)
    return Couple(matchRes, vittoria)
}

private object CustomSerializer : KSerializer<NPC> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("NPC"){
        element<Int>("id")
        element<String>("name")
        element<String>("groupName")
    }

    override fun deserialize(decoder: Decoder): NPC {
        throw NotImplementedError("Deserialization not implemented")
    }

    override fun serialize(encoder: Encoder, value: NPC) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeIntElement(descriptor, 0, value.id)
        composite.encodeStringElement(descriptor, 1, value.name)
        composite.encodeStringElement(descriptor, 2, value.group.name)
        composite.endStructure(descriptor)

    }
}

private fun generateGlobalEvent(event: Event) {
    calciatori.receiveGlobalEvent(event)
    dirigenti.receiveGlobalEvent(event)
    staffTecnico.receiveGlobalEvent(event)
    staffSupporto.receiveGlobalEvent(event)
}
package esample.medievale.npc

import conceptualMap2.clock.TimerEvent
import conceptualMap2.conceptualMap.*
import conceptualMap2.event.*
import conceptualMap2.npc.NPC
import conceptualMap2.npc.knowledge.Knowledge
import conceptualMap2.npc.task.Task
import esample.medievale.SimpleMood
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.event.NewCTAfterLTCE
import esample.medievale.event.pureEvent.MedievalEventImportance
import esample.medievale.event.pureEvent.MedievalEventImportance.Companion.BANALE
import esample.medievale.event.pureEvent.MedievalEventImportance.Companion.CRUCIALE
import esample.medievale.event.pureEvent.MedievalEventImportance.Companion.IMPORTANTE
import esample.medievale.event.pureEvent.MedievalEventImportance.Companion.NORMALE
import esample.medievale.npc.personality.SimplePersonality
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.math.exp
import kotlin.random.Random

class Citizen(
    _age: Int? = null,
    _name: String? = null,
    var sex: Boolean? = null,
    group: ConceptualMap,
    context: Knowledge,
    _story: String? = null,
    _personality: SimplePersonality? = null,
    tasks: MutableList<Task> = mutableListOf(),
    _mood: SimpleMood? = null,
    _thoughtOnPlayer: CommonThoughtImpl?,
    _thoughtOnOtherGroups: MutableMap<String, CommonThought>,
    val job: Job,
): NPC(_age = _age, _name = _name, group = group, context = context, _story = _story, _personality = _personality, tasks = tasks, _mood = _mood, _thoughtOnPlayer = _thoughtOnPlayer, _thoughtOnOtherGroups=_thoughtOnOtherGroups) {
    private val knownNpc = mutableMapOf<Int, NPCRelationship>()
    private val events = mutableListOf<TimerEvent>()

    override fun initialize() {
        if(sex == null)
            sex = Random.nextBoolean()
        engine.generateRandomEvent(toMap().toMutableMap(), mapOf())
    }

    override fun endConversation() {
        val event = engine.generateEvent()
        _mood = _mood!!.update(event.statistic)
        _thoughtOnPlayer!!.update(event.statistic)
        group.receiveEvent(event)
        tasks.forEach { it.action(event) }
    }

    override fun generateRandomEvent(npc: NPC, type: EventType, importance: EventImportance) {
        val n = knownNpc[npc.id]
        if(n != null) {
            when(type.isPositive()) {
                -1 -> knownNpc[npc.id] = n.decrease()
                1 -> knownNpc[npc.id] = n.increase()
            }
        }
        else{
            when(type.isPositive()) {
                -1 -> knownNpc[npc.id] = NPCRelationship.DISTANT
                1 -> knownNpc[npc.id] = NPCRelationship.CLOSE
            }
        }
        npcRelationshipChanged(npc.id)

        val map = mutableMapOf<String, Any>()
        map["character1"] = toMap()
        map["character2"] = npc.toMap()
        map["relationship"] = knownNpc[npc.id]!!
        map["eventGeneration"] = mutableMapOf("rules" to mapOf(
            "tipologia" to mapOf(
                "type" to "l'evento deve essere di questa tipologia: ${type.name}",
                "descrizione" to type.toMap(),
            ),
            "importanza" to mapOf(
                "importance" to "l'importanza deve essere: ${importance.name}",
                "descrizione" to importance.toMap(),
            )

        ))
        val event = engine.generateRandomEvent(map, mapOf())
        _mood = _mood!!.update(event.statistic)
        _thoughtOnPlayer!!.update(event.statistic)
        group.receiveEvent(event)
        tasks.forEach { it.action(event) }
    }

    private fun npcRelationshipChanged(id: Int){
        engine.addDetails(
            "UpdateRelationship",
            mapOf("knownNpc change=" to mapOf(id to knownNpc[id])),
            mapOf("knownNpc change=" to "la relazione con il personaggio con l'id $id è cambiata"))
    }

    override fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        val character = mutableMapOf<String, Any?>()
        character["details"] = mapOf(
            "name" to name,
            "age" to age,
            "sex" to if(sex!!) "women" else "man",
            "group" to mapOf("name" to group.name, "description" to group.description),
            "personalStory" to story,
            "job" to job.name
        )
        character["personality"] = personality?.toMap()
        character["mood"] = mood?.toMap()
        character["thoughtOnPlayer"] = thoughtOnPlayer?.toMap()
        map["character"] = character
        map["context"] = context.toMap()
        map["events"] = group.getEventHistory().map { event -> event.toMap() }
        map["thoughtOnPlayer"] = thoughtOnPlayer!!.toMap()
        map["thoughtOnOtherGroups"] = _thoughtOnOtherGroups
        map["knownNpc"] = knownNpc
        return map
    }

    override fun addEvent(event: AbstractEvent) {
        when(event){
            is PureEvent -> pureEvent(event)
            is GlobalEvent -> globalEvent(event)
            is NewCTAfterLTCE -> newCTAfterLTCE(event)
            else -> TODO("not implemented yet")
        }
    }

    private fun pureEvent(event: PureEvent){
        if(event.personGenerated!!.first == this || event.personGenerated!!.second == this)
            return
        if(computeProbability(event)){
            val obj = TimerEvent(
                event,
                computeSpeed(event),
            ){
                _mood = _mood!!.update(event.statistic)
                if(event.personGenerated!!.first.name == "player" || event.personGenerated!!.second.name == "player")
                    _thoughtOnPlayer!!.update(event.statistic)
                tasks.forEach { it.action(event) }
                engine.receiveEvent(event, _mood!!, _thoughtOnPlayer!!)
                events.remove(it)

            }
            events.add(obj)
        }
    }
    private fun computeProbability(event: PureEvent): Boolean{
        val g = when(group.groupSize){
            GroupSize.PICCOLA -> 1f
            GroupSize.MEDIO_PICCOLA -> .9f
            GroupSize.MEDIO -> .75f
            GroupSize.MEDIO_GRANDE -> .65f
            GroupSize.GRANDE -> .55f
            GroupSize.ENORME -> .35f
            GroupSize.MEGA -> .1f
            GroupSize.GIGA -> .05f
            GroupSize.TERA -> .01f
            GroupSize.PETA -> .005f
        }
        val a = when(group.fellowship){
            Fellowship.VERY_LOW -> .25f
            Fellowship.LOW -> .4f
            Fellowship.MID -> .5f
            Fellowship.HIGH -> .7f
            Fellowship.VERY_HIGH -> .9f
        }
        val i = when(event.importance.name as MedievalEventImportance){
            BANALE -> .05f
            NORMALE -> .1f
            IMPORTANTE -> .8f
            CRUCIALE -> 1.2f
            else -> TODO("not implemented yet")
        }
        val prob = 1- exp(-g*a*i*300f)
        return Random.nextDouble()<=prob
    }
    private fun computeSpeed(event: PureEvent): Duration{
        val g = when(group.groupSize){
            GroupSize.PICCOLA -> 1f
            GroupSize.MEDIO_PICCOLA -> 2f
            GroupSize.MEDIO -> 3f
            GroupSize.MEDIO_GRANDE -> 4f
            GroupSize.GRANDE -> 5f
            GroupSize.ENORME -> 6f
            GroupSize.MEGA -> 7f
            GroupSize.GIGA -> 8f
            GroupSize.TERA -> 9f
            GroupSize.PETA -> 10f
        }
        val a = when(group.fellowship){
            Fellowship.VERY_LOW -> 1.2f
            Fellowship.LOW -> 1f
            Fellowship.MID -> .8f
            Fellowship.HIGH -> .6f
            Fellowship.VERY_HIGH -> .5f
        }
        val i = when(event.importance.name as MedievalEventImportance){
            BANALE -> 1.2f
            NORMALE -> .1f
            IMPORTANTE -> .8f
            CRUCIALE -> .5f
            else -> TODO("not implemented yet")
        }
        return Duration.of((g*a*i*10f).toLong(), ChronoUnit.MONTHS)
    }

    private fun globalEvent(event: GlobalEvent){
        val g = when(group.groupSize){
            GroupSize.PICCOLA -> 1f
            GroupSize.MEDIO_PICCOLA -> 2f
            GroupSize.MEDIO -> 3f
            GroupSize.MEDIO_GRANDE -> 4f
            GroupSize.GRANDE -> 5f
            GroupSize.ENORME -> 6f
            GroupSize.MEGA -> 7f
            GroupSize.GIGA -> 8f
            GroupSize.TERA -> 9f
            GroupSize.PETA -> 10f
        }
        val a = when(group.fellowship){
            Fellowship.VERY_LOW -> 1.2f
            Fellowship.LOW -> 1f
            Fellowship.MID -> .8f
            Fellowship.HIGH -> .6f
            Fellowship.VERY_HIGH -> .5f
        }
        val obj = TimerEvent(
            event,
            Duration.of((g*a*.5f*10f).toLong(), ChronoUnit.MONTHS),
        ){
            tasks.forEach { it.action(event) }
            engine.receiveEvent(event, _mood!!, _thoughtOnPlayer!!)
            events.remove(it)

        }
        events.add(obj)
    }

    private fun newCTAfterLTCE(event: NewCTAfterLTCE){
        val map = mutableMapOf<String, Any>()
        map["group name=${event.groupName}"] = mapOf(
            "oldRelationshipType" to event.oldType.toMap(),
            "newRelationshipType" to event.newType.toMap(),
            "NewCommonThought" to event.newCT.toMap()
        )
        val g = when(group.groupSize){
            GroupSize.PICCOLA -> 1f
            GroupSize.MEDIO_PICCOLA -> 2f
            GroupSize.MEDIO -> 3f
            GroupSize.MEDIO_GRANDE -> 4f
            GroupSize.GRANDE -> 5f
            GroupSize.ENORME -> 6f
            GroupSize.MEGA -> 7f
            GroupSize.GIGA -> 8f
            GroupSize.TERA -> 9f
            GroupSize.PETA -> 10f
        }
        val a = when(group.fellowship){
            Fellowship.VERY_LOW -> 1.2f
            Fellowship.LOW -> 1f
            Fellowship.MID -> .8f
            Fellowship.HIGH -> .6f
            Fellowship.VERY_HIGH -> .5f
        }
        val obj = TimerEvent(
            event,
            Duration.of((g*a*.5f*10f).toLong(), ChronoUnit.MONTHS),
        ){
            _thoughtOnOtherGroups[event.groupName] = event.newCT
            engine.addDetails("UpdateThought", map, mapOf())
            events.remove(it)

        }
        events.add(obj)
    }
}
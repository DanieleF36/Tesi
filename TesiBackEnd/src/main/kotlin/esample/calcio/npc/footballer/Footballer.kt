package esample.calcio.npc.footballer

import conceptualMap2.clock.TimerEvent
import conceptualMap2.npc.NPC
import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.event.Event
import conceptualMap2.event.GlobalEvent
import conceptualMap2.event.LocalEvent
import conceptualMap2.event.PureEvent
import conceptualMap2.npc.Mood
import conceptualMap2.npc.task.Task
import conceptualMap2.npc.knowledge.Knowledge
import esample.calcio.event.impl.FootballEI
import esample.calcio.event.impl.FootballET
import esample.calcio.npc.footballer.personality.FootballerPersonality
import esample.calcio.npc.footballer.personality.WeighedMood
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.random.Random

class Footballer(
    name: String? = null,
    age: Int? = null,
    group: ConceptualMap,
    context: Knowledge,
    story: String? = null,
    personality: FootballerPersonality? = null,
    tasks: MutableList<Task> = mutableListOf(),
    mood: Mood? = null,
    thoughtOnPlayer: CommonThought,
    /** String = npc name */
    private val thoughtsOnOthers: Map<String, Relationship>,
    private val seasonalStats: List<PlayerStats>,
    private val nationalStats: PlayerStats,
    private val transfer: List<Transfer>,
    private val role: GameRole
) : NPC(_age = age, _name=name, group=group, context=context, _story=story, _personality=personality, tasks=tasks, _mood=mood, _thoughtOnPlayer=thoughtOnPlayer) {

    override fun initialize() {
        val carrierStat = computeCarrierStat()
        val map = mutableMapOf<String, Any>()
        val character = mutableMapOf<String, Any?>()
        character["details"] = mapOf<String, Any?>(
            "name" to name,
            "age" to age,
            "group" to mapOf("name" to group.name, "description" to group.description),
            "personalStory" to story,
        )
        character["tasks"] = tasks.map { task -> task.toMap() }
        character["personality"] = personality?.toMap()
        character["mood"] = mood?.toMap()
        character["thoughtOnPlayer"] = thoughtOnPlayer?.toMap()
        map["character"] = character
        map["context"] = context.toMap()
        map["events"] = group.getEventHistory().map { event -> event.toMap() }
        map["thoughtOnPlayer"] = thoughtOnPlayer!!.toMap()
        map["thoughtsOnOthers"] = thoughtsOnOthers
        map["carrierStats"] = carrierStat.toMap()
        map["nationalStats"] = nationalStats.toMap()
        map["actualSeasonStats"] = seasonalStats.last().toMap()
        map["transfer"] = transfer.map{ it.toMap()}
        map["role"] = role.toString()

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

        engine.startNPC(map, comments, this)
    }

    override fun endConversation() {
        val event = engine.generateEvent()
        _mood = _mood!!.update(event.statistic)
        //println("Il mood di $_name è cambiato, diventando $mood, in seguito all'evento: ${event.description}")
        if(event is LocalEvent )
            group.generateEvent(event)
        else if(event is PureEvent)
            group.generateEvent(event)
        tasks.forEach { it.action(event) }
    }

    override fun addEvent(event: Event) {
        if(event is LocalEvent && event.personGenerated == this)
            return
        val evt = group.getEventHistory().last()
        if(computeProbPropagation(evt)) {
            TimerEvent(evt, computeSpeedProp(evt)) {
                _mood = when (evt) {
                    is LocalEvent -> localEvent(evt)
                    is GlobalEvent -> globalEvent(evt)
                    else -> TODO("Not supported yet")
                }
                thoughtOnPlayer!!.update(event.statistic)
                engine.receiveEvent(evt, mood!!, thoughtOnPlayer!!)
                tasks.forEach { it.action(evt) }
                group.receivedEventFromNpc(this, event)
            }
        }
    }

    private fun computeProbPropagation(event: Event): Boolean{
        if(event is GlobalEvent)
            return true //TODO()
        else {
            val p = if(event is LocalEvent)
                 (when (thoughtsOnOthers[event.personGenerated.name]){
                    Relationship.VERY_CLOSE -> 1f
                    Relationship.CLOSE -> .8f
                    Relationship.ACQUAINTANCE -> .5f
                    Relationship.NO_RELATIONSHIP -> .2f
                    Relationship.DISTANT -> .1f
                    Relationship.ADVERSARY -> .6f
                    Relationship.RIVAL -> .8f
                    null -> TODO()
                } * .4f) + (when(event.personGenerated.group.fellowship){
                    Fellowship.VERY_LOW -> .1f
                    Fellowship.LOW -> .3f
                    Fellowship.MID -> .5f
                    Fellowship.HIGH -> .8f
                    Fellowship.VERY_HIGH -> 1f
                } * .3f) + (when(event.importance as FootballEI){
                    FootballEI.BANALE -> .1f
                    FootballEI.NORMALE -> .3f
                    FootballEI.IMPORTANTE -> .7f
                    FootballEI.CRUCIALE -> 1f
                    else -> TODO()
                } * .5f)
            else TODO()
            return Math.random()<=p
        }
    }

    private fun computeSpeedProp(event: Event): Duration{
        if(event is GlobalEvent)
            return Duration.ZERO //TODO()
        else {
            val p = if (event is LocalEvent)
                (when (thoughtsOnOthers[event.personGenerated.name]) {
                    Relationship.VERY_CLOSE -> 1f
                    Relationship.CLOSE -> .8f
                    Relationship.ACQUAINTANCE -> .5f
                    Relationship.NO_RELATIONSHIP -> .2f
                    Relationship.DISTANT -> .1f
                    Relationship.ADVERSARY -> .6f
                    Relationship.RIVAL -> .8f
                    null -> TODO()
                } * .4f) + (when (event.personGenerated.group.fellowship) {
                    Fellowship.VERY_LOW -> .1f
                    Fellowship.LOW -> .3f
                    Fellowship.MID -> .5f
                    Fellowship.HIGH -> .8f
                    Fellowship.VERY_HIGH -> 1f
                } * .3f) + (when (event.importance as FootballEI) {
                    FootballEI.BANALE -> .1f
                    FootballEI.NORMALE -> .3f
                    FootballEI.IMPORTANTE -> .7f
                    FootballEI.CRUCIALE -> 1f
                    else -> TODO()
                } * .5f)
            else TODO()
            val minutes = ((1f/p)*1500).toLong()+Random.nextInt(-5, 5)
            return Duration.of(minutes, ChronoUnit.MINUTES)
        }
    }

    private fun localEvent(evt: LocalEvent): Mood{
        if(evt.personGenerated==this)
            mood
        //each player should react in a different way from each event
        val personGenerated = evt.personGenerated
        val relationshipModifier = when (thoughtsOnOthers[personGenerated.name] ?: Relationship.NO_RELATIONSHIP) {
            Relationship.VERY_CLOSE -> 1.0f
            Relationship.CLOSE -> 0.8f
            Relationship.ACQUAINTANCE -> 0.5f
            Relationship.NO_RELATIONSHIP -> 0.2f
            Relationship.DISTANT -> -0.5f
            Relationship.ADVERSARY -> -0.8f
            Relationship.RIVAL -> -1.0f
        }

        val importanceModifier = when (evt.importance) {
            FootballEI.BANALE -> 0.1f
            FootballEI.NORMALE -> 0.3f
            FootballEI.IMPORTANTE -> 0.7f
            FootballEI.CRUCIALE -> 1.0f
            else -> TODO()
        }

        val satisfactionFactor = if (relationshipModifier > 0) 0.5f + 0.5f * relationshipModifier else relationshipModifier
        val angerFactor = if (relationshipModifier > 0) 0.5f + 0.5f * (1 - relationshipModifier) else relationshipModifier
        val stressFactor = if (relationshipModifier > 0) 0.5f + 0.5f * (1 - relationshipModifier) else relationshipModifier

        val moodChange = when (evt.type) {
            FootballET.RABBIA -> WeighedMood(
                _satisfaction = 0.5f * satisfactionFactor * importanceModifier,
                _stress = 0.3f * stressFactor * importanceModifier,
                _anger = 0.6f * angerFactor * importanceModifier
            )
            FootballET.FIDUCIA -> WeighedMood(
                _satisfaction = 0.7f * satisfactionFactor * importanceModifier,
                _stress = 0.2f * stressFactor * importanceModifier,
                _anger = 0.3f * angerFactor * importanceModifier
            )
            FootballET.SFIDUCIA -> WeighedMood(
                _satisfaction = 0.7f * satisfactionFactor * importanceModifier,
                _stress = 0.4f * stressFactor * importanceModifier,
                _anger = 0.5f * angerFactor * importanceModifier
            )
            FootballET.CRITICA -> WeighedMood(
                _satisfaction = 0.4f * satisfactionFactor * importanceModifier,
                _stress = 0.5f * stressFactor * importanceModifier,
                _anger = 0.4f * angerFactor * importanceModifier
            )
            FootballET.FRUSTAZIONE -> WeighedMood(
                _satisfaction = 0.6f * satisfactionFactor * importanceModifier,
                _stress = 0.6f * stressFactor * importanceModifier,
                _anger = 0.5f * angerFactor * importanceModifier
            )
            FootballET.NESSUNA -> WeighedMood(
                _satisfaction = 0.0f,
                _stress = 0.0f,
                _anger = 0.0f
            )

            else -> TODO()
        }

        return moodChange.update(mood!!)

    }

    private fun globalEvent(evt: GlobalEvent): Mood{
        return evt.statistic.update(mood!!)
    }

    private fun computeCarrierStat(): PlayerStats {
        val stats = PlayerStats(0,0,0,0,0f, null)
        for(s in seasonalStats){
            stats.update(s)
        }
        return stats
    }
}
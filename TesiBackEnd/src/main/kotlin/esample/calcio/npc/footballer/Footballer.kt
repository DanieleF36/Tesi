package esample.calcio.npc.footballer

import conceptualMap2.npc.NPC
import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.event.Event
import conceptualMap2.event.GlobalEvent
import conceptualMap2.event.LocalEvent
import conceptualMap2.event.PropagatedEvent
import conceptualMap2.npc.Mood
import conceptualMap2.npc.task.Task
import conceptualMap2.npc.knowledge.Knowledge
import esample.calcio.event.impl.FootballEI
import esample.calcio.event.impl.FootballET
import esample.calcio.npc.footballer.personality.FootballerPersonality
import esample.calcio.npc.footballer.personality.WeighedMood

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
    private val carrierStat: PlayerStats
    init{
        carrierStat = computeCarrierStat()
        val map = mutableMapOf<String, Any>()
        map["thoughtOnPlayer"] = thoughtOnPlayer.toMap()
        map["thoughtsOnOthers"] = thoughtsOnOthers
        map["carrierStats"] = carrierStat.toMap()
        map["nationalStats"] = nationalStats.toMap()
        map["actualSeasonStats"] = seasonalStats.last().toMap()
        map["transfer"] = transfer.map{ it.toMap()}
        map["role"] = role.toString()
        engine.addDetails(map, mapOf())

    }

    override fun endConversation() {
        val event = engine.generateEvent() ?: return
        mood!!.update(event.statistic)
        //println("Il mood di $_name è cambiato, diventando $mood, in seguito all'evento: ${event.description}")
        group.generateEvent(event)
        tasks.forEach { it.action(event) }
    }

    override fun addEvent(event: LocalEvent) {
        mood!!.update(event.statistic)
        engine.receiveEvent(event, mood!!, thoughtOnPlayer!!)
        group.generateEvent(event)
    }

    /**
     * This function is called when an event is received from the group
     */
    override fun update() {
        val evt = group.getEventHistory().last()
        _mood = when(evt){
            is LocalEvent -> localEvent(evt)
            is PropagatedEvent -> propagatedEvent(evt)
            is GlobalEvent -> globalEvent(evt)
            else -> TODO("Not supported yet")
        }
        engine.receiveEvent(evt, mood!!,thoughtOnPlayer!!)
        tasks.forEach { it.action(evt) }
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

    private fun propagatedEvent(evt: PropagatedEvent): Mood{
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
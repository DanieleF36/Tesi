package esample.calcio.npc.footballer

import conceptualMap2.ConceptualMap
import conceptualMap2.npc.NPC
import conceptualMap2.Statistic
import conceptualMap2.npc.task.Task
import conceptualMap2.npc.knowledge.Knowledge
import esample.calcio.event.impl.FootballEI
import esample.calcio.event.impl.FootballET
import esample.calcio.npc.footballer.personality.FootballerPersonality
import esample.calcio.npc.footballer.personality.SimpleMood

class Footballer(
    name: String? = null,
    age: Int? = null,
    group: ConceptualMap,
    context: Knowledge,
    story: String? = null,
    personality: FootballerPersonality? = null,
    tasks: MutableList<Task> = mutableListOf(),
    mood: Statistic? = null,
    thoughtOnPlayer: Statistic,
    /** String = npc name */
    private val thoughtsOnOthers: Map<String, Relationship>,
    private val seasonalStats: List<PlayerStats>,
    private val nationalStats: PlayerStats,
    private val transfer: List<Transfer>,
    private val role: GameRole
) : NPC(_age = age, _name=name, group=group, context=context, story=story, personality=personality, tasks=tasks, mood=mood, thoughtOnPlayer = thoughtOnPlayer) {
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

    override fun update() {
        val evt = group.getEventHistory().last()
        if(evt.personGenerated==this)
            return
        //each player should react in a different way from each event
        val personGenerated = evt.personGenerated ?: return
        val relationship = thoughtsOnOthers[personGenerated.name] ?: Relationship.NO_RELATIONSHIP
        val relationshipModifier = when (relationship) {
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
            FootballET.RABBIA -> SimpleMood(
                _satisfaction = 0.5f * satisfactionFactor * importanceModifier,
                _stress = 0.3f * stressFactor * importanceModifier,
                _anger = 0.6f * angerFactor * importanceModifier
            )
            FootballET.FIDUCIA -> SimpleMood(
                _satisfaction = 0.7f * satisfactionFactor * importanceModifier,
                _stress = 0.2f * stressFactor * importanceModifier,
                _anger = 0.3f * angerFactor * importanceModifier
            )
            FootballET.SFIDUCIA -> SimpleMood(
                _satisfaction = 0.7f * satisfactionFactor * importanceModifier,
                _stress = 0.4f * stressFactor * importanceModifier,
                _anger = 0.5f * angerFactor * importanceModifier
            )
            FootballET.CRITICA -> SimpleMood(
                _satisfaction = 0.4f * satisfactionFactor * importanceModifier,
                _stress = 0.5f * stressFactor * importanceModifier,
                _anger = 0.4f * angerFactor * importanceModifier
            )
            FootballET.FRUSTAZIONE -> SimpleMood(
                _satisfaction = 0.6f * satisfactionFactor * importanceModifier,
                _stress = 0.6f * stressFactor * importanceModifier,
                _anger = 0.5f * angerFactor * importanceModifier
            )
            FootballET.NESSUNA -> SimpleMood(
                _satisfaction = 0.0f,
                _stress = 0.0f,
                _anger = 0.0f
            )

            else -> TODO()
        }

        TODO(mood!!.update(moodChange)) //Moltiplicatore e non somma

        engine.receiveEvent(evt, mood!!)
        tasks.forEach { it.action(evt) }
        //println("$_name ha cambiato il suo mood, $mood, in seguito all'evento ${evt.description}")
    }

    private fun computeCarrierStat(): PlayerStats {
        val stats = PlayerStats(0,0,0,0,0f, null)
        for(s in seasonalStats){
            stats.update(s)
        }
        return stats
    }
}
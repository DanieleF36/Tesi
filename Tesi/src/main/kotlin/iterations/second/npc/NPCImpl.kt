package iterations.second.npc

import iterations.second.ConceptualMap
import iterations.second.Event
import iterations.second.NPC
import iterations.second.Statistic
import java.io.Closeable

class NPCImpl(
    val name: String,
    val age: Int,
    val group: ConceptualMap,
    private val personality: NPCPersonality,
    /** Stato d'animo */
    private val mood: NPCMood
): NPC, Closeable {
    /**
     * to be used in insertTextInput
     */
    private var eventHistory: List<Event> = listOf()
    override fun getName(): String {
        return name
    }

    override fun getPersonality(): Statistic {
        return personality.clone()
    }

    override fun insertTextInput(input: String): String {
        TODO("Not yet implemented")
    }
    /**
     * When this fun is called the perception of the group to which this npc belongs compared to the player has changed
     */
    override fun update() {
        eventHistory = group.getEventsHistory()
        mood.update(group.getLastEvent().statistic)

    }
    /**
     * Before deleting this npc it will be removed as observer of group stat
     */
    override fun close() {
        group.detach(this)
    }
}
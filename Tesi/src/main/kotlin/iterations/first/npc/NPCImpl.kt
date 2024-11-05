package iterations.first.npc

import iterations.first.ConceptualMap
import iterations.first.Event
import iterations.first.NPC
import iterations.first.Statistic
import java.io.Closeable

class NPCImpl(
    name: String,
    age: Int,
    val group: ConceptualMap,
    personality: NPCPersonality,
    /** Stato d'animo */
    mood: NPCMood
): NPC(name, age, personality, mood), Closeable {
    /**
     * to be used in insertTextInput
     */
    private var eventHistory: Collection<Event> = listOf()

    override fun insertTextInput(input: String): String {
        TODO("Not yet implemented")
    }
    /**
     * When this fun is called the perception of the group to which this npc belongs compared to the player has changed
     */
    override fun update() {
        eventHistory =  group.getEventHistory()
        mood.update(group.getLastEvent().statistic)

    }
    /**
     * Before deleting this npc it will be removed as observer of group stat
     */
    override fun close() {
        group.detach(this)
    }
}
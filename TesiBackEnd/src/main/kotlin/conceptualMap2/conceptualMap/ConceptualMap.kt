package conceptualMap2.conceptualMap

import conceptualMap2.event.Event
import conceptualMap2.event.GlobalEvent
import conceptualMap2.event.LocalEvent
import conceptualMap2.exceptions.*
import conceptualMap2.npc.NPC

/**
 * @param name this is the name of the group that will identify an object
 * @param description this is the general description of an NPC that live in this group
 * @param commonThought this is the common thought of every NPC on the player
 */
abstract class ConceptualMap (
    val name: String,
    val description: String,
    val commonThought: CommonThought,
    val fellowship: Fellowship
) {
    protected val npcs = mutableListOf<NPC>()
    /**
     * @return the collection of all the possible event generated or propagated in this group, sorted by time. i.e. the last event in the list will be the last generated
     */
    abstract fun getEventHistory(): List<Event>
    /**
     * @return true if the link was correctly added, false if the teo groups are already linked
     */
    abstract fun addLink(link: Link): Boolean
    /**
     * @param group
     * @return true if the link was correctly added, false if the link is missing
     */
    abstract fun removeLink(group: ConceptualMap): Boolean
    /**
     * @param event is the one that will be generated inside the group amd the only one that is allowed to modify the common thought
     * @param propagation if true the event will be propagated to the other linked groups
     */
    abstract fun generateEvent(event: LocalEvent, propagation: Boolean = true)
    /**
     * @param event is a global event that was generated in the world
     */
    abstract fun receiveGlobalEvent(event: GlobalEvent)
    /**
     * this function is used to inform that a npc received correctly the event
     */
    abstract fun receivedEventFromNpc(npc: NPC, event: Event)

    abstract fun generateNPC(): NPC

    fun attach(npc: NPC){
        npcs.add(npc)
    }

    fun detach(npc: NPC){
        npcs.remove(npc)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other !is ConceptualMap)
            return false

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
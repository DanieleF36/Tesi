package conceptualMap2.conceptualMap

import conceptualMap2.event.AbstractEvent
import conceptualMap2.event.ChangeRelationshipLTE
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.event.GlobalEvent
import conceptualMap2.event.PureEvent
import conceptualMap2.npc.NPC
import javax.swing.event.ChangeEvent
import kotlin.random.Random

/**
 * @param name this is the name of the group that will identify an object
 * @param description this is the general description of an NPC that live in this group
 * @param commonThoughtOnPlayer this is the common thought of every NPC on the player
 */
abstract class ConceptualMap (
    val name: String,
    val description: String,
    val commonThoughtOnPlayer: CommonThought,
    val commonThoughtOnGroups: MutableMap<String, CommonThought>,
    val fellowship: Fellowship,
    val groupSize: GroupSize
) {
    internal val npcs = mutableListOf<NPC>()
    /**
     * @return the collection of all the possible event generated or propagated in this group, sorted by time. i.e. the last event in the list will be the last generated
     */
    abstract fun getEventHistory(): List<AbstractEvent>
    /**
     * @return true if the link was correctly added, false if the teo groups are already linked
     */
    abstract fun addLink(link: Link, newCT: CommonThought): Boolean
    /**
     * @return true if the link was correctly removed, false if the link is missing
     */
    abstract fun removeLink(link: Link): Boolean

    fun receiveEvent(event: AbstractEvent, propagation: Boolean = true){
        when(event){
            is GlobalEvent -> receiveGlobalEvent(event)
            is ChangeRelationshipLTE -> receiveEvent(event, propagation)
            is PureEvent -> generateEvent(event, propagation)
        }
    }

    protected abstract fun generateEvent(event: PureEvent, propagation: Boolean = true)

    protected abstract fun receiveEvent(event: ChangeRelationshipLTE, propagation: Boolean = true)
    /**
     * this function will generate a random event between 2 random NPCs based on their relationship and personality
     */
    abstract fun generateRandomEvent()

    abstract fun generateRandomEvent(type: EventType)

    abstract fun generateRandomEvent(importance: EventImportance)

    abstract fun generateRandomEvent(type: EventType, importance: EventImportance)
    /**
     * this function will generate a random event between npc1 and npc2 based on their relationship and personality
     */
    abstract fun generateRandomEvent(npc1: NPC, npc2: NPC)

    abstract fun generateRandomEvent(type: EventType, importance: EventImportance, npc1: NPC, npc2: NPC)
    /**
     * @param event is a global event that was generated in the world
     */
    protected abstract fun receiveGlobalEvent(event: GlobalEvent)
    /**
     * this function is used to inform that a npc received correctly the event
     */
    abstract fun receivedEventFromNpc(npc: NPC, event: Event)

    abstract fun generateNPC(): NPC

    open fun find(group: ConceptualMap, limit: Int): MutableList<MutableList<Link>>?{
        val res = recursiveFind(group, limit)
        return res
    }

    internal abstract fun recursiveFind(group: ConceptualMap, limit: Int, visited: MutableSet<ConceptualMap> = mutableSetOf(), path: MutableList<Link> = mutableListOf(), allPaths: MutableList<MutableList<Link>> = mutableListOf()): MutableList<MutableList<Link>>

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
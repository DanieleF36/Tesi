package iterations.second

import observerInterfaces.Subject


interface ConceptualMap: Subject {
    fun setCommonThought(commonThought: Statistic)
    /**
     * This function will return the common thought of the player of this group
     */
    fun getCommonThought(): Statistic?
    fun setBackstory(backStory: String)
    fun getBackstory(): String
    fun setName(name: String)
    fun getName():String
    fun getEventsHistory(): List<Event>
    //TODO serve?
    fun getLastEvent(): Event
    fun setEventPropagationTime(eventiPriority: EventPriority, time: TimeComponent)
    /**
     * This function will add a new group and returns this group.
     * Every group is identified by its name, if you're trying to add a group with the same name of another one
     * a GroupNameUniqueException will be thrown, and has its own statistics that define the common thought of the npc on
     * the player
     */
    fun addGroup(name: String, backStory: String, commonThought: Statistic, weight: Weight): ConceptualMap
    /**
     * This function will remove the group and returns True if it was successfully removed or false if it does not exist.
     * Will not search for that group in all the structure, but just in its group
     * When a group is removed all its links will be also removed.
     * If the group contains some subgroup they will also be removed
     */
    fun removeGroup(name: String): Boolean
    /**
     * This function will add the first new subgroup and returns this new one, and it will throw IllegalStateException if
     * someone try to use this function when a subgroup already exists
     * Every subgroup is identified by its name inside the external group, if two subgroup have the same name, but they
     * are in different groups there will be no problem, and if you're trying to add a subgroup with the same name of
     * another one a GroupNameUniqueException will be thrown, and has its own statistics that define the common thought
     * of the npc on the player.
     */
    fun addSubGroup(name: String, backStory: String, commonThought: Statistic): ConceptualMap
    /**
     * This function will remove the subgroup and returns True if it was successfully removed or false if it does not exist.
     * When a subgroup is removed all its links will be also removed.
     * If the subgroup contains some subgroup they will also be removed
     */
    fun removeSubGroup(name: String): Boolean
    /**
     * This function will add a new link between the two groups, and it will throw a LinkAlreadyExistingException if a link
     * between this two group already exists.
     * This link will be weighed on some statistics and this will be configured using the parameter weight
     */
    fun addLink(node: ConceptualMap, weight: Weight)
    /**
     * This function will remove a new link between the two groups, and will returns true if the link was successfully removed
     * or false otherwise.
     */
    fun removeLink(group: ConceptualMap): Boolean
    /**
     * This function will generate an event that modify the common thought and will propagate, if propagation is true, it's using the weighted links but, the event, will also be propagated
     * through the subgroup if subgroup is true.
     * This function will be the only way to modify the common thought of some group
     */
    fun generateEvent(event: Event, propagation: Boolean = true, subgroup: Boolean = true)
    /**
     * This function will generate a npc powered by some IA using the backstory and the personality as starting point
     * If the backstory is missing the group one will be used
     * If the personality is missing the IA will generate that
     * If both of them are missing the IA will take care of it
     */
    fun generateNPC(backStory: String?, personality: Statistic?): NPC
}
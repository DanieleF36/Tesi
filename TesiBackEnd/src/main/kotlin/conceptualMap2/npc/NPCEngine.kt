package conceptualMap2.npc

import conceptualMap2.Statistic
import conceptualMap2.event.Event
import conceptualMap2.npc.knowledge.Knowledge
import conceptualMap2.exceptions.NPCNotStartedException
import conceptualMap2.npc.task.Task

/**
 * The NPCEngine will provide all the necessary for an NPC to answer to an input, included the history, and receive and comprehend an event
 */
interface NPCEngine {
    fun generateNameAndAge(): Pair<String, Int>
    /**
     * @param name of the NPC
     * @param age of the NPC
     * @param context the global context of the world
     * @param groupName of NPC's group
     * @param groupDescription of NPC's group
     * @return a backstory, used to describe the NPC, based on the global context and the group description
     */
    fun generateStory(name:String, age: Int, context: Knowledge, groupName: String, groupDescription: String): String
    /**
     * @param context the global context of the world
     * @param groupName of NPC's group
     * @param groupDescription of NPC's group
     * @param story used to describe the NPC
     * @return a personality
     */
    fun generatePersonality(context: Knowledge, groupName: String, groupDescription: String, story: String): Personality
    /**
     * @param context the global context of the world
     * @param groupName of NPC's group
     * @param groupDescription of NPC's group
     * @param story used to describe the NPC
     * @param personality of the NPC
     * @param events is the list of all the relevant event happened
     * @return a mood of the player based on all the previous parameters
     */
    fun generateMood(context: Knowledge, groupName: String, groupDescription: String, story: String, personality: Personality, events: List<Event>): Statistic
    /**
     * This function initialize the npc giving him:
     * @param name
     * @param age
     * @param context the global context of the world
     * @param groupDescription of NPC's group
     * @param story used to describe the NPC
     * @param personality of the NPC
     * @param mood of the NPC
     * @param events is the list of all the relevant event happened
     */
    fun startNPC(
        name: String,
        age: Int,
        context: Knowledge,
        groupName: String,
        groupDescription: String,
        story: String,
        personality: Personality,
        mood: Statistic,
        thoughtOnPlayer: Statistic,
        events: List<Event>,
        tasks: List<Task>
    )
    /**
     * @param input is what the user wants to say to the NPC
     * @return the answer to that input
     * @throws NPCNotStartedException
     */
    fun talk(input: String):String
    /**
     * @return an event that was generated with this conversation or null if the whole conversation is meaningless
     */
    fun generateEvent(): Event?
    /**
     * This function will let know the NPC about this event
     */
    fun receiveEvent(event: Event, newMood: Statistic)
    /**
     * The goal of this function is to add some detail to what the NPC know and some comment on it
     * @param detail is a map defined as tag: tagValue
     * @param comments is a map defined as tag: comment about tag
     */
    fun addDetails(detail: Map<String, Any>, comments: Map<String, String>)
}
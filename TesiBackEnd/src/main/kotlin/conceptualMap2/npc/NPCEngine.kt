package conceptualMap2.npc

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.event.Event
import conceptualMap2.event.LocalEvent
import conceptualMap2.exceptions.NPCNotStartedException

/**
 * The NPCEngine will provide all the necessary for an NPC to answer to an input, included the history, and receive and comprehend an event
 */
interface NPCEngine {
    /**
     * This function initialize the npc giving him:
     * a name
     * an age
     * a context the global context of the world
     * a groupDescription of NPC's group
     * a story used to describe the NPC
     * a personality of the NPC
     * a mood of the NPC
     * an events is the list of all the relevant event happened
     */
    fun startNPC(map: MutableMap<String, Any>, comments: Map<String, String>, npc: NPC)
    /**
     * @param input is what the user wants to say to the NPC
     * @return the answer to that input
     * @throws NPCNotStartedException
     */
    fun talk(input: String):String
    /**
     * @return an event that was generated with this conversation or null if the whole conversation is meaningless
     */
    fun generateEvent(): LocalEvent

    fun generateRandomEvent(map: MutableMap<String, Any>, comments: Map<String, String>,): LocalEvent
    /**
     * This function will let know the NPC about this event
     */
    fun receiveEvent(event: Event, newMood: Mood, newThoughtOnPlayer: CommonThought)
    /**
     * The goal of this function is to add some detail to what the NPC know and some comment on it
     * @param detail is a map defined as tag: tagValue
     * @param comments is a map defined as tag: comment about tag
     */
    fun addDetails(detail: Map<String, Any>, comments: Map<String, String>)
}
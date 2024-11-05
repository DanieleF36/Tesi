package iterations.second

import observerInterfaces.Observer

/**
 * This interface describe a simple NPC that has no history of the past conversation and is very usefully when the created NPC
 * will never be encountered again
 */
interface NPC: Observer {
    fun getName(): String
    /**
     * This function will return the set of statistic that describe its personality
     */
    fun getPersonality(): Statistic
    /**
     * This function will take in the input of the player and will return the answer of the npc
     */
    fun insertTextInput(input: String): String
}
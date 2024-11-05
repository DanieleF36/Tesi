package iterations.first

import observerInterfaces.Observer

/**
 * This interface describe a simple NPC that has no history of the past conversation and is very usefully when the created NPC
 * will never be encountered again
 */
abstract class NPC(
    val name: String,
    val age: Int,
    val personality: Statistic,
    val mood: Statistic
): Observer {
    /**
     * This function will take in the input of the player and will return the answer of the npc
     */
    abstract fun insertTextInput(input: String): String
}
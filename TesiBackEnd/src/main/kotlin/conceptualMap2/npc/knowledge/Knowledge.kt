package conceptualMap2.npc.knowledge

/**
 * This class will describe the knowledge of the NPC about the world, the main event happened in the past, the solicial dynamics of the context in which he lives ecc...
 * @param globalContext describes the world where he lives and its main event
 * @param localContext describes the world he knows and its main event, for example the neighborhood in which he lives
 * @param actualContext describes the situation the NPC is in at the present time
 * @param metaContext info on the game and the player
 */
open class Knowledge(
    private val globalContext: Context, 
    private val localContext: Context,
    private val actualContext: Context,
    private val metaContext: Context
){
    /**
     * @return a map for a better comprehension of the knowledge
     */
    open fun toMap(): Map<String, Any>{
        val map: MutableMap<String, Any> = HashMap()
        map["globalContext"] = globalContext.toMap()
        map["localContext"] = localContext.toMap()
        map["actualContext"] = actualContext.toMap()
        map["metaContext"] = metaContext.toMap()
        return map
    }
}
package esample.medievale.npc.knowledge.actualContext

import conceptualMap2.npc.knowledge.Context

class ActualContext(
    val ongoingWars:List<String>,
    val ongoingAlliances:List<String>,
    val peoplesHappiness: String,
): Context() {
    override fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["ongoingWars"] = ongoingWars
        map["ongoingAlliances"] = ongoingAlliances
        map["peoplesHappiness"] = peoplesHappiness
        map["comments"] = mapOf(
            "ongoingWars" to "descrive le guerre attuali",
            "ongoingAlliances" to "descrive le alleanza attuali",
            "peoplesHappiness" to "descrive la felicità del popolo e le loro preoccupazioni"
        )
        return map
    }
}
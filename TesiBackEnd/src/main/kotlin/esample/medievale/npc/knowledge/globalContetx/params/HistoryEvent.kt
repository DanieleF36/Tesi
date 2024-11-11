package esample.medievale.npc.knowledge.globalContetx.params

class HistoryEvent(
    val description: String,
    val cityName: String,
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["description"] = description
        map["cityName"] = cityName
        map["comments"] = mapOf(
            "cityName" to "ci dice a quale città è legato questa conoscenza",
        )
        return map
    }
}
package esample.medievale.npc.knowledge.globalContetx.params

class LocalHistory(
    val events: List<HistoryEvent>,
    val localMyths: String
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["events"] = events.map { it.toMap() }
        map["localMyths"] = localMyths
        map["comments"] = mapOf(
            "events" to "descrive tutti gli eventi storici che il cittadino conosce, ad esempio battaglie o cambiamenti di potere, famiglie nobiliari e Re delle altre città, inclusa la sua",
            "localMyths" to "leggende su re passati, santi locali e creature soprannaturali"
        )
        return map
    }
}
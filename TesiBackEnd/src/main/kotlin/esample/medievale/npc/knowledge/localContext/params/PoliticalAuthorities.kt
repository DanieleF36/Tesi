package esample.medievale.npc.knowledge.localContext.params

class PoliticalAuthorities(
    val royalFamily: String,
    val kingsHistory: List<String>,
    val kingsAdvisers: String,
    val lordsOfNeighborhoods: List<String>
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["royalFamily"] = royalFamily
        map["kingsHistory"] = kingsHistory
        map["kingsAdvisers"] = kingsAdvisers
        map["lordsOfNeighborhoods"] = lordsOfNeighborhoods
        map["comments"] = mapOf(
            "royalFamily" to "descrive chi è il re attuale, e tutta la sua famiglia",
            "kingsHistory" to "descrive i re precedenti e quanto erano benvoluti dal loro popolo",
            "kingsAdvisers" to "descrive tutti quelli che sono subito sotto il re",
            "lordsOfNeighborhoods" to "descrive i comandanti dei quartieri"
        )
        return map
    }
}
package esample.medievale.npc.knowledge.globalContetx.params

class Religion(
    val name: String,
    val organization: String,
    val description: String,
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["name"] = name
        map["organization"] = organization
        map["description"] = description
        map["comments"] = mapOf(
            "organization" to "ci descrive com'è organizzata la religione, struttura gerarchica, luoghi sacri ecc..",
            "description" to "Ci da una descrizione generale sulla religione"
        )
        return map
    }
}
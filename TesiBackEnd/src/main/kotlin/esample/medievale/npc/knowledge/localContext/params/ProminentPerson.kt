package esample.medievale.npc.knowledge.localContext.params

class ProminentPerson(
    val name: String,
    val heroicGesture: String,
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["name"] = name
        map["heroicGesture"] = heroicGesture
        map["comments"] = mapOf(
            "heroicGesture" to "descrive il gesto che lo ha reso popolare tra le persone"
        )
        return map
    }
}
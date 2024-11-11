package esample.medievale.npc.knowledge.globalContetx.params

class LocalGeography(
    val nearbyCities: String,
    val mainRoutes: String,
) {
    fun toMap(): Map<String, Any>{
        val map = mutableMapOf<String, Any>()
        map["nearbyCities"] = nearbyCities
        map["mainRoutes"] = mainRoutes
        map["comments"] = mapOf(
            "nearbyCities" to "descrive tutte le città vicine",
            "mainRoutes" to "descrive le vie di comunicazione fondamentali, come strade, mulattiere o fiumi, usate per il commercio e il pellegrinaggio"
        )
        return map
    }
}
package esample.medievale.npc.knowledge.localContext.params

class CityOrganization(
    val areas: String,
    val bigness: String,
    val plazas: String,
    val markets: String,
    val cityBoundaries: String,
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["areas"] = areas
        map["bigness"] = bigness
        map["plazas"] = plazas
        map["markets"] = markets
        map["cityBoundaries"] = cityBoundaries
        map["comments"] = mapOf(
            "areas" to "descrive com'è divisa la città dal punto di vista dei quartieri",
            "bigness" to "descrive la grandezza della città",
            "plazas" to "descrive i luoghi dove ci sono le fiere e le piazze principali",
            "markets" to "descrive i principali mercati",
            "cityBoundaries" to "descrive i confini della città"
        )
        return map
    }
}
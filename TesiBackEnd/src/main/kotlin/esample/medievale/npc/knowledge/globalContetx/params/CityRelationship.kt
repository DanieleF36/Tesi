package esample.medievale.npc.knowledge.globalContetx.params

import esample.medievale.link.Relationship

class CityRelationship(
    val cityName: String,
    val relationship: Relationship,
    val descriptions: String
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["cityName"] = cityName
        map["relationship"] = relationship.toString()
        map["descriptions"] = descriptions
        map["comments"] = mapOf(
            "descriptions" to "Qui viene descritta brevemente la storia tra le due città e il perché di questo tipo di relazione"
        )
        return map
    }
}
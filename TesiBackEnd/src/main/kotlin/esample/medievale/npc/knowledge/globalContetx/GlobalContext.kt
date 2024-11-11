package esample.medievale.npc.knowledge.globalContetx

import conceptualMap2.npc.knowledge.Context
import esample.medievale.npc.knowledge.globalContetx.params.CityRelationship
import esample.medievale.npc.knowledge.globalContetx.params.LocalGeography
import esample.medievale.npc.knowledge.globalContetx.params.LocalHistory
import esample.medievale.npc.knowledge.globalContetx.params.Religion

class GlobalContext(
    val localGeography: LocalGeography,
    val localHistory: LocalHistory,
    val religion: Religion,
    val citiesRelationship: List<CityRelationship>
):Context() {
    override fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any>()
        map["localGeography"] = localGeography.toMap()
        map["localHistory"] = localHistory.toMap()
        map["religion"] = religion.toMap()
        map["citiesRelationship"] = citiesRelationship.map { it.toMap() }
        return map
    }
}
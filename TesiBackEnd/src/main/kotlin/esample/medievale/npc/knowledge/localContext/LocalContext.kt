package esample.medievale.npc.knowledge.localContext

import conceptualMap2.npc.knowledge.Context
import esample.medievale.npc.knowledge.localContext.params.CityOrganization
import esample.medievale.npc.knowledge.localContext.params.PoliticalAuthorities
import esample.medievale.npc.knowledge.localContext.params.ProminentPerson

class LocalContext(
    val cityOrganization: CityOrganization,
    val politicalAuthorities: PoliticalAuthorities,
    val prominentPeople: List<ProminentPerson>,
): Context() {
    override fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["cityOrganization"] = cityOrganization.toMap()
        map["politicalAuthorities"] = politicalAuthorities.toMap()
        map["prominentPeople"] = prominentPeople.map { it.toMap() }
        map["comments"] = mapOf(
            "cityOrganization" to "descrive com'è organizzata la città in cui vive il personaggio",
            "politicalAuthorities" to "descrive le autorità politiche della città in cui vive il personaggio",
            "prominentPeople" to "descrive le persone di spicco presenti nella città in cui vive il personaggio",
        )
        return map
    }
}
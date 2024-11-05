package esample.calcio.npc.footballer.context

import conceptualMap2.npc.knowledge.Context

class GlobalContext(
    private val topTeams: List<String>,
    private val topPlayers: List<String>,
    private val topManagers: List<String>,
    private val lastInternationalWinnerTeams: List<String>,
    private val generalDescription: String,
): Context() {
    override fun toMap(): Map<String, Any?> {
        val ret = mutableMapOf<String, Any?>()
        ret["topPlayers"] = topPlayers
        ret["topTeams"] = topTeams
        ret["topManagers"] = topManagers
        ret["lastInternationalWinnerTeams"] = lastInternationalWinnerTeams
        ret["generalDescription"] = generalDescription
        return ret
    }
}
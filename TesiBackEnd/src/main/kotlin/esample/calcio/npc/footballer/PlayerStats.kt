package esample.calcio.npc.footballer

import conceptualMap2.Statistic

/**
 * @param pom player of the match
 * @param art average rating
 */
open class PlayerStats(
    var appearances: Int,
    var goal: Int,
    var assist: Int,
    var pom: Int,
    var art: Float,
    val years: Pair<Int, Int>?
) {

    fun update(stats: PlayerStats) {
        appearances+=stats.appearances
        goal+=stats.goal
        assist+=stats.assist
        pom+=stats.pom
        art = (art*appearances+stats.art*stats.appearances)/(appearances+stats.appearances)
    }

    fun toMap(): Map<String, Any> {
        val ret = mutableMapOf<String, Any>()
        ret["appearances"] = appearances
        ret["goal"] = goal
        ret["assist"] = assist
        ret["pom"] = pom
        ret["art"] = art
        ret["years"] = "${years?.first}/${years?.second}"
        return ret
    }
}
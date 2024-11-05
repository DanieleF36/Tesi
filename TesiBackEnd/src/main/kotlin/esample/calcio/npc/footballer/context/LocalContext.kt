package esample.calcio.npc.footballer.context

import conceptualMap2.npc.knowledge.Context

class LocalContext(
    private val players: List<String>,
    private val staff: List<String>,
    private val executives: List<String>,
): Context() {
    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "players" to players,
            "staff" to staff,
            "executives" to executives,
        )
    }
}
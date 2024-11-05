package esample.calcio.npc.footballer.context

import conceptualMap2.npc.knowledge.Context

class SimpleContext(
    val context: String
): Context() {
    override fun toMap(): Map<String, Any?> {
        return mapOf("context" to context)
    }
}
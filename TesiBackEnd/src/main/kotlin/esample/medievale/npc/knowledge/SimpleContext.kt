package esample.medievale.npc.knowledge

import conceptualMap2.npc.knowledge.Context

class SimpleContext(val context: String): Context() {
    override fun toMap(): Map<String, Any?> {
        return mapOf("context" to context)
    }
}
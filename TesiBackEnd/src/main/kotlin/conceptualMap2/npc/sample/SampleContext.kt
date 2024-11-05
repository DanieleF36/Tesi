package conceptualMap2.npc.sample

import conceptualMap2.npc.knowledge.Context

class SampleContext(private val simpleContext: String): Context() {
    override fun toMap(): Map<String, Any> {
        return mapOf("context" to simpleContext)
    }

}
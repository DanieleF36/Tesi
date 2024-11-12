package conceptualMap2.event.sample

import conceptualMap2.event.EventType

class SampleEventType(name: String): EventType(name) {
    companion object{
        val STRESS: SampleEventType = SampleEventType("stress")
    }

    override fun toMap(): Map<String, Any> {
        return mapOf("description" to "questo evento è generato quando nella conversazione c'è un forte sentimento di stress")
    }

    override fun isPositive(): Int {
        return -1
    }
}
package conceptualMap2.event.sample

import conceptualMap2.event.EventType

class SampleEventType(name: String): EventType(name) {
    companion object{
        val STRESS: SampleEventType = SampleEventType("stress")
        val FRIENDSHIP: SampleEventType = SampleEventType("friendship")
    }
}
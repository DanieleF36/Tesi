package conceptualMap2.event.sample

import conceptualMap2.event.EventImportance

class SampleEventImportance(name: String): EventImportance(name) {
    companion object{
        val CRUCIAL: SampleEventImportance = SampleEventImportance("crucial")
    }
}
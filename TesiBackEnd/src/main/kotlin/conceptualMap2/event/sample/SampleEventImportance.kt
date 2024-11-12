package conceptualMap2.event.sample

import conceptualMap2.event.EventImportance

class SampleEventImportance(name: String): EventImportance(name) {
    companion object{
        val CRUCIAL: SampleEventImportance = SampleEventImportance("crucial")
    }

    override fun toMap(): Map<String, Any> {
        return mapOf("descrizione" to "evento che cambia significativamente il mood dell'NPC")
    }
}
package esample.medievale.link

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.event.ChangeRelationshipLTE
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType

interface WeightContext {
    fun getCounters(): Map<EventType, Map<EventImportance, Int>>
    fun changeRelationshipType(evt: ChangeRelationshipLTE, sender: ConceptualMap)
}
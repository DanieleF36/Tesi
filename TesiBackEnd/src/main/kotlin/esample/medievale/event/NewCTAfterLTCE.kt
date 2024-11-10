package esample.medievale.event

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.LinkType
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.npc.Mood
import java.time.LocalDateTime

//New Common thought after link type change event
class NewCTAfterLTCE(
    type: EventType,
    importance: EventImportance,
    statistic: Mood,
    description: String,
    generatedTime: LocalDateTime,
    val group: ConceptualMap,
    val oldType: LinkType,
    val newType: LinkType,
    val newCT: CommonThought,
    var cntLink: Int = 0
): Event(type, importance, statistic, description, generatedTime) {
}
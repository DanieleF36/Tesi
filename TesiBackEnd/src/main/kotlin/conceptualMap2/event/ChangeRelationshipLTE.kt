package conceptualMap2.event

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.LinkType
import conceptualMap2.npc.Mood
import java.time.LocalDateTime

class ChangeRelationshipLTE(
    type: EventType,
    importance: EventImportance,
    statistic: Mood,
    description: String,
    generatedTime: LocalDateTime,
    val groups: Pair<ConceptualMap, ConceptualMap>,
    val newType: LinkType,
    val positive: Boolean
): Event(type, importance, statistic, description, generatedTime) {
}
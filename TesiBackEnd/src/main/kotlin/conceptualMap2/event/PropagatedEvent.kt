package conceptualMap2.event

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PropagatedEvent(
    type: EventType,
    importance: EventImportance,
    statistic: Mood,
    description: String,
    generatedTime: LocalDateTime,
    val personGenerated: NPC,
    private val from: ConceptualMap,
): Event(type, importance, statistic, description, generatedTime) {
    override fun toString(): String {
        return "Propagated: description='$description', importance=$importance, generatedTime=${generatedTime.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))},  personGenerated=${personGenerated.name}), from=${from.name}"
    }

    override fun toMap(): Map<String, Any?> {
        val ret = super.toMap().toMutableMap()
        ret["from"] = from.name
        return ret.toMap()
    }

    override fun clone(): PropagatedEvent {
        return PropagatedEvent(type, importance, statistic, description, generatedTime, personGenerated, from)
    }
}
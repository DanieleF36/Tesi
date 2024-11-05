package conceptualMap2.event

import conceptualMap2.npc.Mood
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GlobalEvent(
    type: EventType,
    importance: EventImportance,
    statistic: Mood,
    description: String,
    generatedTime: LocalDateTime
): Event(type, importance, statistic, description, generatedTime) {
    override fun toString(): String {
        return "Global: description=$description, type=$type, importance=$importance, generatedTime=${generatedTime.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}"
    }

    override fun toMap(): Map<String, Any?> {
        val ret = super.toMap().toMutableMap()
        ret.remove("personGenerated")
        return ret.toMap()
    }
}
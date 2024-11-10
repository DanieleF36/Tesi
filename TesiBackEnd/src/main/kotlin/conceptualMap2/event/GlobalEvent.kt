package conceptualMap2.event

import conceptualMap2.npc.Mood
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GlobalEvent(
    val type: EventType,
    val description: String,
    generatedTime: LocalDateTime
): AbstractEvent(generatedTime) {
    override fun toString(): String {
        return "Global: description=$description, type=$type, generatedTime=${generatedTime.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}"
    }

    override fun toMap(): Map<String, Any> {
        val ret = mutableMapOf<String, Any>()
        ret["type"] = type.name
        ret["description"] = description
        ret["generatedTime"] = generatedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        return ret.toMap()
    }
}
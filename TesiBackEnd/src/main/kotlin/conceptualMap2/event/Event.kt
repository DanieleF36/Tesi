package conceptualMap2.event

import conceptualMap2.npc.Mood
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @param type this is the typology of the event
 * @param importance this says how much is important this event in the context
 * @param statistic define the statistic that describe the event
 * @param description of what happened in the event
 */
@Serializable
abstract class Event(
    val type: EventType,
    val importance: EventImportance,
    val statistic: Mood,
    val description: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val generatedTime: LocalDateTime
): Cloneable {
    override fun toString(): String {
        return "description=$description, type=$type, importance=$importance, generatedTime=${generatedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}"
    }

    open fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["type"] = type
        map["importance"] = importance
        map["description"] = description
        return map
    }
}
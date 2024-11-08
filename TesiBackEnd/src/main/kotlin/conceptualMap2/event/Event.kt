package conceptualMap2.event

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.npc.Mood
import esample.calcio.conceptualMap.CommonThoughtImpl
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
    val generatedTime: LocalDateTime,
    var linkCnt: Int = 0
): Cloneable {
    override fun toString(): String {
        return "description=$description, type=$type, importance=$importance, generatedTime=${generatedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}"
    }

    open fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["type"] = type
        map["importance"] = importance
        map["description"] = description
        val s = statistic.toMap()
        for((key, value) in map) {
            map.remove(key)
            map["$key change="] = value
        }
        val ct = CommonThoughtImpl(0f,0f,0f)
        ct.update(statistic)
        val c = ct.toMap().toMutableMap()
        for((key, value) in c) {
            c.remove(key)
            c["$key change="] = value
        }
        map["impact"] = mapOf<String, Any>("mood" to s, "CommonThought" to c)
        return map
    }
}
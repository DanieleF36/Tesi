package conceptualMap2.event

import conceptualMap2.npc.NPC
import conceptualMap2.Statistic
import conceptualMap2.npc.Mood

/**
 * @param type this is the typology of the event
 * @param importance this says how much is important this event in the context
 * @param statistic define the statistic that describe the event
 * @param description of what happened in the event
 */
abstract class Event(
    val type: EventType,
    val importance: EventImportance,
    val statistic: Mood,
    val description: String,
): Cloneable {
    override fun toString(): String {
        return "description=$description, type=$type, importance=$importance"
    }

    open fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["type"] = type
        map["importance"] = importance
        map["description"] = description
        return map
    }
}
package conceptualMap2.event

import conceptualMap2.npc.NPC
import conceptualMap2.Statistic

/**
 * @param type this is the typology of the event
 * @param importance this says how much is important this event in the context
 * @param statistic define the statistic that describe the event
 * @param description of what happened in the event
 * @param personGenerated this is the person involved in generating the event with the player
 */
open class Event(
    val type: EventType,
    val importance: EventImportance,
    val statistic: Statistic,
    val description: String,
    var personGenerated: NPC? = null,
): Cloneable {
    override fun toString(): String {
        return "description='$description', importance=$importance, personGenerated=${personGenerated?.name}"
    }

    open fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["type"] = type
        map["importance"] = importance
        map["description"] = description
        map["personGenerated"] = personGenerated
        return map
    }

    public override fun clone(): Event {
        return Event(
            type,
            importance,
            statistic.clone(),
            description,
            personGenerated,
        )
    }
}
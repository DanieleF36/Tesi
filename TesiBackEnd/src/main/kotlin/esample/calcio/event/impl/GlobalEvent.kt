package esample.calcio.event.impl

import conceptualMap2.Statistic
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType

class GlobalEvent(
    type: EventType,
    importance: EventImportance,
    statistic: Statistic,
    description: String
): Event(type, importance, statistic, description) {
    override fun toString(): String {
        return "description='$description', importance=$importance"
    }

    override fun toMap(): Map<String, Any?> {
        val ret = super.toMap().toMutableMap()
        ret.remove("personGenerated")
        return ret.toMap()
    }
}
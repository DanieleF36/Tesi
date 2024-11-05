package conceptualMap2.event

import conceptualMap2.npc.Mood

class GlobalEvent(
    type: EventType,
    importance: EventImportance,
    statistic: Mood,
    description: String
): Event(type, importance, statistic, description) {
    override fun toString(): String {
        return "Global: description=$description, type=$type, importance=$importance"
    }

    override fun toMap(): Map<String, Any?> {
        val ret = super.toMap().toMutableMap()
        ret.remove("personGenerated")
        return ret.toMap()
    }
}
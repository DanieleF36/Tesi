package conceptualMap2.event

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC

class PropagatedEvent(
    type: EventType,
    importance: EventImportance,
    statistic: Mood,
    description: String,
    val personGenerated: NPC,
    private val from: ConceptualMap,
): Event(type, importance, statistic, description) {
    override fun toString(): String {
        return "Propagated: description='$description', importance=$importance, personGenerated=${personGenerated.name}), from=${from.name}"
    }

    override fun toMap(): Map<String, Any?> {
        val ret = super.toMap().toMutableMap()
        ret["from"] = from.name
        return ret.toMap()
    }
}
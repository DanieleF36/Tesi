package esample.calcio.event.impl

import conceptualMap2.ConceptualMap
import conceptualMap2.Statistic
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.npc.NPC

class PropagatedEvent(
    type: EventType,
    importance: EventImportance,
    statistic: Statistic,
    description: String,
    personGenerated: NPC?,
    private val from: ConceptualMap,
): Event(type, importance, statistic, description, personGenerated) {
    override fun toString(): String {
        return "description='$description', importance=$importance, personGenerated=${personGenerated?.name}), from=${from.name}"
    }

    override fun toMap(): Map<String, Any?> {
        val ret = super.toMap().toMutableMap()
        ret["from"] = from.name
        return ret.toMap()
    }
}
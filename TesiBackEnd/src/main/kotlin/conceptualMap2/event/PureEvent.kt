package conceptualMap2.event

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PureEvent(
    type: EventType,
    importance: EventImportance,
    statistic: Mood,
    description: String,
    generatedTime: LocalDateTime,
    var personGenerated: Pair<NPC, NPC>?,
    //It can be: personGenerated's group or null if it was generated in a neutral place
    val generationPlace: ConceptualMap?
): Event(type, importance, statistic, description, generatedTime) {
    override fun toString(): String {
        return "Pure: description=$description, type=$type, importance=$importance, generatedTime=${generatedTime.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}, personGenerated1=${personGenerated!!.first.name}, personGenerated2=${personGenerated!!.second.name}, generatedPlace=${generationPlace?.name}"
    }

    override fun toMap(): Map<String, Any> {
        val ret = super.toMap().toMutableMap()
        ret["personGenerated1"] = personGenerated!!.first.name!!
        ret["personGenerated2"] = personGenerated!!.second.name!!
        return ret
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Event) return false

        return if(other is PureEvent)
            super.equals(other) && personGenerated==other.personGenerated && generatedTime==other.generatedTime
        else
            super.equals(other)
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun clone(): Any {
        return PureEvent(type, importance, statistic, description, generatedTime, personGenerated, generationPlace)
    }
}
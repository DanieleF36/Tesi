package conceptualMap2.event

import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * this class is thought for all events generated from a person
 * @param personGenerated this is the person involved in generating the event with the player
 */
class LocalEvent(
    type: EventType,
    importance: EventImportance,
    statistic: Mood,
    description: String,
    generatedTime: LocalDateTime,
    val personGenerated: NPC
    ) : Event(type, importance, statistic, description, generatedTime) {

    override fun toString(): String {
        return "Local: description=$description, type=$type, importance=$importance, generatedTime=${generatedTime.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}, personGenerated=${personGenerated.name}"
    }

    override fun toMap(): Map<String, Any?> {
        val ret = super.toMap().toMutableMap()
        ret["personGenerated"] = personGenerated
        return ret
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Event) return false

        return if(other is LocalEvent)
            super.equals(other) && personGenerated==other.personGenerated
        else
            super.equals(other)
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun clone(): Any {
        return LocalEvent(type, importance, statistic, description, generatedTime, personGenerated)
    }
}
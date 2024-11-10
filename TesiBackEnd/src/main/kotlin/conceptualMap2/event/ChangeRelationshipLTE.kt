package conceptualMap2.event

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.LinkType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChangeRelationshipLTE(
    generatedTime: LocalDateTime,
    val groups: Pair<ConceptualMap, ConceptualMap>,
    val oldType: LinkType,
    val newType: LinkType
): AbstractEvent(generatedTime) {
    override fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["groups"] = "${groups.first.name}, ${groups.second.name}"
        map["oldType"] = oldType.toMap()
        map["newType"] = newType.toMap()
        map["description"] = "Questo evento serve per descrivere un variamento nella relazione tra i groups, che passa da oldType a newType"
        return map.toMap()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChangeRelationshipLTE) return false

        return generatedTime == other.generatedTime && groups == other.groups && oldType == other.oldType && newType == other.newType
    }

    override fun toString(): String {
        return "ChangeRelationshipLTE(groups: $groups, oldType: $oldType, newType: $newType, generatedTime: ${generatedTime.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))})"
    }

    override fun hashCode(): Int {
        var result = groups.hashCode()
        result = 31 * result + oldType.hashCode()
        result = 31 * result + newType.hashCode()
        return result
    }
}
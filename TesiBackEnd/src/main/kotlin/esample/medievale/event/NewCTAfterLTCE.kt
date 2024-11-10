package esample.medievale.event

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.conceptualMap.LinkType
import conceptualMap2.event.AbstractEvent
import java.time.LocalDateTime

//New Common thought after link type change event
class NewCTAfterLTCE(
    generatedTime: LocalDateTime,
    val groupName: String,
    val oldType: LinkType,
    val newType: LinkType,
    val newCT: CommonThought,
    var cntLink: Int = 0
): AbstractEvent(generatedTime) {
    override fun toMap(): Map<String, Any> {
        val ret = mutableMapOf<String, Any>()
        ret["generatedTime"] = generatedTime
        ret["groupName"] = groupName
        ret["oldType"] = oldType
        ret["newType"] = newType
        ret["newCommonThought"] = newCT.toMap()
        return ret.toMap()
    }
}
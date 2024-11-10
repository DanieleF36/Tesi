package esample.medievale.link

import conceptualMap2.conceptualMap.CommunicationLevel
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Link
import conceptualMap2.conceptualMap.LinkType
import conceptualMap2.event.Event
import java.time.Duration

class BidirectionalLink(
    a: ConceptualMap,
    val b: ConceptualMap,
    _type: LinkType,
    _communicationLvl: CommunicationLevel
): Link(a, _type, _communicationLvl) {
    override fun propagate(event: Event) {
        TODO("Not yet implemented")
    }

    override fun computeTime(event: Event): Duration {
        TODO("Not yet implemented")
    }
}
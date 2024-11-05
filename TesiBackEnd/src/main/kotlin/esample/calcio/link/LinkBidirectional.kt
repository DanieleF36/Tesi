package esample.calcio.link

import conceptualMap2.ConceptualMap
import conceptualMap2.Link
import conceptualMap2.event.Event

/**
 * @param a the link through the other node
 * @param weight is a function that act on event statistics updating them according some criteria
 * @param filter all the event that must be propagated or not. Return true if the event is filtered and must be stopped from propagation
 */
class LinkBidirectional(
    a: ConceptualMap,
    val weight: (event: Event) -> Event,
    val filter: (event: Event) -> Boolean
): Link(a) {
    override fun propagate(event: Event) {
        if(!filter(event))
            a.receiveEvent(weight(event))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LinkBidirectional) return false
        return a != other.a
    }

    override fun hashCode(): Int {
        return a.hashCode()
    }
}
package iterations.second.event

import iterations.second.ConceptualMap
import iterations.second.Event
import iterations.second.EventType
import iterations.second.Statistic

class SimpleEvent(
    type: EventType,
    statistic: Statistic,
    /** the number of link that this event can go through */
    propagationRange: Int = 1,
    description: String,
) : Event(type, statistic, propagationRange, description){
    override fun isPropagable(node: ConceptualMap): Boolean {
        if(propagationRange==0 || visited.contains(node))
            return false
        propagationRange--
        visited.add(node)
        return true
    }
}
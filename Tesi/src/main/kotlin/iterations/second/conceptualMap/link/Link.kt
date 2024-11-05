package iterations.second.conceptualMap.link

import iterations.second.ConceptualMap
import iterations.second.Event
import iterations.second.Weight

internal class Link(
    val node: ConceptualMap,
    private val weight: Weight
){
    /**
     * This function will propagate the event through b adding the statistic
     */
    fun propagate(event: Event){
        event.propagationRange--
        weight.update(event)
        node.generateEvent(event)
    }
}
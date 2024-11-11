package conceptualMap2.conceptualMap

import conceptualMap2.event.AbstractEvent
import conceptualMap2.event.Event
import java.time.Duration

abstract class Link(
    protected var _type: LinkType,
){
    val linkType: LinkType
        get() = _type

    abstract fun propagate(event: AbstractEvent, sender: ConceptualMap)

    protected abstract fun computeTime(event: AbstractEvent): Duration
}
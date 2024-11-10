package conceptualMap2.conceptualMap

import conceptualMap2.event.Event
import java.time.Duration

abstract class Link(
    protected var _type: LinkType,
){
    val linkType: LinkType
        get() = _type

    abstract fun propagate(event: Event, sender: ConceptualMap)

    protected abstract fun computeTime(event: Event): Duration
}
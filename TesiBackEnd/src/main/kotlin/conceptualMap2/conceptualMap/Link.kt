package conceptualMap2.conceptualMap

import conceptualMap2.event.Event
import java.time.Duration

abstract class Link(
    val a: ConceptualMap,
    protected var _type: LinkType,
    protected var _communicationLvl: CommunicationLevel
){
    val communicationLvl: CommunicationLevel
        get() = _communicationLvl
    val linkType: LinkType
        get() = _type

    abstract fun propagate(event: Event)

    protected abstract fun computeTime(event: Event): Duration
}
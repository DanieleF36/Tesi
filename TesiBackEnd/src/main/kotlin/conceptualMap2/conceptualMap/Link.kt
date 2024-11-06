package conceptualMap2.conceptualMap

import conceptualMap2.event.Event
import java.time.Duration

abstract class Link(
    val a: ConceptualMap,
    val influenceType: InfluenceType,
    val communicationLvl: CommunicationLevel
){
    abstract fun propagate(event: Event)

    protected abstract fun computeTime(event: Event): Duration
}
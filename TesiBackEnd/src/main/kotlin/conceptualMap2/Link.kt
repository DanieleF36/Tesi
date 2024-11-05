package conceptualMap2

import conceptualMap2.event.Event

abstract class Link(val a: ConceptualMap){
    abstract fun propagate(event: Event)
}
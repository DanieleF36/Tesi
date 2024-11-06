package conceptualMap2.conceptualMap

import conceptualMap2.event.Event

class PropagateEventWhen(
    val event: Event,
    val condition: ()-> Boolean,
    val propagate: ()->Unit
) {
    fun check() {
        if (condition())
            propagate()
    }
}
package conceptualMap2.conceptualMap

import conceptualMap2.event.Event

class PropagateEventWhen(
    val event: Event,
    val condition: ()-> Boolean,
    val propagate: (PropagateEventWhen)->Unit
) {
    fun check() {
        if (condition())
            propagate(this)
    }

    override fun hashCode(): Int {
        var result = event.hashCode()
        result = 31 * result + condition.hashCode()
        result = 31 * result + propagate.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PropagateEventWhen

        if (event != other.event) return false
        if (condition != other.condition) return false
        if (propagate != other.propagate) return false

        return true
    }
}
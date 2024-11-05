package iterations.second

enum class EventType {
    POSITIVE,
    NEUTRAL,
    NEGATIVE
}

enum class EventPriority{
    TRIVIAL,
    MODERATE,
    CRUCIAL
}

abstract class Event(
    val type: EventType,
    val priority: EventPriority,
    val statistic: Statistic,
    /** the number of link that this event can go through */
    var propagationRange: Int = 1,
    /** time needed to propagate this event within the group */
    val propagationTimeInside: TimeComponent,
    /** time needed to propagate this event outside the group */
    val propagationTimeOutside: TimeComponent,
    val description: String,
){
    protected val visited: MutableSet<ConceptualMap> = mutableSetOf()
    /** This function will return True if is possible to propagate this event to a specific node of the conceptual map */
    fun isPropagable(node: ConceptualMap): Boolean {
        return propagationRange == 0
    }
}
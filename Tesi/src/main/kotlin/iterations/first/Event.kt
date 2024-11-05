package iterations.first

interface EventType {
    val name: String
}

interface EventImportance{
    val name: String
}

abstract class Event(
    val type: EventType,
    val importance: EventImportance,
    val statistic: Statistic,
    /** the number of link that this event can go through */
    protected var propagationRange: Int = 1,
    val description: String,
){
    protected val visited: MutableSet<ConceptualMap> = mutableSetOf()
    /** This function will return True if is possible to propagate this event to a specific node of the conceptual map */
    abstract fun isPropagable(node: ConceptualMap): Boolean
}
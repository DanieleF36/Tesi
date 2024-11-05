package iterations.first

abstract class Link(
    val node: ConceptualMap,
    val weight: Weight
){
    /**
     * This function will propagate the event through b adding the statistic
     */
    abstract fun propagate(event: Event)
}
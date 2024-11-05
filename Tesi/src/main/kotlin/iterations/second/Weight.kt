package iterations.second

abstract class Weight {
    /**
     * This function will modify the event by applying the weight
     */
    abstract fun update(event: Event)
}
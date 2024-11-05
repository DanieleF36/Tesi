package iterations.first

/**
 * This class will be extended in order to create all the statistics needed to describe something, for example the common
 * taught or the personality of a npc
 */
abstract class Statistic{
    abstract fun update(stats: Statistic)
    /**
     * This function will return a deep copy of the object
     */
    abstract fun clone(): Statistic
}
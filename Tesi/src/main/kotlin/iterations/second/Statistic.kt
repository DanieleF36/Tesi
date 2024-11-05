package iterations.second

/**
 * This class will be extended in order to create all the statistics needed to describe something, for example the common
 * taught or the personality of a npc
 */
abstract class Statistic{
    abstract fun update(stats: Statistic)
    /**
     * Simple function that invert all the stats, for example if they are integer it will multiply them for -1
     */
    abstract fun invert()
    /**
     * This function will return a deep copy of the object
     */
    abstract fun clone(): Statistic
}
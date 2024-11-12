package conceptualMap2.event

import conceptualMap2.event.sample.SampleEventType
/**
 * This class has to be immutable
 * The idea is that this will be implemented by a class with only static attribute, i.e all the event types.
 * @sample SampleEventType
 */
@kotlinx.serialization.Serializable
abstract class EventType(val name: String){
    override fun toString(): String {
        return name
    }
    abstract fun toMap(): Map<String, Any>
    /**
     * @return -1 negative, 0 neutral and 1 positive
     */
    abstract fun isPositive(): Int
}


package conceptualMap2.event
import conceptualMap2.event.sample.SampleEventImportance

/**
 * This class has to be immutable
 * The idea is that this will be implemented by a class with only static attribute, i.e all the event types.
 * @sample SampleEventImportance
 */
@kotlinx.serialization.Serializable
abstract class EventImportance(val name: String){
    override fun toString(): String {
        return name
    }
    abstract fun toMap(): Map<String, Any>
}
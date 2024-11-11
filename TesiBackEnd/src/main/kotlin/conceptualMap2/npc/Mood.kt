package conceptualMap2.npc

import kotlinx.serialization.Serializable

/**
 * this class must be unmodifiable
 */
@Serializable
abstract class Mood(
    val happiness: Float, //if <0 this is considered sadness
    val stress: Float,
    val anger: Float,
): Cloneable {

    abstract fun update(stats: Mood): Mood

    open fun toMap(): Map<String, Any> {
        val ret = mutableMapOf<String, Any>()
        ret["happiness"] = happiness
        ret["stress"] = stress
        ret["anger"] = anger
        return ret
    }
}
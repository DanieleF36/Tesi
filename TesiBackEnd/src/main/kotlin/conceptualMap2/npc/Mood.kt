package conceptualMap2.npc

import kotlinx.serialization.Serializable

/**
 * this class must be unmodifiable
 */
@Serializable
abstract class Mood(
    protected var _satisfaction: Float, //if <0 this is considered disappointment
    protected var _stress: Float,
    protected var _anger: Float,
): Cloneable {
    val satisfaction: Float
        get() = _satisfaction
    val stress: Float
        get() = _stress
    val anger: Float
        get() = _anger

    abstract fun update(stats: Mood): Mood

    open fun toMap(): Map<String, Any> {
        val ret = mutableMapOf<String, Any>()
        ret["satisfaction"] = _satisfaction
        ret["stress"] = _stress
        ret["anger"] = _anger
        return ret
    }
}
package conceptualMap2.conceptualMap

import conceptualMap2.npc.Mood

abstract class CommonThought(
    protected var _affection: Float,
    protected var _respect: Float,
    protected var _angry: Float,
): Cloneable{
    val affection: Float
        get() = _affection
    val respect: Float
        get() = _respect
    val angry: Float
        get() = _angry

    fun update(stats: CommonThought){
        _affection+=stats._affection
        _respect+=stats._respect
        _angry+=stats._angry
    }

    abstract fun update(stats: Mood)

    open fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["affection"] = _affection
        map["respect"] = _respect
        map["hungry"] = _angry
        return map
    }

    override fun toString(): String {
        return "CommonThoughtImpl.kt(_affection=$_affection, _respect=$_respect, _angry=$_angry)"
    }
}
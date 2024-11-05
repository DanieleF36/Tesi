package esample.calcio.conceptualMap

import conceptualMap2.Statistic
import esample.calcio.npc.footballer.personality.SimpleMood

class CommonThought(
    private var _affection: Float,
    private var _respect: Float,
    private var _angry: Float,
):Statistic {
    val affection: Float
        get() = _affection
    val respect: Float
        get() = _respect
    val angry: Float
        get() = _angry

    override fun update(stats: Statistic) {
        when(stats) {
            is CommonThought -> updateCT(stats)
            is SimpleMood -> TODO()
            else -> throw IllegalArgumentException("stats must be of type CommonThought or SimpleMood")
        }
        statCheck()
    }

    private fun updateCT(stats: CommonThought){
        _affection+=stats._affection
        _respect+=stats._respect
        _angry+=stats._angry
    }

    private fun statCheck() {
        _affection.coerceIn(-1f,1f)
        _respect.coerceIn(-1f,1f)
        _angry.coerceIn(-1f,1f)
    }

    override fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["affection"] = _affection
        map["respect"] = _respect
        map["hungry"] = _angry
        return map
    }

    override fun clone(): Statistic {
        return CommonThought(_affection, _respect, _angry)
    }

    override fun toString(): String {
        return "CommonThought(_affection=$_affection, _respect=$_respect, _angry=$_angry)"
    }
}
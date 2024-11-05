package esample.calcio.npc.footballer.personality;

import conceptualMap2.Statistic
import esample.calcio.conceptualMap.CommonThought
import kotlinx.serialization.Serializable

@Serializable
class SimpleMood(
    private var _satisfaction: Float, //if <0 this is considered disappointment
    private var _stress: Float,
    private var _anger: Float,
): Statistic {
    val satisfaction: Float
        get() = _satisfaction
    val stress: Float
        get() = _stress
    val anger: Float
        get() = _anger
    init {
        if(_satisfaction > 0f && _anger > 0f) throw IllegalArgumentException("Positive satisfaction && anger")
        if(_satisfaction < 0f && _anger < 0f) throw IllegalArgumentException("Negative satisfaction && anger")
    }

    override fun update(stats: Statistic) {
        when(stats) {
            is SimpleMood -> updateSM(stats)
            else -> throw IllegalArgumentException("stats must be of type CommonThought or SimpleMood")
        }

        statCheck()
    }


    private fun updateSM(stats: SimpleMood){
        _stress += stats._stress
        _anger += stats._anger
        _satisfaction += stats._satisfaction
    }

    private fun statCheck(){
        _satisfaction.coerceIn(-1f, 1f)
        _stress.coerceIn(-1f, 1f)
        _anger.coerceIn(-1f, 1f)
    }

    override fun toMap(): Map<String, Any> {
        val ret = mutableMapOf<String, Any>()
        ret["satisfaction"] = _satisfaction
        ret["stress"] = _stress
        ret["anger"] = _anger
        return ret
    }
    override fun clone(): Statistic {
        return SimpleMood(_satisfaction, _stress, _anger)
    }

    override fun toString(): String {
        return "_anger=$_anger, _satisfaction=$_satisfaction, _stress=$_stress"
    }
}

package esample.calcio.conceptualMap

import conceptualMap2.Statistic
import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.npc.Mood
import esample.calcio.npc.footballer.personality.SimpleMood
import observerInterfaces.Observer

class CommonThoughtImpl(
    _affection: Float,
    _respect: Float,
    _angry: Float,
):CommonThought(_affection, _respect, _angry) {
    override fun update(stats: Mood) {
        TODO()
    }

    private fun statCheck() {
        _affection.coerceIn(-1f,1f)
        _respect.coerceIn(-1f,1f)
        _angry.coerceIn(-1f,1f)
    }

    override fun clone(): CommonThought {
        return CommonThoughtImpl(_affection, _respect, _angry)
    }


}
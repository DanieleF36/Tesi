package esample.calcio.npc.footballer.personality;

import conceptualMap2.npc.Mood
class SimpleMood(
    _satisfaction: Float, //if <0 this is considered disappointment
    _stress: Float,
    _anger: Float,
): Mood(_satisfaction, _stress, _anger) {

    init {
        if (_satisfaction > 0f && _anger > 0f) throw IllegalArgumentException("Positive satisfaction && anger")
        if (_satisfaction < 0f && _anger < 0f) throw IllegalArgumentException("Negative satisfaction && anger")
    }

    override fun update(stats: Mood): Mood {
        return SimpleMood(
            _satisfaction + stats.satisfaction,
            _stress + stats.stress,
            _anger + stats.anger
        )
    }

    private fun statCheck() {
        _satisfaction.coerceIn(-1f, 1f)
        _stress.coerceIn(-1f, 1f)
        _anger.coerceIn(-1f, 1f)
    }

    override fun clone(): Mood {
        return SimpleMood(_satisfaction, _stress, _anger)
    }

    override fun toString(): String {
        return "_anger=$_anger, _satisfaction=$_satisfaction, _stress=$_stress"
    }
}

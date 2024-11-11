package esample.medievale

import conceptualMap2.npc.Mood

class SimpleMood(
    satisfaction: Float, //if <0 this is considered disappointment
    stress: Float,
    anger: Float,
): Mood(satisfaction, stress, anger) {
    override fun update(stats: Mood): Mood {
        return SimpleMood(
            happiness + stats.happiness,
            stress + stats.stress,
            anger + stats.anger,
        )
    }
}
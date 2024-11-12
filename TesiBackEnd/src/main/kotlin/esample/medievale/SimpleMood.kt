package esample.medievale

import conceptualMap2.npc.Mood

class SimpleMood(
    happiness: Float, //if <0 this is considered sadness
    stress: Float,
    anger: Float,
): Mood(happiness, stress, anger) {
    override fun update(stats: Mood): Mood {
        return SimpleMood(
            happiness + stats.happiness,
            stress + stats.stress,
            anger + stats.anger,
        )
    }
}
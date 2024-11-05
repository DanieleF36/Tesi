package esample.calcio.npc.footballer.personality

import conceptualMap2.npc.Mood
import observerInterfaces.Observer

class WeighedMood(
    _satisfaction: Float,
    _stress: Float,
    _anger: Float,
): Mood(_satisfaction, _stress, _anger) {
    private val observers = mutableSetOf<Observer>()
    override fun update(stats: Mood): Mood {
        return WeighedMood(
            stats.satisfaction*_satisfaction,
            stats.stress*_stress,
            stats.anger*_anger
        )
    }

    override fun clone(): Mood {
        return WeighedMood(_satisfaction, _stress, _anger)
    }
}
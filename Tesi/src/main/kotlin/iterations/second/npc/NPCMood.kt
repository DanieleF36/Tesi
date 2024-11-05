package iterations.second.npc

import iterations.second.Statistic
import iterations.second.conceptualMap.CommonTaught

class NPCMood(
    private var _happiness: Int,
    private var _anxiety: Int,
    private var _calm: Int,
    private var _frustration: Int,
    private var _stress: Int,
    private var _confidence: Int,
    private var _loneliness: Int,
    private var _anger: Int,
    private var _sadness: Int,
    private var _enthusiasm: Int
) : Statistic() {
    var happiness: Int
        get() = _happiness
        set(value) {
            if (value > 10) throw IllegalArgumentException("Happiness must be at max 10")
            _happiness = value
        }

    var anxiety: Int
        get() = _anxiety
        set(value) {
            if (value > 10) throw IllegalArgumentException("Anxiety must be at max 10")
            _anxiety = value
        }

    var calm: Int
        get() = _calm
        set(value) {
            if (value > 10) throw IllegalArgumentException("Calm must be at max 10")
            _calm = value
        }

    var frustration: Int
        get() = _frustration
        set(value) {
            if (value > 10) throw IllegalArgumentException("Frustration must be at max 10")
            _frustration = value
        }

    var stress: Int
        get() = _stress
        set(value) {
            if (value > 10) throw IllegalArgumentException("Stress must be at max 10")
            _stress = value
        }

    var confidence: Int
        get() = _confidence
        set(value) {
            if (value > 10) throw IllegalArgumentException("confidence must be at max 10")
            _confidence = value
        }

    var loneliness: Int
        get() = _loneliness
        set(value) {
            if (value > 10) throw IllegalArgumentException("Loneliness must be at max 10")
            _loneliness = value
        }

    var anger: Int
        get() = _anger
        set(value) {
            if (value > 10) throw IllegalArgumentException("Anger must be at max 10")
            _anger = value
        }

    var sadness: Int
        get() = _sadness
        set(value) {
            if (value > 10) throw IllegalArgumentException("Sadness must be at max 10")
            _sadness = value
        }

    var enthusiasm: Int
        get() = _enthusiasm
        set(value) {
            if (value > 10) throw IllegalArgumentException("Enthusiasm must be at max 10")
            _enthusiasm = value
        }

    override fun update(stats: Statistic) {
        when (stats) {
            is NPCMood -> statConvertNPCMood(stats)
            is CommonTaught -> statConvertCommonTaught(stats)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    private fun statConvertNPCMood(s: NPCMood) {
        _happiness += s.happiness
        _anxiety += s.anxiety
        _calm += s.calm
        _frustration += s.frustration
        _stress += s.stress
        _confidence += s.confidence
        _loneliness += s.loneliness
        _anger += s.anger
        _sadness += s.sadness
        _enthusiasm += s.enthusiasm
    }

    private fun statConvertCommonTaught(s: CommonTaught) {
        _happiness += s.respect
        _calm += s.trust
        _enthusiasm += s.affection
        _anxiety += s.suspicion
        _confidence += s.admiration
        _loneliness -= s.friendship
        _anger += s.anger

        statCheck()
    }

    private fun statCheck() {
        _happiness = _happiness.coerceIn(0, 10)
        _calm = _calm.coerceIn(0, 10)
        _enthusiasm = _enthusiasm.coerceIn(0, 10)
        _anxiety = _anxiety.coerceIn(0, 10)
        _confidence = _confidence.coerceIn(0, 10)
        _loneliness = _loneliness.coerceIn(0, 10)
        _anger = _anger.coerceIn(0, 10)
    }

    override fun invert() {
        _happiness *= -1
        _anxiety *= -1
        _calm *= -1
        _frustration *= -1
        _stress *= -1
        _confidence *= -1
        _loneliness *= -1
        _anger *= -1
        _sadness *= -1
        _enthusiasm *= -1
    }

    override fun clone(): Statistic {
        return NPCMood(
            _happiness, _anxiety, _calm, _frustration, _stress, _confidence,
            _loneliness, _anger, _sadness, _enthusiasm
        )
    }
}

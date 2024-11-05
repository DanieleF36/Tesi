package iterations.first.conceptualMap

import iterations.first.Statistic

class CommonTaught(
    private var _respect: Int,
    private var _trust: Int,
    private var _affection: Int,
    private var _suspicion: Int,
    private var _admiration: Int,
    private var _friendship: Int,
    private var _anger: Int
): Statistic() {
    var respect: Int
        get() = _respect
        set(value) {
            if (value > 10) throw IllegalArgumentException("Respect must be at max 10")
            _respect = value
        }

    var trust: Int
        get() = _trust
        set(value) {
            if (value > 10) throw IllegalArgumentException("Trust must be at max 10")
            _trust = value
        }

    var affection: Int
        get() = _affection
        set(value) {
            if (value > 10) throw IllegalArgumentException("Affection must be at max 10")
            _affection = value
        }

    var suspicion: Int
        get() = _suspicion
        set(value) {
            if (value > 10) throw IllegalArgumentException("Suspicion must be at max 10")
            _suspicion = value
        }

    var admiration: Int
        get() = _admiration
        set(value) {
            if (value > 10) throw IllegalArgumentException("Admiration must be at max 10")
            _admiration = value
        }

    var friendship: Int
        get() = _friendship
        set(value) {
            if (value > 10) throw IllegalArgumentException("Friendship must be at max 10")
            _friendship = value
        }

    var anger: Int
        get() = _anger
        set(value) {
            if (value > 10) throw IllegalArgumentException("Anger must be at max 10")
            _anger = value
        }

    override fun update(stats: Statistic){
        when(stats){
            is CommonTaught -> statConvertCommonTaught(stats)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    private fun statConvertCommonTaught(s: CommonTaught){
        _respect+=s.respect
        _trust+=s._trust
        _affection+=s.affection
        _suspicion+=s.suspicion
        _admiration+=s.admiration
        _friendship+=s.friendship
        _anger+=s.anger
    }

    override fun clone(): Statistic {
        return CommonTaught(_respect, _trust, _affection, _suspicion, _admiration, _friendship, _anger)
    }
}
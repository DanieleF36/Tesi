package iterations.second.npc

import iterations.second.Statistic

class NPCPersonality(
    private var _courage: Int,
    private var _determination: Int,
    private var _empathy: Int,
    private var _optimism: Int,
    private var _selfControl: Int,
    private var _sociability: Int,
    private var _leadership: Int,
    private var _loyalty: Int,
    private var _creativity: Int,
    private var _adaptability: Int,
    private var _kindness: Int,
    private var _ego: Int,
    private var _ambition: Int
): Statistic() {
    var courage: Int
        get() = _courage
        set(value) {
            if (value > 10) throw IllegalArgumentException("Courage must be at max 10")
            _courage = value
        }

    var determination: Int
        get() = _determination
        set(value) {
            if (value > 10) throw IllegalArgumentException("Determination must be at max 10")
            _determination = value
        }

    var empathy: Int
        get() = _empathy
        set(value) {
            if (value > 10) throw IllegalArgumentException("Empathy must be at max 10")
            _empathy = value
        }

    var optimism: Int
        get() = _optimism
        set(value) {
            if (value > 10) throw IllegalArgumentException("Optimism must be at max 10")
            _optimism = value
        }

    var selfControl: Int
        get() = _selfControl
        set(value) {
            if (value > 10) throw IllegalArgumentException("Self-control must be at max 10")
            _selfControl = value
        }

    var sociability: Int
        get() = _sociability
        set(value) {
            if (value > 10) throw IllegalArgumentException("Sociability must be at max 10")
            _sociability = value
        }

    var leadership: Int
        get() = _leadership
        set(value) {
            if (value > 10) throw IllegalArgumentException("Leadership must be at max 10")
            _leadership = value
        }

    var loyalty: Int
        get() = _loyalty
        set(value) {
            if (value > 10) throw IllegalArgumentException("Loyalty must be at max 10")
            _loyalty = value
        }

    var creativity: Int
        get() = _creativity
        set(value) {
            if (value > 10) throw IllegalArgumentException("Creativity must be at max 10")
            _creativity = value
        }

    var adaptability: Int
        get() = _adaptability
        set(value) {
            if (value > 10) throw IllegalArgumentException("Adaptability must be at max 10")
            _adaptability = value
        }

    var kindness: Int
        get() = _kindness
        set(value) {
            if (value > 10) throw IllegalArgumentException("Kindness must be at max 10")
            _kindness = value
        }

    var ego: Int
        get() = _ego
        set(value) {
            if (value > 10) throw IllegalArgumentException("Ego must be at max 10")
            _ego = value
        }

    var ambition: Int
        get() = _ambition
        set(value) {
            if (value > 10) throw IllegalArgumentException("Ambition must be at max 10")
            _ambition = value
        }

    override fun clone(): Statistic {
        return NPCPersonality(_courage, _determination, _empathy, _optimism, _selfControl, _sociability, _leadership, _loyalty, _creativity, _adaptability, _kindness, _ego, _ambition)
    }

    override fun update(stats: Statistic) {
        when(stats){
            is NPCPersonality -> statConvertNPCPersonality(stats)
            //is CommonTaught -> statConvertCommonTaught(stats)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    private fun statConvertNPCPersonality(s: NPCPersonality){
        this._ego+=s.ego
        this._courage+=s.courage
        this._empathy+=s.empathy
        this._adaptability+=s.adaptability
        this._ambition+=s.ambition
        this._creativity+=s.creativity
        this._determination+=s.determination
        this._kindness+=s.kindness
        this._leadership+=s.leadership
        this._loyalty+=s.loyalty
        this._optimism+=s.optimism
        this._selfControl+=s.selfControl
        this._sociability+=s.sociability
    }

    override fun invert() {
        this._ego *= -1
        this._courage *= -1
        this._empathy *= -1
        this._adaptability *= -1
        this._ambition *= -1
        this._creativity *= -1
        this._determination *= -1
        this._kindness *= -1
        this._leadership *= -1
        this._loyalty *= -1
        this._optimism *= -1
        this._selfControl *= -1
        this._sociability *= -1
    }
}
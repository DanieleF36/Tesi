package conceptualMap2.conceptualMap

import conceptualMap2.npc.Mood

abstract class CommonThought(
    protected var _paura: Float, //Timore di minacce o aggressioni.
    protected var _fiducia: Float, //Grado di affidabilità percepita basata su passate alleanze e accordi
    protected var _rispetto: Float, //Ammirazione per le qualità o le realizzazioni della città.
    protected var _influenza: Float, //Percezione del potere dell'altra città.
    protected var _collaborazione: Float, //Potenziale per lavorare insieme per obiettivi comuni.
): Cloneable{
    val paura: Float
        get() = _paura
    val fiducia: Float
        get() = _fiducia
    val rispetto: Float
        get() = _rispetto
    val influenza: Float
        get() = _influenza
    val collaborazione: Float
        get() = _collaborazione

    open fun update(stats: CommonThought){
        _paura+=stats.paura
        _fiducia+=stats.fiducia
        _rispetto+=stats.rispetto
        _influenza+=stats.influenza
        _collaborazione+=stats.collaborazione
    }

    abstract fun update(stats: Mood)

    open fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["paura"] = _paura
        map["fiducia"] = _fiducia
        map["rispetto"] = _rispetto
        map["influenza"] = _influenza
        map["collaborazione"] = _collaborazione
        return map
    }
}
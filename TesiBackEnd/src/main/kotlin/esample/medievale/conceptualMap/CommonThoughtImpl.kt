package esample.medievale.conceptualMap

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.npc.Mood

class CommonThoughtImpl(
    _paura: Float, //Timore di minacce o aggressioni.
    _fiducia: Float, //Grado di affidabilità percepita basata su passate alleanze e accordi
    _rispetto: Float, //Ammirazione per le qualità o le realizzazioni della città.
    _influenza: Float, //Percezione del potere o dell'impatto dell'altra città sulle proprie politiche o economia.
    _collaborazione: Float, //Potenziale per lavorare insieme per obiettivi comuni.
): CommonThought(_paura, _fiducia, _rispetto, _influenza, _collaborazione) {
    override fun update(stats: Mood) {
        _fiducia += stats.happiness * 0.1f
        _rispetto += stats.happiness * 0.1f

        _paura += stats.stress * 0.1f
        _collaborazione -= stats.stress * 0.1f

        _paura += stats.anger * 0.1f
        _fiducia -= stats.anger * 0.1f
        _influenza -= stats.anger * 0.1f

        _paura = _paura.coerceIn(-1f, 1f)
        _fiducia = _fiducia.coerceIn(-1f, 1f)
        _rispetto = _rispetto.coerceIn(-1f, 1f)
        _influenza = _influenza.coerceIn(-1f, 1f)
        _collaborazione = _collaborazione.coerceIn(-1f, 1f)
    }
}
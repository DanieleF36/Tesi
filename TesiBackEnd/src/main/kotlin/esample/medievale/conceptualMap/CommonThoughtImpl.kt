package esample.medievale.conceptualMap

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.npc.Mood

class CommonThoughtImpl(
    _paura: Float, //Timore di minacce o aggressioni.
    _fiducia: Float, //Grado di affidabilità percepita basata su passate alleanze e accordi
    _rispetto: Float, //Ammirazione per le qualità o le realizzazioni della città.
    _influenza: Float, //Percezione del potere o dell'impatto dell'altra città sulle proprie politiche o economia. Vicino a 0 si teme attacco
    _collaborazione: Float, //Potenziale per lavorare insieme per obiettivi comuni.
): CommonThought(_paura, _fiducia, _rispetto, _influenza, _collaborazione) {
    override fun update(stats: Mood) {
        TODO("Not yet implemented")
    }
}
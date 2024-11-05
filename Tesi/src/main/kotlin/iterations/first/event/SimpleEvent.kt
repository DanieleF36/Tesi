package iterations.first.event

import iterations.first.*

class SimpleEventType(override val name: String) :EventType {
    companion object{
        val SOSPETTO = SimpleEventType("Sospetto")
        val RISPETTO = SimpleEventType("Rispetto")
        val AMICIZIA = SimpleEventType("Amicizia")
        val FIDUCIA = SimpleEventType("Fiducia")
        val LODE = SimpleEventType("Lode")
        val RICHIESTA = SimpleEventType("Richiesta")
        val CONFLITTO = SimpleEventType("Conflitto")
        val SUPPORTO = SimpleEventType("Supporto")
        val OBIETTIVO = SimpleEventType("Obiettivo")
        val AMMIRAZIONE = SimpleEventType("Ammirazione")
        val RABBIA = SimpleEventType("Rabbia")
    }
}

class SimpleEventImportance(override val name: String) : EventImportance{
    companion object{
        val BANALE = SimpleEventImportance("BANALE")
        val NORMALE = SimpleEventImportance("NORMALE")
        val IMPORTANTE = SimpleEventImportance("IMPORTANTE")
        val CRUCIALE = SimpleEventImportance("CRUCIALE")
    }
}

class SimpleEvent(
    type: SimpleEventType,
    importance: SimpleEventImportance,
    statistic: Statistic,
    /** the number of link that this event can go through */
    propagationRange: Int = 1,
    description: String,
) : Event(type, importance, statistic, propagationRange, description){
    override fun isPropagable(node: ConceptualMap): Boolean {
        if(propagationRange==0 || visited.contains(node))
            return false
        propagationRange--
        visited.add(node)
        return true
    }
}
package esample.calcio.conceptualMap

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Link
import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.event.Event
import conceptualMap2.exceptions.EventGeneratedInAnotherGroupException
import conceptualMap2.event.GlobalEvent
import conceptualMap2.event.LocalEvent
import conceptualMap2.event.PropagatedEvent
import conceptualMap2.npc.NPC
import observerInterfaces.Observer

class ConceptualMapImpl(name: String, description: String, commonThought: CommonThought) : ConceptualMap(name, description, commonThought) {
    private val observers: MutableList<Observer> = mutableListOf()
    private val events: MutableList<Event> = mutableListOf()
    private val links = mutableSetOf<Link>()

    override fun getEventHistory(): List<Event> {
        return events.toList()
    }

    override fun addLink(link: Link): Boolean {
        return links.add(link)
    }

    override fun removeLink(group: ConceptualMap): Boolean {
        return links.removeIf{ it.a == group }
    }

    override fun generateEvent(event: LocalEvent, propagation: Boolean) {
        if(event.personGenerated.group != this)
            throw EventGeneratedInAnotherGroupException()
        events.add(event)
        commonThought.update(event.statistic)
        //println("In seguito allo stesso evento il pensiero comune è diventato: $commonThought")
        notifyObservers()
        if(propagation) {
            val evt = PropagatedEvent(event.type, event.importance, event.statistic, event.description, event.personGenerated, this)
            for (link in links) {
                link.propagate(evt)
            }
        }
    }

    override fun receiveEvent(event: PropagatedEvent) {
        events.add(event)
        commonThought.update(event.statistic)
        notifyObservers()
        //println("In seguito ad aver ricevuto un evento propagato da ${event.personGenerated?.group?.name} a $name, il pensiero comune è diventato $commonThought")
    }

    override fun receiveGlobalEvent(event: GlobalEvent) {
        events.add(event)
        commonThought.update(event.statistic)
        notifyObservers()
        //println("In seguito ad aver ricevuto l'evento globale ${event.description}, il pensiero comune di $name è cambiato in $commonThought")
    }

    override fun generateNPC(): NPC {
        TODO("Not yet implemented")
    }

    override fun attach(o: Observer) {
        observers.add(o)
    }

    override fun detach(o: Observer) {
        observers.remove(o)
    }

    override fun notifyObservers() {
        for(observer in observers)
            observer.update()
    }
}
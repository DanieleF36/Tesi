package esample.calcio.conceptualMap

import conceptualMap2.conceptualMap.*
import conceptualMap2.event.Event
import conceptualMap2.exceptions.EventGeneratedInAnotherGroupException
import conceptualMap2.event.GlobalEvent
import conceptualMap2.event.LocalEvent
import conceptualMap2.event.PureEvent
import conceptualMap2.npc.NPC

class ConceptualMapImpl(
    name: String,
    description: String,
    commonThought: CommonThought,
    commonThoughtOnGroups: Collection<Pair<String, CommonThought>>,
    fellowship: Fellowship
) : ConceptualMap(name, description, commonThought, commonThoughtOnGroups, fellowship) {
    //Pair<Event, n# of npc who has received it>
    private val events: MutableList<Pair<Event, Int>> = mutableListOf()
    private val links = mutableSetOf<Link>()
    private val propagationList = mutableListOf<PropagateEventWhen>()
    override fun getEventHistory(): List<Event> {
        return events.map { it.first }.toList()
    }

    override fun addLink(link: Link): Boolean {
        return links.add(link)
    }

    override fun removeLink(group: ConceptualMap): Boolean {
        return links.removeIf{ it.a == group }
    }

    override fun generateEvent(event: LocalEvent, propagation: Boolean) {
        events.add(Pair(event, 0))
        propagationList.add(PropagateEventWhen(
            event = event,
            condition = { events.find { it.first == event }!!.second >= npcs.size/2f},
            propagate = {
                commonThoughtOnPlayer.update(event.statistic)
                if(propagation) {
                    for (link in links) {
                        link.propagate(event)
                    }
                }
            }
        ))

        notifyObservers(event)
    }

    override fun generateEvent(event: PureEvent, propagation: Boolean) {
        events.add(Pair(event, 0))
        propagationList.add(PropagateEventWhen(
            event = event,
            condition = { events.find { it.first == event }!!.second >= npcs.size/2f},
            propagate = {
                commonThoughtOnPlayer.update(event.statistic)
                commonThoughtOnGroups.find { it.first.lowercase() == "staff tecnico" }!!.second.update(event.statistic)
                if(propagation) {
                    for (link in links) {
                        link.propagate(event)
                    }
                }
            }
        ))
        notifyObservers(event)
    }

    override fun generateRandomEvent(npc1: NPC, npc2: NPC) {
        npc1.generateRandomEvent(npc2)
    }

    override fun receivedEventFromNpc(npc: NPC, event: Event) {
        val p = events.find { it.first==event }
        if(p != null) {
            events.remove(p)
            events.add(Pair(event, p.second+1))
            propagationList.find { it.event==event }!!.check()
        }
    }

    override fun receiveGlobalEvent(event: GlobalEvent) {
        //Tutti hanno ricevuto l'evento
        events.add(Pair(event, npcs.size))
        commonThoughtOnPlayer.update(event.statistic)
        notifyObservers(event)
        //println("In seguito ad aver ricevuto l'evento globale ${event.description}, il pensiero comune di $name è cambiato in $commonThought")
    }

    override fun generateNPC(): NPC {
        TODO("Not yet implemented")
    }

    private fun notifyObservers(e: Event) {
        npcs.forEach { it.addEvent(e) }
    }
}
package esample.calcio.conceptualMap

import conceptualMap2.conceptualMap.*
import conceptualMap2.event.ChangeRelationshipLTE
import conceptualMap2.event.Event
import conceptualMap2.event.GlobalEvent
import conceptualMap2.event.LocalEvent
import conceptualMap2.event.PureEvent
import conceptualMap2.npc.NPC
import javax.swing.event.ChangeEvent
import kotlin.random.Random

class ConceptualMapImpl(
    name: String,
    description: String,
    commonThought: CommonThought,
    commonThoughtOnGroups: MutableMap<String, CommonThought>,
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
        commonThoughtOnGroups[link.a.name] = CommonThoughtImpl(0f,0f,0f)
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
                //Se il giocatore è uno delle persone
                if(event.personGenerated!!.first.name=="player" || event.personGenerated!!.second.name=="player") {
                    commonThoughtOnPlayer.update(event.statistic)
                    commonThoughtOnGroups["staff tecnico"]!!.update(event.statistic)
                    commonThoughtOnGroups[if(event.personGenerated!!.first.name=="player")
                            event.personGenerated!!.second.group.name.lowercase()
                        else
                            event.personGenerated!!.first.group.name.lowercase()
                    ]!!.update(event.statistic)
                }
                else {
                    commonThoughtOnGroups[event.personGenerated!!.first.group.name.lowercase()]!!.update(event.statistic)
                    commonThoughtOnGroups[event.personGenerated!!.second.group.name.lowercase()]!!.update(event.statistic)
                }
                if(propagation) {
                    for (link in links) {
                        link.propagate(event)
                    }
                }
            }
        ))
        notifyObservers(event)
    }

    override fun receiveEvent(event: ChangeRelationshipLTE, propagation: Boolean) {
        if(event.linkCnt != 1)
            return
        links.forEach { it.propagate(event) }
        notifyObservers(event)
    }

    override fun generateRandomEvent() {
        val index1 = Random.nextInt(npcs.size)
        val groupIndex = Random.nextInt(links.size+1)
        var group: ConceptualMap = this
        if(groupIndex!=links.size){
            group = links.toList()[groupIndex].a
        }
        val index2 = Random.nextInt(group.npcs.size)
        generateRandomEvent(npcs[index1], group.npcs[index2])
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
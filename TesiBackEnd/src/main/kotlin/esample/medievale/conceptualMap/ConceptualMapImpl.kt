package esample.medievale.conceptualMap

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.Link
import conceptualMap2.conceptualMap.LinkType
import conceptualMap2.conceptualMap.PropagateEventWhen
import conceptualMap2.event.ChangeRelationshipLTE
import conceptualMap2.event.Event
import conceptualMap2.event.GlobalEvent
import conceptualMap2.event.LocalEvent
import conceptualMap2.event.PureEvent
import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC
import esample.medievale.WeightSimpleMood
import esample.medievale.event.NewCTAfterLTCE
import esample.medievale.link.BidirectionalLink
import esample.medievale.link.Relationship
import kotlin.math.abs
import kotlin.random.Random

class ConceptualMapImpl(
    name: String,
    description: String,
    commonThoughtOnPlayer: CommonThought,
    commonThoughtOnGroups: MutableMap<String, CommonThought>,
    fellowship: Fellowship,
    private val relationships: MutableMap<String, LinkType>
): ConceptualMap(name, description, commonThoughtOnPlayer, commonThoughtOnGroups, fellowship) {

    private val events: MutableMap<Event, Int> = mutableMapOf()
    private val eventToPropagate: MutableSet<PropagateEventWhen> = mutableSetOf()
    private val links: MutableSet<BidirectionalLink> = mutableSetOf()


    override fun getEventHistory(): List<Event> {
        return events.keys.toList()
    }

    override fun addLink(link: Link, newCT: CommonThought): Boolean {
        commonThoughtOnGroups[link.a.name] = newCT
        return links.add(link as BidirectionalLink)
    }

    override fun removeLink(link: Link): Boolean {
        return links.remove(link)
    }

    override fun generateEvent(event: LocalEvent, propagation: Boolean) {
        TODO("Da eliminare")
    }

    override fun generateEvent(event: PureEvent, propagation: Boolean) {
        events[event] = 0
        eventToPropagate.add(PropagateEventWhen(
                event = event,
                condition = { events[event]!! >= npcs.size/2f },
                propagate = {
                    if(event.personGenerated!!.first.name == "player" || event.personGenerated!!.second.name == "player")
                        commonThoughtOnPlayer.update(event.statistic)
                    val m1 = updateStat(event.personGenerated!!.first.group.name).update(event.statistic)
                    val m2 = updateStat(event.personGenerated!!.second.group.name).update(event.statistic)
                    commonThoughtOnGroups[event.personGenerated!!.first.group.name]!!.update(m1)
                    commonThoughtOnGroups[event.personGenerated!!.second.group.name]!!.update(m2)
                }
            )
        )
        notifyNPCs(event)
    }

    private fun updateStat(groupName: String): Mood{
        return when(relationships[groupName]!!) {
            Relationship.ALLEATO_pos -> WeightSimpleMood(1.2f, 0.5f, 0.4f)
            Relationship.ALLEATO_neg -> WeightSimpleMood(1f, 0.5f, 0.5f)
            Relationship.AMICIZIA_pos -> WeightSimpleMood(1f, 0.55f, 0.55f)
            Relationship.AMICIZIA_neg -> WeightSimpleMood(.9f, 0.55f, 0.55f)
            Relationship.NEUTRALE_pos -> WeightSimpleMood(.85f, 0.6f, 0.6f)
            Relationship.NEUTRALE_neg -> WeightSimpleMood(.7f, 0.7f, 0.75f)
            Relationship.RIVALE_pos -> WeightSimpleMood(-.2f, 0.5f, -0.5f)
            Relationship.RIVALE_neg -> WeightSimpleMood(-.3f, 0.5f, -0.6f)
            Relationship.NEMICO_pos -> WeightSimpleMood(-.5f, 0.6f, -0.7f)
            Relationship.NEMICO_neg -> WeightSimpleMood(-.7f, 0.65f, -0.9f)
            Relationship.CONFLITTO_pos -> WeightSimpleMood(-1f, 0.8f, -1.1f)
            Relationship.CONFLITTO_neg -> WeightSimpleMood(-1.1f, 1f, -1.3f)
            else -> TODO()
        }

    }
    //TODO propaga cambiamento
    override fun receiveEvent(event: ChangeRelationshipLTE, propagation: Boolean) {
        //Se uno dei 2 gruppi siamo noi
        if(event.groups.first == this &&  relationships[event.groups.second.name] != null && relationships[event.groups.second.name] != event.newType || event.groups.second == this &&  relationships[event.groups.first.name] != null && relationships[event.groups.first.name] != event.newType) {
            lateinit var g: ConceptualMap
            lateinit var oldType: LinkType
            if (event.groups.first == this && relationships[event.groups.second.name] != null && relationships[event.groups.second.name] != event.newType) {
                oldType = relationships[event.groups.second.name]!!
                relationships[event.groups.second.name] = event.newType
                g = event.groups.second
            }
            if (event.groups.second == this && relationships[event.groups.first.name] != null && relationships[event.groups.first.name] != event.newType) {
                oldType = relationships[event.groups.first.name]!!
                relationships[event.groups.first.name] = event.newType
                g = event.groups.first
            }
            //Cambio del pensiero comune
            updateGroupCT(g.name, oldType as Relationship, event.newType as Relationship)
            //Notifica agli NPC
            notifyNPCs(
                NewCTAfterLTCE(
                    event.type,
                    event.importance,
                    event.statistic,
                    event.description,
                    event.generatedTime,
                    g,
                    oldType,
                    event.newType,
                    commonThoughtOnGroups[g.name]!!
                )
            )
        }else{//Se sono due gruppi diversi dal mio
            //Se entrambi i gruppi sono direttamente linkati a me allora cambio CT e link
            val l1 = links.find { (it.a == event.groups.first || it.b == event.groups.first)}
            val l2 = links.find { (it.a == event.groups.second || it.b == event.groups.second) }
            val diff = abs(event.newType.value) - abs(event.oldType.value)
            if(l1 != null && l2 != null){
                if(l1.linkType.value > 0 && l2.linkType.value > 0){//caso positivo con entrambi
                    if(event.oldType.value < event.newType.value) {    //caso link migliora
                        relationships[event.groups.first.name] = if(relationships[event.groups.first.name]!! != Relationship.AMICIZIA_pos) relationships[event.groups.first.name]!!.increase() else relationships[event.groups.first.name]!!
                        relationships[event.groups.second.name] = if(relationships[event.groups.second.name]!! != Relationship.AMICIZIA_pos) relationships[event.groups.second.name]!!.increase() else relationships[event.groups.second.name]!!

                        commonThoughtOnGroups[event.groups.first.name] = CommonThoughtImpl(
                            _paura = commonThoughtOnGroups[event.groups.first.name]!!.paura*(1f-(diff/10f)),
                            _fiducia = commonThoughtOnGroups[event.groups.first.name]!!.fiducia*(1f+(diff/10f)),
                            _rispetto = commonThoughtOnGroups[event.groups.first.name]!!.rispetto*(1f+(diff/10f)),
                            _influenza = commonThoughtOnGroups[event.groups.first.name]!!.rispetto*(1f+(diff/10f)),
                            _collaborazione = commonThoughtOnGroups[event.groups.first.name]!!.collaborazione*(1f+(diff/10f))
                        )
                        commonThoughtOnGroups[event.groups.second.name] = CommonThoughtImpl(
                            _paura = commonThoughtOnGroups[event.groups.second.name]!!.paura*(1f-(diff/10f)),
                            _fiducia = commonThoughtOnGroups[event.groups.second.name]!!.fiducia*(1f+(diff/10f)),
                            _rispetto = commonThoughtOnGroups[event.groups.second.name]!!.rispetto*(1f+(diff/10f)),
                            _influenza = commonThoughtOnGroups[event.groups.second.name]!!.rispetto*(1f+(diff/10f)),
                            _collaborazione = commonThoughtOnGroups[event.groups.second.name]!!.collaborazione*(1f+(diff/10f))
                        )
                    }else{//caso link peggiora
                        if(l1.linkType.value > l2.linkType.value || l1.linkType.value < l2.linkType.value){//Se un link è più forte dell'altro
                            val strongest = if(l1.linkType.value > l2.linkType.value)
                                if(l1.b==this)l1.b
                                else l1.a
                            else if(l2.b==this)l2.b
                                else l2.a
                            val weakest = if(l1.linkType.value > l2.linkType.value)
                                if(l2.b==this)l2.b
                                else l2.a
                            else if(l1.b==this)l1.b
                                else l1.a
                            relationships[weakest.name] = if(relationships[weakest.name] != Relationship.NEMICO_pos) relationships[weakest.name]!!.decrease() else relationships[weakest.name]!!
                            commonThoughtOnGroups[weakest.name] = CommonThoughtImpl(
                                _paura = commonThoughtOnGroups[weakest.name]!!.paura*(1f+(diff/10f)),
                                _fiducia = commonThoughtOnGroups[weakest.name]!!.fiducia*(1f-(diff/10f)),
                                _rispetto = commonThoughtOnGroups[weakest.name]!!.rispetto*(1f-(diff/10f)),
                                _influenza = commonThoughtOnGroups[weakest.name]!!.rispetto*(1f-(diff/10f)),
                                _collaborazione = commonThoughtOnGroups[weakest.name]!!.collaborazione*(1f-(diff/10f))
                            )
                        } else{ //Se sono uguali
                            relationships[event.groups.first.name] = if(relationships[event.groups.first.name]!! != Relationship.NEMICO_neg) relationships[event.groups.first.name]!!.decrease() else relationships[event.groups.first.name]!!
                            relationships[event.groups.second.name] = if(relationships[event.groups.second.name]!! != Relationship.NEMICO_neg) relationships[event.groups.second.name]!!.decrease() else relationships[event.groups.second.name]!!
                            commonThoughtOnGroups[event.groups.first.name] = CommonThoughtImpl(
                                _paura = commonThoughtOnGroups[event.groups.first.name]!!.paura*(1f+(diff/10f)),
                                _fiducia = commonThoughtOnGroups[event.groups.first.name]!!.fiducia*(1f-(diff/10f)),
                                _rispetto = commonThoughtOnGroups[event.groups.first.name]!!.rispetto*(1f-(diff/10f)),
                                _influenza = commonThoughtOnGroups[event.groups.first.name]!!.rispetto*(1f-(diff/10f)),
                                _collaborazione = commonThoughtOnGroups[event.groups.first.name]!!.collaborazione*(1f-(diff/10f))
                            )
                            commonThoughtOnGroups[event.groups.second.name] = CommonThoughtImpl(
                                _paura = commonThoughtOnGroups[event.groups.second.name]!!.paura*(1f+(diff/10f)),
                                _fiducia = commonThoughtOnGroups[event.groups.second.name]!!.fiducia*(1f-(diff/10f)),
                                _rispetto = commonThoughtOnGroups[event.groups.second.name]!!.rispetto*(1f-(diff/10f)),
                                _influenza = commonThoughtOnGroups[event.groups.second.name]!!.rispetto*(1f-(diff/10f)),
                                _collaborazione = commonThoughtOnGroups[event.groups.second.name]!!.collaborazione*(1f-(diff/10f))
                            )
                        }

                    }
                }else if(l1.linkType.value < 0 && l2.linkType.value < 0){//caso negativo con entrambi
                    if(event.oldType.value < event.newType.value) {    //caso link migliora
                        relationships[event.groups.first.name] = if(relationships[event.groups.first.name]!! != Relationship.NEMICO_neg) relationships[event.groups.first.name]!!.decrease() else relationships[event.groups.first.name]!!
                        relationships[event.groups.second.name] = if(relationships[event.groups.second.name]!! != Relationship.NEMICO_neg) relationships[event.groups.second.name]!!.decrease() else relationships[event.groups.second.name]!!

                        commonThoughtOnGroups[event.groups.first.name] = CommonThoughtImpl(
                            _paura = commonThoughtOnGroups[event.groups.first.name]!!.paura*(1f+(diff/10f)),
                            _fiducia = commonThoughtOnGroups[event.groups.first.name]!!.fiducia*(1f-(diff/10f)),
                            _rispetto = commonThoughtOnGroups[event.groups.first.name]!!.rispetto*(1f-(diff/10f)),
                            _influenza = commonThoughtOnGroups[event.groups.first.name]!!.rispetto*(1f-(diff/10f)),
                            _collaborazione = commonThoughtOnGroups[event.groups.first.name]!!.collaborazione*(1f-(diff/10f))
                        )
                        commonThoughtOnGroups[event.groups.second.name] = CommonThoughtImpl(
                            _paura = commonThoughtOnGroups[event.groups.second.name]!!.paura*(1f+(diff/10f)),
                            _fiducia = commonThoughtOnGroups[event.groups.second.name]!!.fiducia*(1f-(diff/10f)),
                            _rispetto = commonThoughtOnGroups[event.groups.second.name]!!.rispetto*(1f-(diff/10f)),
                            _influenza = commonThoughtOnGroups[event.groups.second.name]!!.rispetto*(1f-(diff/10f)),
                            _collaborazione = commonThoughtOnGroups[event.groups.second.name]!!.collaborazione*(1f+(diff/10f))
                        )
                    }else{//caso link peggiora
                        if(l1.linkType.value > l2.linkType.value || l1.linkType.value < l2.linkType.value) {//Se un link è più forte dell'altro
                            val strongest = if(l1.linkType.value > l2.linkType.value)
                                if(l1.b==this)l1.b
                                else l1.a
                            else if(l2.b==this)l2.b
                            else l2.a
                            val weakest = if(l1.linkType.value > l2.linkType.value)
                                if(l2.b==this)l2.b
                                else l2.a
                            else if(l1.b==this)l1.b
                            else l1.a
                            relationships[weakest.name] = if(relationships[weakest.name] != Relationship.NEMICO_pos) relationships[weakest.name]!!.decrease() else relationships[weakest.name]!!
                            commonThoughtOnGroups[weakest.name] = CommonThoughtImpl(
                                _paura = commonThoughtOnGroups[weakest.name]!!.paura*(1f+(diff/10f)),
                                _fiducia = commonThoughtOnGroups[weakest.name]!!.fiducia*(1f-(diff/10f)),
                                _rispetto = commonThoughtOnGroups[weakest.name]!!.rispetto*(1f-(diff/10f)),
                                _influenza = commonThoughtOnGroups[weakest.name]!!.rispetto*(1f-(diff/10f)),
                                _collaborazione = commonThoughtOnGroups[weakest.name]!!.collaborazione*(1f-(diff/10f))
                            )
                            relationships[strongest.name] = if(relationships[strongest.name] != Relationship.AMICIZIA_pos) relationships[strongest.name]!!.increase() else relationships[strongest.name]!!
                            commonThoughtOnGroups[strongest.name] = CommonThoughtImpl(
                                _paura = commonThoughtOnGroups[strongest.name]!!.paura*(1f-(diff/10f)),
                                _fiducia = commonThoughtOnGroups[strongest.name]!!.fiducia*(1f+(diff/10f)),
                                _rispetto = commonThoughtOnGroups[strongest.name]!!.rispetto*(1f+(diff/10f)),
                                _influenza = commonThoughtOnGroups[strongest.name]!!.rispetto*(1f+(diff/10f)),
                                _collaborazione = commonThoughtOnGroups[strongest.name]!!.collaborazione*(1f+(diff/10f))
                            )
                        }else{//Se sono uguali
                            //TODO Rimane uguale?
                        }
                    }
                }


                //caso uno posivo e uno negativo
            }
            //Se uno dei due gruppi è direttamente linkato a me allora cambio CT e link
            if(links.find { it.a == event.groups.first || it.b == event.groups.first || it.a == event.groups.second || it.b == event.groups.second} != null){
                //Cambio CT
            }
            //Se nessuno dei due gruppi è linkato a me ma uno si trova a max distanza 2 e il peso dei rapporti è alterato: me --pos--> C --neg--> B  me --neg--> C --pos--> B allora cambio CT
            //Se nessuno dei due gruppi è linkato a me ma uno si trova a max distanza 2 e il peso dei rapporti è uguale: me --pos--> C --pos--> B  me --neg--> C --neg--> B allora cambio CT
            //altrimenti ignoro
            TODO()
        }
    }

    /**
     * return true only if group is found in less or equal number of links
     */
    private fun find(group: ConceptualMap, limit: Int): Boolean{

    }

    override fun navigateThroughLinks() {
        TODO("Not yet implemented")
    }

    private fun updateGroupCT(name: String, oldType: Relationship, newType: Relationship){
        val pos = Relationship.computeDiff(oldType, newType)
        val newCt = if(pos < 0)
            CommonThoughtImpl((1f*pos)/11f, (-1f*pos)/11f,(-1f*pos)/11f,1f,(-1f*pos)/11f)
        else if(pos > 0)
            CommonThoughtImpl((-1f*pos)/11f, (1f*pos)/11f,(1f*pos)/11f,1f,(1f*pos)/11f)
        else
            commonThoughtOnGroups[name]!!
        commonThoughtOnGroups[name] = newCt
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

    override fun receiveGlobalEvent(event: GlobalEvent) {
        events[event] =  npcs.size
        commonThoughtOnPlayer.update(event.statistic)
        notifyNPCs(event)
    }

    override fun receivedEventFromNpc(npc: NPC, event: Event) {
        events[event] = events[event]!!+1
        eventToPropagate.find { it.event==event }!!.check()
    }

    override fun generateNPC(): NPC {
        TODO("Not yet implemented")
    }

    private fun notifyNPCs(e: Event) {
        npcs.forEach { it.addEvent(e) }
    }
}
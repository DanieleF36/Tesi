package esample.medievale.conceptualMap

import conceptualMap2.clock.Clock
import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.Link
import conceptualMap2.conceptualMap.LinkType
import conceptualMap2.conceptualMap.PropagateEventWhen
import conceptualMap2.event.AbstractEvent
import conceptualMap2.event.ChangeRelationshipLTE
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.event.GlobalEvent
import conceptualMap2.event.LocalEvent
import conceptualMap2.event.PureEvent
import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC
import esample.medievale.WeightSimpleMood
import esample.medievale.event.NewCTAfterLTCE
import esample.medievale.event.pureEvent.MedievalEventImportance
import esample.medievale.event.pureEvent.MedievalEventType
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

    private val events: MutableMap<AbstractEvent, Int> = mutableMapOf()
    private val eventToPropagate: MutableSet<PropagateEventWhen> = mutableSetOf()
    private val links: MutableSet<BidirectionalLink> = mutableSetOf()


    override fun getEventHistory(): List<AbstractEvent> {
        return events.keys.toList()
    }

    override fun addLink(link: Link, newCT: CommonThought): Boolean {
        link as BidirectionalLink
        val l = if(link.a == this) link.b else link.a
        commonThoughtOnGroups[l.name] = newCT
        return links.add(link )
    }

    override fun removeLink(link: Link): Boolean {
        return links.remove(link)
    }

    override fun generateEvent(event: LocalEvent, propagation: Boolean) {
        TODO("Da eliminare")
    }

    override fun generateEvent(event: PureEvent, propagation: Boolean) {
        if(events.containsKey(event))
            return
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
                    if(propagation)
                        links.forEach { it.propagate(event, this) }
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

    override fun receiveEvent(event: ChangeRelationshipLTE, propagation: Boolean) {
        if(events.containsKey(event))
            return
        events[event] = 0
        //Se uno dei 2 gruppi siamo noi
        if((event.groups.first == this &&  relationships[event.groups.second.name] != null && relationships[event.groups.second.name] != event.newType) || (event.groups.second == this &&  relationships[event.groups.first.name] != null && relationships[event.groups.first.name] != event.newType)) {
            directLinked(event)
        }else{//Se sono due gruppi diversi dal mio
            //Se entrambi i gruppi sono direttamente linkati a me allora cambio CT e link
            val l1 = links.find { (it.a == event.groups.first || it.b == event.groups.first)}
            val l2 = links.find { (it.a == event.groups.second || it.b == event.groups.second) }
            if(l1 != null && l2 != null){
                bothLinked(event, l1, l2)
            }
            //Se uno dei due gruppi è direttamente linkato a me e l'altro si trova a max distanza 2 allora cambio CT e link
            else if(l1 != null || l2 != null){
                val l = if(l1 != null) l1 else l2
                oneLinked(event, l!!)
            }
            else {
                val allPaths1 = find(event.groups.first, 2)
                val allPaths2 = find(event.groups.second, 2)
                if(allPaths1 != null && allPaths2 != null) //Entrambi i gruppi si trovano a distanza 2
                {
                    //pos pos, pos pos
                    //pos pos, pos neg
                    //pos pos, neg pos
                    //pos pos, neg neg
                    //pos neg, pos pos
                    //pos neg, pos neg
                    //pos neg, neg pos
                    //pos neg, neg neg
                    //neg pos, pos pos
                    //neg pos, pos neg
                    //neg pos, neg pos
                    //neg pos, neg neg
                    //neg neg, pos pos
                    //neg neg, pos neg
                    //neg neg, neg pos
                    //neg neg, neg neg
                }
                else //Solo un gruppo si trova a distanza 1
                {
                    //pos pos
                    //pos neg
                    //neg pos
                    //neg neg
                }
            }
            //altrimenti ignoro
        }
    }

    private fun propagate(event: ChangeRelationshipLTE){
        links.forEach {
            it.propagate(event, this)
        }
    }

    private fun directLinked(event: ChangeRelationshipLTE){
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
                event.generatedTime,
                g.name,
                oldType,
                event.newType,
                commonThoughtOnGroups[g.name]!!
            )
        )
        propagate(event)
    }

    private fun bothLinked(event: ChangeRelationshipLTE, l1: BidirectionalLink, l2: BidirectionalLink) {
        val diff = abs(event.newType.value) - abs(event.oldType.value)
        if(l1.linkType.value > 0 && l2.linkType.value > 0){//caso positivo con entrambi
            if(event.oldType.value < event.newType.value) //caso link migliora
            {
                var oldType = relationships[event.groups.first.name]!!
                relationships[event.groups.first.name] = if(relationships[event.groups.first.name]!! != Relationship.AMICIZIA_pos) relationships[event.groups.first.name]!!.increase() else relationships[event.groups.first.name]!!
                modifyCT(event.groups.first.name, diff, true, oldType)
                propagate(ChangeRelationshipLTE(
                    Clock.getCurrentDateTime(),
                    Pair(this, event.groups.first),
                    oldType,
                    relationships[event.groups.first.name]!!
                ))
                oldType = relationships[event.groups.second.name]!!
                relationships[event.groups.second.name] = if(relationships[event.groups.second.name]!! != Relationship.AMICIZIA_pos) relationships[event.groups.second.name]!!.increase() else relationships[event.groups.second.name]!!
                modifyCT(event.groups.second.name, diff, true, oldType)
                propagate(ChangeRelationshipLTE(
                    Clock.getCurrentDateTime(),
                    Pair(this, event.groups.first),
                    oldType,
                    relationships[event.groups.second.name]!!
                ))
            }
            else //caso link peggiora
            {
                if(l1.linkType.value > l2.linkType.value || l1.linkType.value < l2.linkType.value)//Se un link è più forte dell'altro
                {
                    val weakest = if(l1.linkType.value > l2.linkType.value)
                        if(l2.b!=this)l2.b
                        else l2.a
                    else if(l1.b!=this)l1.b
                    else l1.a
                    val oldType = relationships[weakest.name]!!
                    relationships[weakest.name] = if(relationships[weakest.name] != Relationship.NEMICO_pos) relationships[weakest.name]!!.decrease() else relationships[weakest.name]!!
                    modifyCT(weakest.name, diff, false, oldType)
                    propagate(ChangeRelationshipLTE(
                        Clock.getCurrentDateTime(),
                        Pair(this, weakest),
                        oldType,
                        relationships[weakest.name]!!
                    ))
                }
                else //Se sono uguali (Non dovrebbe capitare)
                {
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
        }else if(l1.linkType.value < 0 && l2.linkType.value < 0) //caso negativo con entrambi
        {
            if(event.oldType.value < event.newType.value) //caso link migliora
            {
                var oldType = relationships[event.groups.first.name]!!
                relationships[event.groups.first.name] = if(relationships[event.groups.first.name]!! != Relationship.NEMICO_neg) relationships[event.groups.first.name]!!.decrease() else relationships[event.groups.first.name]!!
                modifyCT(event.groups.first.name, diff, false, oldType)
                propagate(ChangeRelationshipLTE(
                    Clock.getCurrentDateTime(),
                    Pair(this, event.groups.first),
                    oldType,
                    relationships[event.groups.first.name]!!
                ))
                oldType = relationships[event.groups.second.name]!!
                relationships[event.groups.second.name] = if(relationships[event.groups.second.name]!! != Relationship.NEMICO_neg) relationships[event.groups.second.name]!!.decrease() else relationships[event.groups.second.name]!!
                modifyCT(event.groups.second.name, diff, false, oldType)
                propagate(ChangeRelationshipLTE(
                    Clock.getCurrentDateTime(),
                    Pair(this, event.groups.second),
                    oldType,
                    relationships[event.groups.second.name]!!
                ))
            }
            else //caso link peggiora
            {
                if(l1.linkType.value > l2.linkType.value || l1.linkType.value < l2.linkType.value) //Se un link è più forte dell'altro
                {
                    val strongest = if(l1.linkType.value > l2.linkType.value)
                        if(l1.b!=this)l1.b
                        else l1.a
                    else if(l2.b!=this)l2.b
                    else l2.a
                    val weakest = if(l1.linkType.value > l2.linkType.value)
                        if(l2.b!=this)l2.b
                        else l2.a
                    else if(l1.b!=this)l1.b
                    else l1.a
                    var oldType = relationships[weakest.name]!!
                    relationships[weakest.name] = if(relationships[weakest.name] != Relationship.NEMICO_pos) relationships[weakest.name]!!.decrease() else relationships[weakest.name]!!
                    modifyCT(weakest.name, diff, false, oldType)
                    propagate(ChangeRelationshipLTE(
                        Clock.getCurrentDateTime(),
                        Pair(this, weakest),
                        oldType,
                        relationships[weakest.name]!!
                    ))
                    oldType = relationships[strongest.name]!!
                    relationships[strongest.name] = if(relationships[strongest.name] != Relationship.AMICIZIA_pos) relationships[strongest.name]!!.increase() else relationships[strongest.name]!!
                    modifyCT(strongest.name, diff, true, oldType)
                    propagate(ChangeRelationshipLTE(
                        Clock.getCurrentDateTime(),
                        Pair(this, strongest),
                        oldType,
                        relationships[strongest.name]!!
                    ))
                }else{//Se sono uguali (non dovrebbe capitare)
                    //TODO Rimane uguale?
                }
            }
        }
        else  //caso uno posivo e uno negativo
        {
            val pos = if(l1.linkType.value > 0) if(l1.a != this) l1.a else l1.b else if(l2.b != this) l2.b else l2.b
            val neg = if(l1.linkType.value < 0) if(l1.a != this) l1.a else l1.b else if(l2.b != this) l2.b else l2.b
            if(event.oldType.value < event.newType.value) //caso link migliora
            {
                val oldType = relationships[pos.name]!!
                relationships[pos.name] = relationships[pos.name]!!.decrease()
                modifyCT(pos.name, diff, false, oldType)
                propagate(ChangeRelationshipLTE(
                    Clock.getCurrentDateTime(),
                    Pair(this, pos),
                    oldType,
                    relationships[pos.name]!!
                ))
            }else if(event.oldType.value > event.newType.value) //caso link peggiora
            {
                val oldType = relationships[pos.name]!!
                relationships[pos.name] = if(relationships[pos.name] != Relationship.AMICIZIA_pos) relationships[pos.name]!!.increase() else relationships[pos.name]!!
                modifyCT(pos.name, diff, true, oldType)
                propagate(ChangeRelationshipLTE(
                    Clock.getCurrentDateTime(),
                    Pair(this, pos),
                    oldType,
                    relationships[pos.name]!!
                ))
            }
        }
    }

    private fun modifyCT(name: String, diff: Int, pos: Boolean, oldType: LinkType){
        if(!pos)
            commonThoughtOnGroups[name] = CommonThoughtImpl(
                _paura = commonThoughtOnGroups[name]!!.paura*(1f+(diff/10f)),
                _fiducia = commonThoughtOnGroups[name]!!.fiducia*(1f-(diff/10f)),
                _rispetto = commonThoughtOnGroups[name]!!.rispetto*(1f-(diff/10f)),
                _influenza = commonThoughtOnGroups[name]!!.rispetto*(1f-(diff/10f)),
                _collaborazione = commonThoughtOnGroups[name]!!.collaborazione*(1f-(diff/10f))
            )
        else
            commonThoughtOnGroups[name] = CommonThoughtImpl(
                _paura = commonThoughtOnGroups[name]!!.paura*(1f-(diff/10f)),
                _fiducia = commonThoughtOnGroups[name]!!.fiducia*(1f+(diff/10f)),
                _rispetto = commonThoughtOnGroups[name]!!.rispetto*(1f+(diff/10f)),
                _influenza = commonThoughtOnGroups[name]!!.rispetto*(1f+(diff/10f)),
                _collaborazione = commonThoughtOnGroups[name]!!.collaborazione*(1f+(diff/10f))
            )
        notifyNPCs(NewCTAfterLTCE(Clock.getCurrentDateTime(), name, oldType, relationships[name]!!, commonThoughtOnGroups[name]!!))
    }
    /**
     * @param l è il link tra me e quello direttamente linkato
     */
    private fun oneLinked(event: ChangeRelationshipLTE, l: BidirectionalLink){
        //l'altro gruppo che ha subito il cambiamento del link
        val g = if(l.a != this) if(event.groups.first != l.a) event.groups.first else event.groups.second else if(event.groups.first != l.b) event.groups.first else event.groups.second
        val allPaths = find(g, 2)
        if(allPaths == null)
            return
        //Controllo che non abbia usato lo stesso link modificato
        val list = mutableListOf< MutableList<Link>>()
        for(path in allPaths){
            val b = path.find {
                it as BidirectionalLink
                (it.a == event.groups.first || it.a == event.groups.second) && (it.b == event.groups.first || it.b == event.groups.second)
            }
            if(b != null)
                list.add(path)
        }
        allPaths.removeAll(list)
        for (path in  allPaths) {
            val diff = abs(event.newType.value) - abs(event.oldType.value)
            val directNode = if (l.a != this) l.a else l.b
            val middleLink = path.first() as BidirectionalLink
            val middleNode = if (middleLink.a != this) middleLink.a else middleLink.b
            val lastLink = path.last() as BidirectionalLink
            val lastNode = if (lastLink.a != middleNode) middleLink.a else middleLink.b
            // a questo punto allPath contiene tutti i modi per andare a g senza usare il link che è cambiato
            if (l.linkType.value > 0)//Se il l ink a cui sono direttamente collegato è positivo
            {
                if (middleLink.linkType.value > 0) //Se il link che collega me e il nodo di intermezzo è positivo
                {
                    if (lastLink.linkType.value > 0) //Se il link che collega il nodo di intermezzo a quello finale è positivo
                    {
                        if (event.newType.value > event.oldType.value) //Se il nuovo link è positivo
                        {
                            var oldType = relationships[middleNode.name]!!
                            relationships[middleNode.name] = if (relationships[middleNode.name] != Relationship.AMICIZIA_pos) relationships[middleNode.name]!!.increase() else relationships[middleNode.name]!!
                            modifyCT(middleNode.name, diff, true, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, middleNode),
                                oldType,
                                relationships[middleNode.name]!!
                            ))
                        } else //Se il nuovo link è negativo
                        {
                            val weak = if (l.linkType.value < middleLink.linkType.value) if (l.a != this) l.a else l.b else middleNode
                            var oldType = relationships[weak.name]!!
                            relationships[weak.name] = relationships[weak.name]!!.decrease()
                            modifyCT(weak.name, diff, false, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, weak),
                                oldType,
                                relationships[weak.name]!!
                            ))
                        }
                    } else //Se il link che collega il nodo di intermezzo a quello finale è negativo
                    {
                        if (event.newType.value > event.oldType.value) //Se il nuovo link è positivo
                        {
                            val strongest = if (l.linkType.value > middleLink.linkType.value) if (l.a != this) l.a else l.b else middleNode
                            var oldType = relationships[strongest.name]!!
                            relationships[strongest.name] = if (relationships[strongest.name] != Relationship.AMICIZIA_pos) relationships[strongest.name]!!.increase() else relationships[strongest.name]!!
                            modifyCT(strongest.name, diff, true, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, strongest),
                                oldType,
                                relationships[strongest.name]!!
                            ))
                        } else //Se il nuovo link è negativo
                        {
                            val weakest =
                                if (l.linkType.value < middleLink.linkType.value) if (l.a != this) l.a else l.b else middleNode
                            var oldType = relationships[weakest.name]!!
                            relationships[weakest.name] = relationships[weakest.name]!!.decrease()
                            modifyCT(weakest.name, diff, false, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, weakest),
                                oldType,
                                relationships[weakest.name]!!
                            ))
                        }
                    }
                } else { //Se il link che collega me e il nodo di intermezzo è negativo
                    if (lastLink.linkType.value > 0) //Se il link che collega il nodo di intermezzo a quello finale è positivo
                    {
                        if (event.newType.value > event.oldType.value) //Se il nuovo link è positivo
                        {
                            var oldType = relationships[directNode.name]!!
                            relationships[directNode.name] = relationships[directNode.name]!!.decrease()
                            modifyCT(middleNode.name, diff, false, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, middleNode),
                                oldType,
                                relationships[directNode.name]!!
                            ))
                        } else //Se il nuovo link è negativo
                        {
                            var oldType = relationships[directNode.name]!!
                            relationships[directNode.name] =
                                if (relationships[directNode.name] != Relationship.AMICIZIA_pos) relationships[directNode.name]!!.increase() else relationships[directNode.name]!!
                            modifyCT(directNode.name, diff, true, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, directNode),
                                oldType,
                                relationships[directNode.name]!!
                            ))
                        }
                    } else //Se il link che collega il nodo di intermezzo a quello finale è negativo
                    {
                        if (event.newType.value > event.oldType.value) //Se il nuovo link è positivo
                        {
                            var oldType = relationships[directNode.name]!!
                            relationships[directNode.name] =
                                if (relationships[directNode.name] != Relationship.AMICIZIA_pos) relationships[directNode.name]!!.increase() else relationships[directNode.name]!!
                            modifyCT(directNode.name, diff, true, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, directNode),
                                oldType,
                                relationships[directNode.name]!!
                            ))
                        } else //Se il nuovo link è negativo
                        {
                            var oldType = relationships[directNode.name]!!
                            relationships[directNode.name] = relationships[directNode.name]!!.decrease()
                            modifyCT(middleNode.name, diff, false, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, directNode),
                                oldType,
                                relationships[directNode.name]!!
                            ))
                        }
                    }
                }
            } else //Se il l ink a cui sono direttamente collegato è negativo
            {
                if (middleLink.linkType.value > 0) //Se il link che collega me e il nodo di intermezzo è positivo
                {
                    if (lastLink.linkType.value > 0) //Se il link che collega il nodo di intermezzo a quello finale è positivo
                    {
                        if (event.newType.value > event.oldType.value) //Se il nuovo link è positivo
                        {
                            var oldType = relationships[middleNode.name]!!
                            relationships[middleNode.name] = relationships[middleNode.name]!!.decrease()
                            modifyCT(middleNode.name, diff, false, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, middleNode),
                                oldType,
                                relationships[middleNode.name]!!
                            ))
                        } else //Se il nuovo link è negativo
                        {
                            var oldType = relationships[middleNode.name]!!
                            relationships[middleNode.name] =
                                if (relationships[middleNode.name] != Relationship.AMICIZIA_pos) relationships[middleNode.name]!!.decrease() else relationships[middleNode.name]!!
                            modifyCT(middleNode.name, diff, true, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, middleNode),
                                oldType,
                                relationships[middleNode.name]!!
                            ))
                        }
                    } else //Se il link che collega il nodo di intermezzo a quello finale è negativo
                    {
                        if (event.newType.value > event.oldType.value) //Se il nuovo link è positivo
                        {
                            var oldType = relationships[directNode.name]!!
                            relationships[directNode.name] =
                                if (relationships[directNode.name] != Relationship.CONFLITTO_neg) relationships[directNode.name]!!.decrease() else relationships[directNode.name]!!
                            modifyCT(directNode.name, diff, false, oldType)
                            propagate(ChangeRelationshipLTE(
                                Clock.getCurrentDateTime(),
                                Pair(this, directNode),
                                oldType,
                                relationships[directNode.name]!!
                            ))
                        } else //Se il nuovo link è negativo
                        {
                            if (event.newType.value < l.linkType.value) {//Se il nuovo link è peggiore di quello diretto
                                var oldType = relationships[directNode.name]!!
                                relationships[directNode.name] = relationships[directNode.name]!!.increase()
                                modifyCT(directNode.name, diff, false, oldType)
                                propagate(ChangeRelationshipLTE(
                                    Clock.getCurrentDateTime(),
                                    Pair(this, directNode),
                                    oldType,
                                    relationships[directNode.name]!!
                                ))
                            }
                        }
                    }
                } else { //Se il link che collega me e il nodo di intermezzo è negativo
                    if (lastLink.linkType.value > 0) //Se il link che collega il nodo di intermezzo a quello finale è positivo
                    {
                        if (event.newType.value > event.oldType.value) //Se il nuovo link è positivo
                        {
                            //Non succede niente
                        } else //Se il nuovo link è negativo
                        {
                            if (event.newType.value < l.linkType.value) {
                                var oldType = relationships[directNode.name]!!
                                relationships[directNode.name] = relationships[directNode.name]!!.increase()
                                modifyCT(directNode.name, diff, true, oldType)
                                propagate(ChangeRelationshipLTE(
                                    Clock.getCurrentDateTime(),
                                    Pair(this, directNode),
                                    oldType,
                                    relationships[directNode.name]!!
                                ))
                            }
                        }
                    } else //Se il link che collega il nodo di intermezzo a quello finale è negativo
                    {
                        if (event.newType.value > event.oldType.value) //Se il nuovo link è positivo
                        {
                            if (lastLink.linkType.value <= middleLink.linkType.value) {
                                var oldType = relationships[directNode.name]!!
                                relationships[directNode.name] = relationships[directNode.name]!!.increase()
                                modifyCT(directNode.name, diff, true, oldType)
                                propagate(ChangeRelationshipLTE(
                                    Clock.getCurrentDateTime(),
                                    Pair(this, directNode),
                                    oldType,
                                    relationships[directNode.name]!!
                                ))
                            }
                        } else //Se il nuovo link è negativo
                        {
                            //Non succede niente
                        }
                    }
                }
            }
        }
    }
    //TODO to check
    override fun recursiveFind(group: ConceptualMap, limit: Int, visited: MutableSet<ConceptualMap>, path: MutableList<Link>, allPaths: MutableList<MutableList<Link>>): MutableList<MutableList<Link>>{
        visited.add(this)

        if(this == group) {
            allPaths.add(ArrayList(path))
            return allPaths
        }

        if(limit<=0)
            return allPaths

        links.forEach{ link ->
            val nextNode = if(link.a == this) link.b else link.a
            if(nextNode !in visited){
                path.add(link)
                nextNode.recursiveFind(group, limit-1, visited, path, allPaths)
                path.removeAt(path.size - 1)
            }
        }
        visited.remove(this)
        return allPaths
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

        val rndType = MedievalEventType.random()
        val rndImp = MedievalEventImportance.random()

        generateRandomEvent(rndType, rndImp, npcs[index1], group.npcs[index2])
    }

    override fun generateRandomEvent(type: EventType) {
        val rndImp = MedievalEventImportance.random()
        val index1 = Random.nextInt(npcs.size)
        val groupIndex = Random.nextInt(links.size+1)
        var group: ConceptualMap = this
        if(groupIndex!=links.size){
            group = links.toList()[groupIndex].a
        }
        val index2 = Random.nextInt(group.npcs.size)
        generateRandomEvent(type, rndImp, npcs[index1], group.npcs[index2])
    }

    override fun generateRandomEvent(importance: EventImportance) {
        val rndType = MedievalEventType.random()
        val index1 = Random.nextInt(npcs.size)
        val groupIndex = Random.nextInt(links.size+1)
        var group: ConceptualMap = this
        if(groupIndex!=links.size){
            group = links.toList()[groupIndex].a
        }
        val index2 = Random.nextInt(group.npcs.size)
        generateRandomEvent(rndType, importance, npcs[index1], group.npcs[index2])
    }

    override fun generateRandomEvent(type: EventType, importance: EventImportance) {
        val index1 = Random.nextInt(npcs.size)
        val groupIndex = Random.nextInt(links.size+1)
        var group: ConceptualMap = this
        if(groupIndex!=links.size){
            group = links.toList()[groupIndex].a
        }
        val index2 = Random.nextInt(group.npcs.size)
        generateRandomEvent(type, importance, npcs[index1], group.npcs[index2])
    }

    override fun generateRandomEvent(npc1: NPC, npc2: NPC) {
        if(npc1 !in npcs || npc2 !in npcs)
            throw IllegalArgumentException("No npc is intern to this group")
        val rndType = MedievalEventType.random()
        val rndImp = MedievalEventImportance.random()
        generateRandomEvent(rndType, rndImp, npc1, npc2)
    }

    override fun generateRandomEvent(
        type: EventType,
        importance: EventImportance,
        npc1: NPC,
        npc2: NPC
    ) {
        if(npc1 !in npcs || npc2 !in npcs)
            throw IllegalArgumentException("No npc is intern to this group")
        val intern = if(npc1 !in npcs) npc2 else npc1
        val second = if(npc1 in npcs) npc2 else npc1
        intern.generateRandomEvent(second, type, importance)
    }

    override fun receiveGlobalEvent(event: GlobalEvent) {
        events[event] =  npcs.size
        notifyNPCs(event)
    }

    override fun receivedEventFromNpc(npc: NPC, event: Event) {
        events[event] = events[event]!!+1
        eventToPropagate.find { it.event==event }!!.check()
    }

    override fun generateNPC(): NPC {
        TODO("Not yet implemented")
    }

    private fun notifyNPCs(e: AbstractEvent) {
        npcs.forEach { it.addEvent(e) }
    }
}
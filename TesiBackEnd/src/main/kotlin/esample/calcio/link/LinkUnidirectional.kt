package esample.calcio.link

import conceptualMap2.clock.TimerEvent
import conceptualMap2.conceptualMap.CommunicationLevel
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Link
import conceptualMap2.event.ChangeRelationshipLTE
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.event.LocalEvent
import esample.calcio.event.impl.FootballEI
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.random.Random

interface WeightContext {
    fun getCounters(): Map<EventType, Map<EventImportance, Int>>
    fun getCommunicationLevel(): CommunicationLevel
    fun setCommunicationLevel(communicationLevel: CommunicationLevel)
    fun changeRelationshipType(evt: ChangeRelationshipLTE)
}

/**
 * @param a the link through the other node
 * @param weight is a function that act on event statistics updating them according some criteria
 * @param filter all the event that must be propagated or not. Return true if the event is filtered and must be stopped from propagation
 */
class LinkUnidirectional(
    a: ConceptualMap,
    influenceType: LinkTypeImpl,
    communicationLvl: CommunicationLevelImpl,
    val weight: (context: WeightContext, event: Event) -> LocalEvent,
    val filter: (event: Event) -> Boolean
): Link(a, influenceType, communicationLvl), WeightContext {
    private val l = mutableListOf<TimerEvent>()
    private val counters = mutableMapOf<EventType, MutableMap<EventImportance, Int>>()

    override fun propagate(event: Event) {
        if(++(event.linkCnt) > 3 || (event is ChangeRelationshipLTE && event.linkCnt > 1))
            return
        if(!filter(event)) {
            if(counters[event.type] != null)
                if(counters[event.type]!![event.importance] != null)
                    counters[event.type]!![event.importance] = counters[event.type]!![event.importance]!!+1
                else
                    counters[event.type]!![event.importance] = 1
            else
                counters[event.type] = mutableMapOf(event.importance to 1)
            val e = weight(this, event)
            l.add(
                TimerEvent(
                    e,
                    computeTime(e),
                ) { a.generateEvent(e) }
            )
        }
    }

    override fun computeTime(event: Event): Duration {
        val t = when(linkType){
            LinkTypeImpl.DIRETTIVA -> .9f
            LinkTypeImpl.SUPPORTIVA -> .4f
            LinkTypeImpl.COLLABORATIVA -> .7f
            else -> TODO("Not implemented yet")
        } * when (communicationLvl) {
            CommunicationLevelImpl.ALTO -> 1f
            CommunicationLevelImpl.MEDIO -> .6f
            CommunicationLevelImpl.BASSO -> .3f
            else -> TODO("Not implemented yet")
        } * when (event.importance){
            FootballEI.BANALE -> .1f
            FootballEI.NORMALE -> .4f
            FootballEI.IMPORTANTE -> .7f
            FootballEI.CRUCIALE -> 1f
            else -> TODO("Not implemented yet")
        }
        val minutes = ((1/t)*900).toLong()+Random.nextInt(-5, 5)
        return Duration.of(minutes, ChronoUnit.MINUTES)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LinkUnidirectional) return false
        return a != other.a
    }

    override fun hashCode(): Int {
        return a.hashCode()
    }

    override fun getCounters(): Map<EventType, Map<EventImportance, Int>> {
        return counters.mapValues { it.value.toMap() }
    }

    override fun getCommunicationLevel(): CommunicationLevel {
        return communicationLvl
    }

    override fun setCommunicationLevel(communicationLevel: CommunicationLevel) {
        _communicationLvl = communicationLvl
    }

    override fun changeRelationshipType(evt: ChangeRelationshipLTE) {
        a.generateEvent(evt)
    }
}
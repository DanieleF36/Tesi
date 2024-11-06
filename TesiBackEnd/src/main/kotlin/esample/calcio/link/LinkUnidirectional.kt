package esample.calcio.link

import conceptualMap2.clock.TimerEvent
import conceptualMap2.conceptualMap.CommunicationLevel
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.InfluenceType
import conceptualMap2.conceptualMap.Link
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import conceptualMap2.event.LocalEvent
import esample.calcio.event.impl.FootballEI
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.random.Random

/**
 * @param a the link through the other node
 * @param weight is a function that act on event statistics updating them according some criteria
 * @param filter all the event that must be propagated or not. Return true if the event is filtered and must be stopped from propagation
 */
class LinkUnidirectional(
    a: ConceptualMap,
    influenceType: InfluenceTypeImpl,
    communicationLvl: CommunicationLevelImpl,
    val weight: (event: Event) -> LocalEvent,
    val filter: (event: Event) -> Boolean
): Link(a, influenceType, communicationLvl) {
    private val l = mutableListOf<TimerEvent>()
    override fun propagate(event: Event) {
        if(!filter(event))
            l.add(
                TimerEvent(
                    weight(event),
                    computeTime(weight(event)),
                ) { a.generateEvent(weight(event)) })
    }

    override fun computeTime(event: Event): Duration {
        val t = when(influenceType){
            InfluenceTypeImpl.DIRETTIVA -> .9f
            InfluenceTypeImpl.SUPPORTIVA -> .4f
            InfluenceTypeImpl.COLLABORATIVA -> .7f
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
}
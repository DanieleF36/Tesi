package esample.medievale.link

import conceptualMap2.clock.TimerEvent
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Link
import conceptualMap2.conceptualMap.LinkType
import conceptualMap2.event.*
import esample.medievale.event.pureEvent.MedievalEventImportance
import java.time.Duration
import java.time.temporal.ChronoUnit

class BidirectionalLink(
    _type: LinkType,
    val a: ConceptualMap,
    val b: ConceptualMap,
    val distance: Distance,
    val weight: (context: WeightContext, event: AbstractEvent) -> LocalEvent,
    val filter: (event: AbstractEvent) -> Boolean
): Link(_type), WeightContext {
    private val historicalContextContribution = 10f
    private val l = mutableListOf<TimerEvent>()
    private val counters = mutableMapOf<EventType, MutableMap<EventImportance, Int>>()

    override fun propagate(event: AbstractEvent, sender: ConceptualMap) {
        val receiver = if(a == sender) b else a
        if(event is Event) {
            updateCounter(event)
            val e = weight(this, event)
            if (!filter(event))
                l.add(
                    TimerEvent(
                        e,
                        computeTime(e),
                    ) { receiver.receiveEvent(e) }
                )
        }
        else{
            l.add(
                TimerEvent(
                    event,
                    computeTime(event),
                ) { receiver.receiveEvent(event) }
            )
        }
    }

    override fun computeTime(event: AbstractEvent): Duration {
        if(event is Event)
            return Duration.of(
                (distance.contribution*historicalContextContribution*convertContributionEventImportance(event.importance as MedievalEventImportance)*convertContributionLinkType(_type)).toLong(),
                ChronoUnit.MONTHS
            )
        return Duration.of(
            (distance.contribution*historicalContextContribution*convertContributionEventImportance(MedievalEventImportance.IMPORTANTE)*convertContributionLinkType(_type)).toLong(),
            ChronoUnit.MONTHS
        )
    }

    private fun convertContributionEventImportance(imp: MedievalEventImportance): Float{
        return when(imp){
            MedievalEventImportance.BANALE -> 1.2f
            MedievalEventImportance.NORMALE -> 1f
            MedievalEventImportance.IMPORTANTE -> .8f
            MedievalEventImportance.CRUCIALE -> .5f
            else -> TODO("Not implemented yet")
        }
    }

    private fun convertContributionLinkType(type: LinkType): Float{
        TODO()
    }

    private fun updateCounter(event: Event) {
        if (!filter(event)) {
            if (counters[event.type] != null)
                if (counters[event.type]!![event.importance] != null)
                    counters[event.type]!![event.importance] = counters[event.type]!![event.importance]!! + 1
                else
                    counters[event.type]!![event.importance] = 1
            else
                counters[event.type] = mutableMapOf(event.importance to 1)
        }
    }

    override fun getCounters(): Map<EventType, Map<EventImportance, Int>> {
        return counters.mapValues { it.value.toMap() }
    }

    override fun changeRelationshipType(evt: ChangeRelationshipLTE, sender: ConceptualMap) {
        _type = evt.newType
        val receiver = if(a == sender) b else a
        l.add(
            TimerEvent(
                evt,
                computeTime(),
            ) { receiver.receiveEvent(evt) }
        )
    }

    private fun computeTime(): Duration {
        return Duration.of(
            (distance.contribution*historicalContextContribution*convertContributionEventImportance(MedievalEventImportance.CRUCIALE)*convertContributionLinkType(_type)).toLong(),
            ChronoUnit.MONTHS
        )
    }
}

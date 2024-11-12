package esample.medievale.realEsample.map.links

import conceptualMap2.event.PureEvent
import esample.medievale.WeightSimpleMood
import esample.medievale.event.NewCTAfterLTCE
import esample.medievale.event.pureEvent.MedievalEventImportance
import esample.medievale.link.BidirectionalLink
import esample.medievale.link.Distance
import esample.medievale.link.Relationship
import esample.medievale.realEsample.map.cities.*

val aToC = BidirectionalLink(
    _type = Relationship.CONFLITTO_neg,
    a = aronathConceptualMap,
    b = cyrendilConceptualMap,
    distance = Distance.MEDIO_BASSA,
    weight = { context, event ->
        when(event){
            is PureEvent -> pureEvent(event)
            is NewCTAfterLTCE -> event
            else -> TODO("not implemented yet")
        }
    },
    filter = { _, it ->
        when(it){
            is PureEvent -> it.importance == MedievalEventImportance.CRUCIALE || it.importance == MedievalEventImportance.CRUCIALE
            is NewCTAfterLTCE -> true
            else -> TODO("not implemented yet")
        }
    }
)

private fun pureEvent(event: PureEvent): PureEvent{
    val towardsCorA = event.personGenerated!!.first.group == aronathConceptualMap || event.personGenerated!!.first.group == cyrendilConceptualMap ||
                      event.personGenerated!!.second.group == aronathConceptualMap || event.personGenerated!!.second.group == cyrendilConceptualMap
    val weight = if(!towardsCorA)WeightSimpleMood(1f, 1f, 1.2f) else WeightSimpleMood(1.2f, .7f, .5f)
    val newStat = weight.update(event.statistic)
    return PureEvent(
        event.type,
        event.importance,
        newStat,
        event.description,
        event.generatedTime,
        event.confidentiality,
        event.personGenerated,
        event.generationPlace
    )
}
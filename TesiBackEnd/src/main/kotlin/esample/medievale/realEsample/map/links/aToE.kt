package esample.medievale.realEsample.map.links

import conceptualMap2.clock.Clock
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.LinkType
import conceptualMap2.event.ChangeRelationshipLTE
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.event.PureEvent
import esample.medievale.WeightSimpleMood
import esample.medievale.event.NewCTAfterLTCE
import esample.medievale.event.pureEvent.MedievalEventImportance
import esample.medievale.link.BidirectionalLink
import esample.medievale.link.Distance
import esample.medievale.link.Relationship
import esample.medievale.link.Relationship.Companion.AMICIZIA_neg
import esample.medievale.link.Relationship.Companion.AMICIZIA_pos
import esample.medievale.link.Relationship.Companion.CONFLITTO_neg
import esample.medievale.link.Relationship.Companion.NEMICO_neg
import esample.medievale.link.Relationship.Companion.NEMICO_pos
import esample.medievale.link.Relationship.Companion.NEUTRALE_neg
import esample.medievale.link.Relationship.Companion.NEUTRALE_pos
import esample.medievale.link.Relationship.Companion.RIVALE_neg
import esample.medievale.link.Relationship.Companion.RIVALE_pos
import esample.medievale.link.WeightContext
import esample.medievale.realEsample.map.cities.*

val aToE = BidirectionalLink(
    _type = CONFLITTO_neg,
    a = aronathConceptualMap,
    b = ellesmereConceptualMap,
    distance = Distance.MEDIO,
    weight = { context, event ->
        when(event){
            is PureEvent -> pureEvent(context, event, CONFLITTO_neg)
            is NewCTAfterLTCE -> event
            else -> TODO("not implemented yet")
        }
    },
    filter = { link, event ->
        when(event){
            is PureEvent -> if(link.linkType.value>=-1 || link.linkType.value<=1)
                    event.importance == MedievalEventImportance.CRUCIALE || event.importance == MedievalEventImportance.IMPORTANTE
                else
                    event.importance == MedievalEventImportance.CRUCIALE
            is NewCTAfterLTCE -> true
            else -> TODO("not implemented yet")
        }
    }
)

private fun pureEvent(context: WeightContext, event: PureEvent, type: LinkType): PureEvent{
    updateLinkType(context, event, type)
    val towardsEorA = event.personGenerated!!.first.group == aronathConceptualMap || event.personGenerated!!.first.group == ellesmereConceptualMap ||
            event.personGenerated!!.second.group == aronathConceptualMap || event.personGenerated!!.second.group == ellesmereConceptualMap
    val weight = if(!towardsEorA)WeightSimpleMood(1f, 1f, 1.2f) else WeightSimpleMood(1.2f, .7f, .5f)
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
val map = mutableMapOf<Relationship, MutableMap<Boolean, MutableMap<EventImportance, Int>>>()
private fun updateLinkType(context: WeightContext, event: PureEvent, type: LinkType){
    if(event.type.isPositive()>0){
        when(type){
            AMICIZIA_neg -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), true)
            NEUTRALE_pos -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE, MedievalEventImportance.IMPORTANTE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), true)
            NEUTRALE_neg -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE, MedievalEventImportance.IMPORTANTE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), true)
            RIVALE_pos -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), true)
            RIVALE_neg -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), true)
            NEMICO_pos -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), true)
            NEMICO_neg -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), true)
            else -> TODO()
        }
    } else if(event.type.isPositive()<0){
        when(type){
            AMICIZIA_neg -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), false)
            NEUTRALE_pos -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE, MedievalEventImportance.IMPORTANTE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), false)
            NEUTRALE_neg -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE, MedievalEventImportance.IMPORTANTE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), false)
            RIVALE_pos -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), false)
            RIVALE_neg -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), false)
            NEMICO_pos -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), false)
            NEMICO_neg -> computeChangeRelationship(context, listOf(MedievalEventImportance.CRUCIALE), event.importance as MedievalEventImportance, type as Relationship, Pair(aronathConceptualMap, ellesmereConceptualMap), false)
            else -> TODO()
        }
    }
}

private fun computeChangeRelationship(context: WeightContext, importance: List<MedievalEventImportance>, actualImportance: MedievalEventImportance, relationship: Relationship, groups: Pair<ConceptualMap, ConceptualMap>, pos: Boolean){
    val n = getNumbers(context.getCounters(), importance, pos)
    var m = 0
    importance.forEach {
        if (map[relationship] != null && map[relationship]!![pos] != null && map[relationship]!![pos]!![it] != null)
            m+=map[relationship]!![pos]!![it]!!
    }
    if (m != n && n % 3 == 0 && relationship != AMICIZIA_pos) {
            context.changeRelationshipType(
                ChangeRelationshipLTE(
                    Clock.getCurrentDateTime(),
                    groups,
                    relationship,
                    if(pos)relationship.increase() else relationship.decrease()
                ), groups.first
            )
            map[relationship]!![pos]!![actualImportance] = map[relationship]!![pos]!![actualImportance]!! + 1
    } else if (map[relationship] == null)
        map[relationship] = mutableMapOf(true to mutableMapOf(actualImportance to 1))
    else if (map[relationship]!![pos] == null)
        map[relationship]!![pos] = mutableMapOf(actualImportance to 1)
    else if (map[relationship]!![pos]!![actualImportance] == null)
        map[relationship]!![pos]!![actualImportance] = 1
}

private fun getNumbers(map: Map<EventType, Map<EventImportance, Int>>, importance: List<MedievalEventImportance>, pos:Boolean): Int{
    var cnt = 0
    map.forEach { (k, v) ->
        if(pos && k.isPositive()>0)
            importance.forEach { cnt+=v[it]!! }
        else if(!pos && k.isPositive()<0)
            importance.forEach { cnt+=v[it]!! }
    }
    return cnt
}
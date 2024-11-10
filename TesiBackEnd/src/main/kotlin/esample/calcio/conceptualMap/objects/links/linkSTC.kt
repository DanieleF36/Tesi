package esample.calcio.conceptualMap.objects.links

import conceptualMap2.conceptualMap.Link
import conceptualMap2.event.PureEvent
import esample.calcio.conceptualMap.objects.nodes.calciatori
import esample.calcio.conceptualMap.objects.nodes.staffTecnico
import esample.calcio.event.impl.FootballEI
import esample.calcio.event.impl.FootballET
import esample.calcio.link.CommunicationLevelImpl
import esample.calcio.link.LinkTypeImpl
import esample.calcio.link.LinkUnidirectional
import esample.calcio.link.WeightContext
import esample.calcio.npc.footballer.personality.WeighedMood

val linkSTC: Link = LinkUnidirectional(
    a = calciatori,
    influenceType = LinkTypeImpl.COLLABORATIVA,
    communicationLvl = CommunicationLevelImpl.ALTO,
    weight = {context: WeightContext, event: PureEvent ->
        if((event.personGenerated!!.first != staffTecnico && event.personGenerated!!.second != calciatori) ||
            (event.personGenerated!!.second != staffTecnico && event.personGenerated!!.first != calciatori))
            propagatedEvent(context, event)
        else if(event.personGenerated!!.first != staffTecnico && event.personGenerated!!.second != staffTecnico)
            internalEvent(context, event)
        else
            eventBetweenCD(context, event)
    },
    filter = {it.importance == FootballEI.BANALE || it.type == FootballET.NESSUNA  ||
            (it.statistic.stress<=.3f && it.statistic.anger<=.3f && it.statistic.satisfaction<=.3f)},
)

private fun propagatedEvent(context: WeightContext, event: PureEvent): PureEvent{
    val weight = WeighedMood(.45f, .45f, .45f)
    return PureEvent(
        event.type,
        event.importance,
        weight.update(event.statistic),
        event.description,
        event.generatedTime,
        event.personGenerated,
        event.generationPlace
    )
}

private fun internalEvent(context: WeightContext, event: PureEvent): PureEvent{
    val n = if(event.type == FootballET.FIDUCIA)
        if(event.importance == FootballEI.IMPORTANTE) {
            if(context.getCounters()[event.type]!![event.importance]!!%10 == 0)
                context.setCommunicationLevel(context.getCommunicationLevel().increase())
            context.getCounters()[event.type]!![event.importance]!! / 100f
        }
        else {
            if(context.getCounters()[event.type]!![event.importance]!!%10 == 0)
                context.setCommunicationLevel(context.getCommunicationLevel().increase())
            context.getCounters()[event.type]!![event.importance]!! / 10f
        }
    else
        if(event.importance == FootballEI.IMPORTANTE) {
            if(context.getCounters()[event.type]!![event.importance]!!%2 == 0)
                context.setCommunicationLevel(context.getCommunicationLevel().decrease())
            context.getCounters()[event.type]!![event.importance]!! / -20f
        }
        else {
            context.setCommunicationLevel(context.getCommunicationLevel().decrease())
            context.getCounters()[event.type]!![event.importance]!! / -1f
        }
    val weight = if(event.importance == FootballEI.IMPORTANTE)
        WeighedMood(.7f+n, .7f+n, .7f+n)
    else
        WeighedMood(.8f+n, 1f+n, 1f+n)
    return PureEvent(
        event.type,
        event.importance,
        weight.update(event.statistic),
        event.description,
        event.generatedTime,
        event.personGenerated,
        event.generationPlace
    )
}

private fun eventBetweenCD(context: WeightContext, event: PureEvent): PureEvent{
    val n = if(event.type == FootballET.FIDUCIA)
        if(event.importance == FootballEI.IMPORTANTE) {
            if(context.getCounters()[event.type]!![event.importance]!!%10 == 0)
                context.setCommunicationLevel(context.getCommunicationLevel().increase())
            context.getCounters()[event.type]!![event.importance]!! / 100f
        }
        else {
            if(context.getCounters()[event.type]!![event.importance]!!%10 == 0)
                context.setCommunicationLevel(context.getCommunicationLevel().increase())
            context.getCounters()[event.type]!![event.importance]!! / 10f
        }
    else
        if(event.importance == FootballEI.IMPORTANTE) {
            if(context.getCounters()[event.type]!![event.importance]!!%2 == 0)
                context.setCommunicationLevel(context.getCommunicationLevel().decrease())
            context.getCounters()[event.type]!![event.importance]!! / -20f
        }
        else {
            context.setCommunicationLevel(context.getCommunicationLevel().decrease())
            context.getCounters()[event.type]!![event.importance]!! / -1f
        }
    val weight = if(event.importance == FootballEI.IMPORTANTE){
        if(event.type == FootballET.FIDUCIA)
            WeighedMood(.6f+n, .6f+n, .6f+n)
        else
            WeighedMood(.8f+n, .9f+n, 1f+n)
    }
    else
        if(event.type == FootballET.FIDUCIA)
            WeighedMood(.7f+n, .7f+n, .7f+n)
        else
            WeighedMood(.6f+n, 1.2f+n, 1.2f+n)
    return PureEvent(
        event.type,
        event.importance,
        weight.update(event.statistic),
        event.description,
        event.generatedTime,
        event.personGenerated,
        event.generationPlace
    )
}
package esample.calcio.conceptualMap

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import esample.calcio.event.impl.FootballEI
import esample.calcio.npc.footballer.personality.SimpleMood
import java.io.File


internal fun readFile(filePath: String): String {
    return File(filePath).readText()
}
/*
fun initMap(){
    calciatori.addLink(dirigenti, CommonThought(0.1f, 0.1f, 0.2f, 0.2f, 0.2f)) { it.importance != FootballEI.BANALE  }
    calciatori.addLink(staffTecnico, CommonThought(0.2f, 0.1f, 0.2f, 0.2f, 0.2f)){it.importance != FootballEI.BANALE}
    calciatori.addLink(staffSupporto, CommonThought(0.1f,0f,0.1f, 0f, 0.1f)){ !(it.importance == FootballEI.BANALE || it.importance == FootballEI.NORMALE) }
    dirigenti.addLink(staffTecnico, CommonThought(0.2f, 0.1f, 0.2f, 0.2f, 0.2f)){it.importance != FootballEI.BANALE}
    dirigenti.addLink(staffSupporto, CommonThought(0f,0f,0.1f, 0f, 0.1f)){ !(it.importance == FootballEI.BANALE || it.importance == FootballEI.NORMALE || it.importance == FootballEI.IMPORTANTE) }
    staffTecnico.addLink(staffSupporto, CommonThought(0.1f,0f,0.1f, 0.1f, 0.1f)){ !(it.importance == FootballEI.BANALE || it.importance == FootballEI.NORMALE) }
}*/

fun initMap(){

}

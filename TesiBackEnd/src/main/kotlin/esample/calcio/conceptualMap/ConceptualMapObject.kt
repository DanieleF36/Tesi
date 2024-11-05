package esample.calcio.conceptualMap

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.event.Event
import conceptualMap2.event.EventImportance
import esample.calcio.event.impl.FootballEI
import esample.calcio.npc.footballer.personality.SimpleMood
import java.io.File


private fun readFile(filePath: String): String {
    return File(filePath).readText()
}
/*
val calciatori: ConceptualMap = ConceptualMapImpl("calciatori", readFile("C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\npc\\backstory\\giocatori.txt"), CommonThought(0.7f, 0.9f, 1f, 1f, 0.6f))
val dirigenti: ConceptualMap = ConceptualMapImpl("dirigenti", readFile("C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\npc\\backstory\\dirigenti.txt"), CommonThought(0.3f, 0.7f, 0.9f, 0.4f, 0.8f))
val staffTecnico: ConceptualMap = ConceptualMapImpl("staff tecnico", readFile("C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\npc\\backstory\\staffTecnico.txt"), CommonThought(0.7f, 0.9f, 0.9f, 1f, 0.3f))
val staffSupporto: ConceptualMap = ConceptualMapImpl("staff di supporto", readFile("C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\npc\\backstory\\staffSupporto.txt"), CommonThought(0.4f, 0.6f, 1f, 1f, 0.1f))
/*
fun initMap(){
    calciatori.addLink(dirigenti, CommonThought(0.1f, 0.1f, 0.2f, 0.2f, 0.2f)) { it.importance != FootballEI.BANALE  }
    calciatori.addLink(staffTecnico, CommonThought(0.2f, 0.1f, 0.2f, 0.2f, 0.2f)){it.importance != FootballEI.BANALE}
    calciatori.addLink(staffSupporto, CommonThought(0.1f,0f,0.1f, 0f, 0.1f)){ !(it.importance == FootballEI.BANALE || it.importance == FootballEI.NORMALE) }
    dirigenti.addLink(staffTecnico, CommonThought(0.2f, 0.1f, 0.2f, 0.2f, 0.2f)){it.importance != FootballEI.BANALE}
    dirigenti.addLink(staffSupporto, CommonThought(0f,0f,0.1f, 0f, 0.1f)){ !(it.importance == FootballEI.BANALE || it.importance == FootballEI.NORMALE || it.importance == FootballEI.IMPORTANTE) }
    staffTecnico.addLink(staffSupporto, CommonThought(0.1f,0f,0.1f, 0.1f, 0.1f)){ !(it.importance == FootballEI.BANALE || it.importance == FootballEI.NORMALE) }
}*/
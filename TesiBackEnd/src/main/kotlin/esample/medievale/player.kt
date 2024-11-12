package esample.medievale

import conceptualMap2.event.AbstractEvent
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.npc.NPC
import conceptualMap2.npc.knowledge.Knowledge
import esample.medievale.npc.knowledge.SimpleContext
import esample.medievale.realEsample.map.cities.faelwenConceptualMap

val player: NPC = object : NPC(
    _name = "player",
    group = faelwenConceptualMap,
    context = Knowledge(SimpleContext(""), SimpleContext(""), SimpleContext(""), SimpleContext(""))
){
    override fun initialize() {
        println("USER")
    }

    override fun endConversation() {
        println("USER")
    }

    override fun generateRandomEvent(npc: NPC, type: EventType, importance: EventImportance) {
        println("USER")
    }

    override fun toMap(): Map<String, Any?> {
        println("USER")
        return mapOf()
    }

    override fun addEvent(event: AbstractEvent) {
        println("USER")
    }

}
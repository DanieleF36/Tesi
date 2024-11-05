package conceptualMap2

import conceptualMap2.event.Event
import conceptualMap2.npc.NPC

interface Game {
    fun generateGlobalEvent(event: Event)
    fun getNPC(npcId: Int): NPC?
    fun getNPCs(): List<NPC>
    fun getGroupsName(): List<String>
}
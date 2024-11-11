package esample.medievale.npc

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.event.AbstractEvent
import conceptualMap2.event.EventImportance
import conceptualMap2.event.EventType
import conceptualMap2.npc.Mood
import conceptualMap2.npc.NPC
import conceptualMap2.npc.Personality
import conceptualMap2.npc.knowledge.Knowledge
import conceptualMap2.npc.task.Task

class Citizen(
    _age: Int? = null,
    _name: String? = null,
    val sex: Boolean? = null,
    group: ConceptualMap,
    context: Knowledge,
    _story: String? = null,
    _personality: Personality? = null,
    tasks: MutableList<Task> = mutableListOf(),
    _mood: Mood? = null,
    _thoughtOnPlayer: CommonThought?,
    val job: Job,
): NPC(_age = _age, _name = _name, group = group, context = context, _story = _story, _personality = _personality, tasks = tasks, _mood = _mood, _thoughtOnPlayer = _thoughtOnPlayer) {

    override fun initialize() {
        val m = toMap()
        val comments = mapOf(
            "" to ""
        )
    }

    override fun endConversation() {
        TODO("Not yet implemented")
    }

    override fun generateRandomEvent(npc: NPC, type: EventType, importance: EventImportance) {
        TODO("Not yet implemented")
    }

    override fun toMap(): Map<String, Any> {
        TODO("Not yet implemented")
    }

    override fun addEvent(event: AbstractEvent) {
        TODO("Not yet implemented")
    }
}
package esample.calcio.npc.manager;

import conceptualMap2.ConceptualMap
import conceptualMap2.Statistic
import conceptualMap2.npc.NPC
import conceptualMap2.npc.task.Task
import conceptualMap2.npc.knowledge.Knowledge
import conceptualMap2.npc.Personality
import esample.calcio.conceptualMap.CommonThought

class Manager(
    age: Int? = null,
    name: String? = null,
    group: ConceptualMap,
    context: Knowledge,
    story: String? = null,
    personality: Personality? = null,
    tasks: MutableList<Task> = mutableListOf(),
    mood: Statistic? = null,
    thoughtOnPlayer: CommonThought,
    private val role: ManagerRole
): NPC(_age = age, _name=name, group=group, context=context, story=story, personality=personality, thoughtOnPlayer=thoughtOnPlayer, tasks=tasks, mood=mood) {
    init{
        engine.addDetails(mapOf("role" to role.toString()), mapOf())
    }
}

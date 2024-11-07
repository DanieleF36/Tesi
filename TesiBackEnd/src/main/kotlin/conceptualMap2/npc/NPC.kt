package conceptualMap2.npc

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.event.Event
import conceptualMap2.moduleDagger2.*
import conceptualMap2.npc.task.Task
import conceptualMap2.npc.knowledge.Knowledge
import java.io.Closeable
import javax.inject.Inject

/**
 * @param id it is its uid
 * @param _name its name
 * @param context this is used to describe his knowledge about the game world
 * @param story the personal story of the NPC
 * @param personality its personality
 * @param tasks that the NPC aim to complete
 * @param mood
 */
abstract class NPC (
    val id: Int = ++idNpc,
    protected var _age: Int? = null,
    protected var _name: String? = null,
    val group: ConceptualMap,
    val context: Knowledge,
    protected var _story: String? = null,
    protected var _personality: Personality? = null,
    val tasks: MutableList<Task> = mutableListOf(),
    protected var _mood: Mood? = null,
    protected var _thoughtOnPlayer: CommonThought?,
): Closeable {
    companion object {protected var idNpc = 0}
    @Inject lateinit var engine: NPCEngine
    val age: Int?
        get() = _age
    val name: String?
        get() = _name
    val story: String?
        get() = _story
    val personality: Personality?
        get() = _personality
    val mood: Mood?
        get() = _mood
    val thoughtOnPlayer: CommonThought?
        get() = _thoughtOnPlayer

    internal fun setAge(age: Int){
        _age = age
    }
    internal fun setName(name: String){
        _name = name
    }
    internal fun setStory(story: String){
        _story = story
    }
    internal fun setPersonality(p: Personality){
        _personality = p
    }
    internal fun setMood(mood: Mood){
        _mood = mood
    }
    internal fun setThoughtOnPlayer(thoughtOnPlayer: CommonThought){
        _thoughtOnPlayer = thoughtOnPlayer
    }

    /**
    * If the backstory is missing the group one will be used
    * If the personality is missing the IA will generate that
    * If both of them are missing the IA will take care of it
    **/
    init {
        DaggerMyComponent.create().inject(this)
        group.attach(this)
    }
    /**
     * this function will initialize allowing the user to talk with it
     */
    protected abstract fun initialize()
    /**
     * @param input is what the user wants to say to the NPC
     * @return the answer to that input
     */
    open fun insertTextInput(input: String): String = engine.talk(input)
    /**
     * this function will end the conversation and generate an event that will be propagated
     */
    abstract fun endConversation()
    /**
     * this function will generate a random event between this and npc
     */
    abstract fun generateRandomEvent(npc: NPC)

    abstract fun toMap(): Map<String, Any>

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (javaClass != other?.javaClass)
            return false
        other as NPC
        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "NPC(name='$_name')"
    }

    override fun close() {
        group.detach(this)
    }

    abstract fun addEvent(event: Event)
}
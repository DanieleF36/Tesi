package conceptualMap2.npc

import conceptualMap2.ConceptualMap
import conceptualMap2.Statistic
import conceptualMap2.event.Event
import conceptualMap2.moduleDagger2.DaggerMyComponent
import conceptualMap2.npc.task.Task
import conceptualMap2.npc.knowledge.Knowledge
import observerInterfaces.Observer
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
open class NPC (
    val id: Int = ++idNpc,
    protected var _age: Int? = null,
    protected var _name: String? = null,
    val group: ConceptualMap,
    protected  val context: Knowledge,
    protected var story: String? = null,
    protected var personality: Personality? = null,
    val tasks: MutableList<Task> = mutableListOf(),
    protected var mood: Statistic? = null,
    protected val thoughtOnPlayer: Statistic,
): Observer, Closeable {
    companion object {protected var idNpc = 0}
    @Inject lateinit var engine: NPCEngine
    val age: Int
        get() = _age!!
    val name: String
        get() = _name!!
    /**
    * If the backstory is missing the group one will be used
    * If the personality is missing the IA will generate that
    * If both of them are missing the IA will take care of it
    **/
    init {
        DaggerMyComponent.create().inject(this)
        group.attach(this)
        if(_name==null || _age==null) {
            val res = engine.generateNameAndAge()
            if(_name==null) _name = res.first
            if(_age==null) _age = res.second
        }
        if(story==null) story = engine.generateStory(_name!!, _age!!, context, group.name, group.description)
        if(personality==null) personality = engine.generatePersonality(context, group.name, group.description, story!!)
        if(mood==null) mood = engine.generateMood(context, group.name, group.description, story!!, personality!!, group.getEventHistory())
        //From now on name, age, mood, story and personality are surely not null
        engine.startNPC(_name!!, _age!!, context, group.name, group.description, story!!, personality!!, mood!!, thoughtOnPlayer, group.getEventHistory(), tasks)
    }

    /**
     * @param input is what the user wants to say to the NPC
     * @return the answer to that input
     */
    fun insertTextInput(input: String): String = engine.talk(input)

    /**
     * this function will end the conversation and generate an event that will be propagated
     */
     open fun endConversation(){
        val event = engine.generateEvent() ?: return
        mood!!.update(event.statistic)
        event.personGenerated = this
        //println("Il mood di $_name è cambiato, diventando $mood, in seguito all'evento: ${event.description}")
        group.generateEvent(event)
        tasks.forEach { it.action(event) }
     }

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

    /** this function is called when a new event arrive in its group */
    override fun update() {
        val evt = group.getEventHistory().last()
        if(evt.personGenerated==this)
            return
        mood!!.update(evt.statistic)
        engine.receiveEvent(evt, mood!!)
        tasks.forEach { it.action(evt) }
        //println("$_name ha cambiato il suo mood, $mood, in seguito all'evento ${evt.description}")
    }

    //for debug
    fun addEvent(event: Event){
        event.personGenerated = this
        mood!!.update(event.statistic)
        engine.receiveEvent(event, mood!!)
        group.generateEvent(event)
    }
}
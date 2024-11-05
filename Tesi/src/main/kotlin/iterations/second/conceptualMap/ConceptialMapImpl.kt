package iterations.second.conceptualMap

import iterations.second.*
import iterations.second.conceptualMap.link.Link
import observerInterfaces.Observer
import java.lang.IllegalStateException
import java.util.concurrent.Executors

class ConceptialMapImpl: ConceptualMap {
    private var name: String = ""
    private var backStory: String = ""
    private var commonThought: Statistic? = null
    private val nodes = mutableListOf<ConceptialMapImpl>()
    private val links = mutableListOf<Link>()
    private val subGroups = mutableListOf<ConceptialMapImpl>()
    private val observers = mutableListOf<Observer>()
    private val events: MutableList<Event> = mutableListOf()
    private var trivialEventPropagationTime: TimeComponent = TimeComponent.DAYS(7)
    private var moderateEventPropagationTime: TimeComponent = TimeComponent.DAYS(2)
    private var crucialEventPropagationTime: TimeComponent = TimeComponent.DAYS(1)
    private val clock: Clock = Clock(Date(Time(0,0,25,12),11,10,2024))

    override fun setCommonThought(commonThought: Statistic) {
        this.commonThought = commonThought
    }

    override fun getCommonThought(): Statistic? {
        return commonThought?.clone()
    }

    override fun setBackstory(backStory: String) {
        this.backStory = backStory
    }

    override fun getBackstory(): String {
        return backStory
    }

    override fun setName(name: String) {
        this.name = name
    }

    override fun getName(): String {
        return name
    }

    override fun getEventsHistory(): List<Event> {
        return events.toList()
    }

    override fun getLastEvent(): Event {
        return events[events.size-1]
    }

    override fun setEventPropagationTime(eventiPriority: EventPriority, time: TimeComponent) {
        when(eventiPriority){
            EventPriority.TRIVIAL -> trivialEventPropagationTime = time
            EventPriority.MODERATE -> moderateEventPropagationTime = time
            EventPriority.CRUCIAL -> crucialEventPropagationTime = time
        }
    }

    override fun addGroup(name: String, backStory: String, commonThought: Statistic, weight: Weight): ConceptualMap {
        val ret = ConceptialMapImpl()
        ret.setName(name)
        ret.setBackstory(backStory)
        ret.setCommonThought(commonThought)
        addLink(ret, weight)
        nodes.add(ret)
        return ret
    }

    override fun removeGroup(name: String): Boolean {
        for(node in nodes)
            if (node.name == name){
                for(link in node.links)
                        node.removeLink(link.node)
                nodes.remove(node)
                return true
            }
        return false
    }

    override fun addSubGroup(name: String, backStory: String, commonThought: Statistic): ConceptualMap {
        if (subGroups.isNotEmpty())
            throw IllegalStateException("There is already one subgroup")
        val ret = ConceptialMapImpl()
        ret.setName(name)
        ret.setBackstory(backStory)
        ret.setCommonThought(commonThought)
        subGroups.add(ret)
        return ret
    }

    override fun removeSubGroup(name: String): Boolean {
        for(sub in subGroups)
            if(sub.name == name){
                for(link in sub.links)
                        sub.removeLink(link.node)
                subGroups.remove(sub)
                return true
            }
        return false
    }

    override fun addLink(node: ConceptualMap, weight: Weight) {
        links.add(Link(node, weight))
    }

    override fun removeLink(group: ConceptualMap): Boolean {
        for(link in links)
            if(link.node == group){
                links.remove(link)
                return true
            }
        return false
    }

    override fun generateEvent(event: Event, propagation: Boolean, subgroup: Boolean) {
        Executors.newSingleThreadExecutor().execute {
            val endDateInside = clock.getDate()
            endDateInside.addTime(event.propagationTimeInside)
            val endDateOutside = clock.getDate()
            endDateInside.addTime(event.propagationTimeOutside)
            val stat = event.statistic.clone()
            if (event.type == EventType.NEGATIVE)
                stat.invert()
            while(endDateInside > clock.getDate())
                clock.waitForChange()
            commonThought?.update(stat)
            events.add(event)
            for (observer in observers)
                observer.update()
            while(endDateOutside > clock.getDate())
                clock.waitForChange()
            if (propagation)
                for (link in links)
                    if (event.isPropagable(link.node))
                        link.propagate(event)
            if (subgroup)
                subGroups[0].generateEvent(event, propagation, subgroup)
        }
    }

    override fun generateNPC(backStory: String?, personality: Statistic?): NPC {
        TODO("Not yet implemented")
    }

    override fun attach(o: Observer) {
        observers.add(o)
    }

    override fun detach(o: Observer) {
        observers.remove(o)
    }
}
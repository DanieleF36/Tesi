package iterations.first.conceptualMap

import iterations.first.*
import iterations.first.Link
import iterations.first.conceptualMap.link.LinkImpl
import iterations.first.event.SimpleEventImportance
import observerInterfaces.Observer
import java.lang.IllegalStateException

class ConceptualMapImpl(commonThought: Statistic, backStory: String, name: String) : ConceptualMap(commonThought, backStory, name) {
    private var name: String = ""
    private var backStory: String = ""
    private var commonThought: Statistic? = null
    private val nodes = mutableListOf<ConceptualMapImpl>()
    private val links = mutableListOf<Link>()
    private val subGroups = mutableListOf<ConceptualMapImpl>()
    private val observers = mutableListOf<Observer>()
    private val events: MutableList<Event> = mutableListOf()

    override fun getEventHistory(): Collection<Event> {
        return events.toList()
    }

    override fun getLastEvent(): Event {
        return events[events.size-1]
    }

    override fun addGroup(name: String, backStory: String, commonThought: Statistic, weight: Weight): ConceptualMap {
        val ret = ConceptualMapImpl(commonThought, backStory, name)
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

    override fun addLink(node: ConceptualMap, weight: Weight) {
        links.add(LinkImpl(node, weight))
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
        if(event.importance != SimpleEventImportance.NORMALE && event.importance != SimpleEventImportance.BANALE) {
            commonThought?.update(event.statistic)
            events.add(event)
        }
        for(observer in observers)
            observer.update()
        if(propagation)
            for(link in links)
                if(event.isPropagable(link.node))
                    link.propagate(event)
        if(subgroup)
            subGroups[0].generateEvent(event, propagation, subgroup)
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
package iterations.first.conceptualMap.link

import iterations.first.*

class LinkImpl(node: ConceptualMap, weight: Weight) : Link(node, weight) {
    override fun propagate(event: Event){
        //filter
        if(event.importance != EventImportance.BANALE){
            weight.update(event)
            node.generateEvent(event)
        }
    }
}
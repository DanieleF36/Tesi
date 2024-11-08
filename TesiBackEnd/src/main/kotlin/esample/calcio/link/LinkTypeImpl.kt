package esample.calcio.link

import conceptualMap2.conceptualMap.LinkType

class LinkTypeImpl(name: String): LinkType(name) {
    companion object {
        val DIRETTIVA = LinkTypeImpl("DIRETTIVA")
        val COLLABORATIVA  = LinkTypeImpl("COLLABORATIVA")
        val SUPPORTIVA = LinkTypeImpl("SUPPORTIVA")
    }
}
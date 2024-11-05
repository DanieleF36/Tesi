package esample.calcio.event.impl

import conceptualMap2.event.EventImportance

class FootballEI(name: String) : EventImportance(name) {
    companion object{
        val BANALE = FootballEI("Banale")
        val NORMALE = FootballEI("Normale")
        val IMPORTANTE = FootballEI("Importante")
        val CRUCIALE = FootballEI("Cruciale")
    }
}
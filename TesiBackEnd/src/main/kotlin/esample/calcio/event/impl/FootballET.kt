package esample.calcio.event.impl

import conceptualMap2.event.EventType

class FootballET(name: String) : EventType(name) {
    companion object{
        val RABBIA = FootballET("RABBIA")
        val FIDUCIA = FootballET("FIDUCIA")
        val SFIDUCIA = FootballET("SFIDUCIA")
        val CRITICA = FootballET("CRITICA")
        val FRUSTAZIONE = FootballET("FRUSTAZIONE")
        val NESSUNA = FootballET("NESSUNA")
    }
}
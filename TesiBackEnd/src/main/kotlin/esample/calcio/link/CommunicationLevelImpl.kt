package esample.calcio.link

import conceptualMap2.conceptualMap.CommunicationLevel

class CommunicationLevelImpl(name: String): CommunicationLevel(name) {
    companion object{
        val BASSO = CommunicationLevelImpl("BASSO")
        val MEDIO = CommunicationLevelImpl("MEDIO")
        val ALTO = CommunicationLevelImpl("ALTO")
    }
}
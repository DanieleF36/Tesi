package esample.calcio.link

import conceptualMap2.conceptualMap.CommunicationLevel

class CommunicationLevelImpl(name: String): CommunicationLevel(name) {
    override fun increase(): CommunicationLevel {
        when(name){
            "BASSO" -> return MEDIO
            "MEDIO" -> return ALTO
            "ALTO" -> return ALTO
            else -> TODO("not implemented yet")
        }
    }

    override fun decrease(): CommunicationLevel {
        when(name){
            "BASSO" -> return BASSO
            "MEDIO" -> return BASSO
            "ALTO" -> return MEDIO
            else -> TODO("not implemented yet")
        }
    }

    companion object{
        val BASSO = CommunicationLevelImpl("BASSO")
        val MEDIO = CommunicationLevelImpl("MEDIO")
        val ALTO = CommunicationLevelImpl("ALTO")
    }
}
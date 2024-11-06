package esample.calcio.link

import conceptualMap2.conceptualMap.InfluenceType

class InfluenceTypeImpl(name: String): InfluenceType(name) {
    companion object {
        val DIRETTIVA = InfluenceTypeImpl("DIRETTIVA")
        val COLLABORATIVA  = InfluenceTypeImpl("COLLABORATIVA")
        val SUPPORTIVA = InfluenceTypeImpl("SUPPORTIVA")
    }
}
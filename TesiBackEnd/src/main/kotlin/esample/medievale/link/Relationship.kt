package esample.medievale.link

import conceptualMap2.conceptualMap.LinkType

class Relationship(value: Int): LinkType(value) {
    companion object {
        val ALLEATO_pos = Relationship(8)
        val ALLEATO_neg = Relationship(6)
        val AMICIZIA_pos = Relationship(4)
        val AMICIZIA_neg = Relationship(2)
        val NEUTRALE_pos = Relationship(1)
        val NEUTRALE_neg = Relationship(-1)
        val RIVALE_pos = Relationship(-2)
        val RIVALE_neg = Relationship(-4)
        val NEMICO_pos = Relationship(-6)
        val NEMICO_neg = Relationship(-8)
        val CONFLITTO_pos = Relationship(-9)
        val CONFLITTO_neg = Relationship(-11)

        private val orderedRelationships = listOf(
            ALLEATO_pos, ALLEATO_neg,
            AMICIZIA_pos, AMICIZIA_neg,
            NEUTRALE_pos, NEUTRALE_neg,
            RIVALE_pos, RIVALE_neg,
            NEMICO_pos, NEMICO_neg,
            CONFLITTO_pos, CONFLITTO_neg
        )
        //Se R2 si trova sotto a R1 restituisce un valore negativo
        fun computeDiff(r1: Relationship, r2: Relationship): Int {
            val indexR1 = orderedRelationships.indexOf(r1)
            val indexR2 = orderedRelationships.indexOf(r2)
            return indexR1 - indexR2
        }
    }

    override fun increase(): Relationship{
        return when(this){
            ALLEATO_pos -> ALLEATO_pos
            ALLEATO_neg -> ALLEATO_pos
            AMICIZIA_pos -> ALLEATO_neg
            AMICIZIA_neg -> AMICIZIA_pos
            NEUTRALE_pos -> AMICIZIA_neg
            NEUTRALE_neg -> NEUTRALE_pos
            RIVALE_pos -> NEUTRALE_neg
            RIVALE_neg -> RIVALE_pos
            NEMICO_pos -> RIVALE_neg
            NEMICO_neg -> NEMICO_pos
            CONFLITTO_pos -> NEMICO_neg
            CONFLITTO_neg -> CONFLITTO_pos
            else -> TODO()
        }
    }

    override fun decrease(): Relationship{
        return when(this){
            ALLEATO_pos -> ALLEATO_neg
            ALLEATO_neg -> AMICIZIA_pos
            AMICIZIA_pos -> AMICIZIA_neg
            AMICIZIA_neg -> NEUTRALE_pos
            NEUTRALE_pos -> NEUTRALE_neg
            NEUTRALE_neg -> RIVALE_pos
            RIVALE_pos -> RIVALE_neg
            RIVALE_neg -> NEMICO_pos
            NEMICO_pos -> NEMICO_neg
            NEMICO_neg -> CONFLITTO_pos
            CONFLITTO_pos -> CONFLITTO_neg
            CONFLITTO_neg -> CONFLITTO_neg
            else -> TODO()
        }
    }
}
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

    override fun toMap(): Map<String, Any> {
        return when(this){
            ALLEATO_pos -> mapOf("ALLEATO_pos" to "Questi gruppi sono legati da una alleanza forte")
            ALLEATO_neg -> mapOf("ALLEATO_neg" to "Questi gruppi sono legati da una alleanza debole")
            AMICIZIA_pos -> mapOf("AMICIZIA_pos" to "Questi gruppi hanno un forte senso di amicizia ma non sono alleati")
            AMICIZIA_neg -> mapOf("AMICIZIA_neg" to "Questi gruppi hanno un legame di amicizia, anche se non troppo forte, ma non sono alleati")
            NEUTRALE_pos -> mapOf("NEUTRALE_pos" to "Questi gruppi non hanno una relazione ma si vedono di buon grado")
            NEUTRALE_neg -> mapOf("NEUTRALE_neg" to "Questi gruppi non hanno una relazione ma NON si vedono di buon grado")
            RIVALE_pos -> mapOf("RIVALE_pos" to "Questi gruppi non vanno certamente d'accordo e hanno un forte senso di rivalità")
            RIVALE_neg -> mapOf("RIVALE_neg" to "Questi gruppi non vanno propio d'accordo e iniziano a \"farsi dispetti\" a vicenda")
            NEMICO_pos -> mapOf("NEMICO_pos" to "Questi gruppi sono nemici")
            NEMICO_neg -> mapOf("NEMICO_neg" to "Questi gruppi sono nemici e sull'orlo di una guerra")
            CONFLITTO_pos -> mapOf("CONFLITTO_pos" to "Questi gruppi sono in guerra e questa o è iniziata da poco o sta per finire")
            CONFLITTO_neg -> mapOf("CONFLITTO_neg" to "Questi gruppi sono in guerra e questa e si è nel cuore di quest'ultima")
            else -> TODO()
        }
    }
}
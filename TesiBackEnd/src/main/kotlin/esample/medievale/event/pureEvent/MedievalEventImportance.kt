package esample.medievale.event.pureEvent

import conceptualMap2.event.EventImportance
import kotlin.random.Random

class MedievalEventImportance(name: String): EventImportance(name){
    companion object{
        val BANALE = MedievalEventImportance("BANALE")
        val NORMALE = MedievalEventImportance("NORMALE")
        val IMPORTANTE = MedievalEventImportance("IMPORTANTE")
        val CRUCIALE = MedievalEventImportance("CRUCIALE")

        fun random(): MedievalEventImportance{
            return when(Random.nextInt(3)){
                0 -> BANALE
                1 -> NORMALE
                2 -> IMPORTANTE
                else -> throw RuntimeException("Error during generation of random number")
            }
        }
    }

    override fun toMap(): Map<String, Any>{
        val map = mutableMapOf<String, Any>()
        when(name){
            "BANALE" -> map["BANALE"] = mapOf(
                "Funzione" to "Una tipologia di evento che ha un impatto quasi nullo"
            )
            "NORMALE" -> map["NORMALE"] = mapOf(
                "Funzione" to "Una tipologia di evento che ha un impatto medio ma non influenza il pensiero o il resto della giornata"
            )
            "IMPORTANTE" -> map["IMPORTANTE"] = mapOf(
                "Funzione" to "Una tipologia di evento che ha un impatto molto alto e porta molte conseguenze al personaggio"
            )
            "CRUCIALE" -> map["CRUCIALE"] = mapOf(
                "Funzione" to "Una tipologia di evento che ha un impatto altissimo e porta conseguenze critiche al personaggio"
            )
        }
        return map
    }

}
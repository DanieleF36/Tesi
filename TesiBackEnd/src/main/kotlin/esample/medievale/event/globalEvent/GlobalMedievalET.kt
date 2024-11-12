package esample.medievale.event.globalEvent

import conceptualMap2.event.EventType

class GlobalMedievalET(name: String): EventType(name) {
    companion object{
        val INIZIO_CONFLITTO = GlobalMedievalET("INIZIO_CONFLITTO")
        val FINE_CONFLITTO_VITTORIA = GlobalMedievalET("FINE_CONFLITTO_VITTORIA")
        val FINE_CONFLITTO_SCONFITTA = GlobalMedievalET("FINE_CONFLITTO_SCONFITTA")
        val INIZIO_ALLEANZA = GlobalMedievalET("INIZIO_ALLEANZA")
        val FINE_ALLEANZA = GlobalMedievalET("FINE_ALLEANZA")
        val OMICIDIO_IMPORTANTE = GlobalMedievalET("OMICIDIO_IMPORTANTE")
        val RIVOLTA_POPOLARE = GlobalMedievalET("RIVOLTA_POPOLARE")
    }

    override fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        when(name) {
            "INIZIO_CONFLITTO" -> map["INIZIO_CONFLITTO"] = mutableMapOf(
                "Funzione" to "Segnala l'inizio di un conflitto armato tra due o più entità",
                "Esempi" to "Le trombe suonano mentre le armate si schierano ai confini del regno"
            )
            "FINE_CONFLITTO_VITTORIA" -> map["FINE_CONFLITTO_VITTORIA"] = mutableMapOf(
                "Funzione" to "Indica la conclusione di un conflitto con una vittoria",
                "Esempi" to "La città celebra la vittoria dei suoi guerrieri con una grande festa"
            )
            "FINE_CONFLITTO_SCONFITTA" -> map["FINE_CONFLITTO_SCONFITTA"] = mutableMapOf(
                "Funzione" to "Segnala la fine di un conflitto con una sconfitta",
                "Esempi" to "Le strade sono silenziose mentre il nemico marcia attraverso la città"
            )
            "INIZIO_ALLEANZA" -> map["INIZIO_ALLEANZA"] = mutableMapOf(
                "Funzione" to "Annuncia la formazione di una nuova alleanza tra città o signori",
                "Esempi" to "I sigilli delle grandi famiglie vengono apposti sul trattato di alleanza"
            )
            "FINE_ALLEANZA" -> map["FINE_ALLEANZA"] = mutableMapOf(
                "Funzione" to "Marca la rottura di un'alleanza precedentemente stabilita",
                "Esempi" to "I messaggeri annunciano la fine della lunga alleanza tra le due città"
            )
            "OMICIDIO_IMPORTANTE" -> map["OMICIDIO_IMPORTANTE"] = mutableMapOf(
                "Funzione" to "Riporta l'assassinio di una figura chiave",
                "Esempi" to "Il re è stato ucciso in circostanze misteriose"
            )
            "RIVOLTA_POPOLARE" -> map["RIVOLTA_POPOLARE"] = mutableMapOf(
                "Funzione" to "Descrive l'insorgere di una rivolta tra la popolazione",
                "Esempi" to "La piazza centrale è teatro di violenti scontri tra cittadini e guardie"
            )
        }
        return map
    }

    override fun isPositive(): Int {
        return when(name){
            "INIZIO_CONFLITTO" -> -1
            "FINE_CONFLITTO_VITTORIA" -> 1
            "FINE_CONFLITTO_SCONFITTA" -> -1
            "INIZIO_ALLEANZA" -> 1
            "FINE_ALLEANZA" -> -1
            "OMICIDIO_IMPORTANTE" -> -1
            "RIVOLTA_POPOLARE" -> -1
            else -> TODO()
        }
    }

}
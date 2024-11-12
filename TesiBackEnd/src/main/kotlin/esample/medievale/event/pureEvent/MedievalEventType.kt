package esample.medievale.event.pureEvent

import conceptualMap2.event.EventType
import esample.medievale.event.pureEvent.MedievalEventImportance.Companion.BANALE
import esample.medievale.event.pureEvent.MedievalEventImportance.Companion.IMPORTANTE
import esample.medievale.event.pureEvent.MedievalEventImportance.Companion.NORMALE
import kotlin.random.Random

class MedievalEventType(name: String): EventType(name) {
    companion object{
        val AMICIZIA = MedievalEventType("AMICIZIA")
        val MINACCIA = MedievalEventType("MINACCIA")
        val SOSPETTO = MedievalEventType("SOSPETTO")
        val SUPPORTO = MedievalEventType("SUPPORTO")
        val COLLABORAZIONE = MedievalEventType("COLLABORAZIONE")
        val TRADIMENTO = MedievalEventType("TRADIMENTO")
        val SFIDA = MedievalEventType("SFIDA")
        val AIUTO = MedievalEventType("AIUTO")
        val OMICIDIO = MedievalEventType("OMICIDIO")

        fun random(): MedievalEventType{
            return when(Random.nextInt(9)){
                0 -> AMICIZIA
                1 -> MINACCIA
                2 -> SOSPETTO
                3 -> SUPPORTO
                4 -> COLLABORAZIONE
                5 -> TRADIMENTO
                6 -> SFIDA
                7 -> AIUTO
                8 -> OMICIDIO
                else -> throw RuntimeException("Error during generation of random number")
            }
        }
    }

    override fun toMap(): Map<String, Any>{
        val map = mutableMapOf<String, Any>()
        when(name){
            "AMICIZIA" -> map["AMICIZIA"] = mutableMapOf<String, String>("Funzione" to "Rafforzare legami sociali, esprimere supporto e solidarietà", "Esempi" to "Due artigiani che discutono delle loro famiglie e progetti futuri mentre lavorano")
            "MINACCIA" -> map["MINACCIA"] = mutableMapOf<String, String>("Funzione" to "Intimidire, costringere o dissuadere altri attraverso la promessa di conseguenze negative", "Esempi" to "Un cavaliere minaccia un cittadino di ucciderlo se non la smette di urlare")
            "TRADIMENTO" -> map["TRADIMENTO"] = mutableMapOf(
                "Funzione" to "Discutere o pianificare azioni di tradimento contro individui, gruppi o città",
                "Esempi" to "Un consigliere che segretamente instiga alla ribellione contro il proprio sovrano"
            )
            "SFIDA" -> map["SFIDA"] = mutableMapOf(
                "Funzione" to "Sfidare l'autorità o le decisioni di qualcuno in modo aperto o velato",
                "Esempi" to "Un cavaliere che sfida pubblicamente le decisioni di un leader durante un'assemblea"
            )
            "SOSPETTO" -> map["SOSPETTO"] = mutableMapOf(
                "Funzione" to "Esprimere dubbi o preoccupazioni riguardo le intenzioni o le azioni di altri",
                "Esempi" to "Cittadini che discutono sospetti su un nuovo arrivato in città, interrogandosi sulle sue vere intenzioni"
            )
            "SUPPORTO" -> map["SUPPORTO"] = mutableMapOf(
                "Funzione" to "Offrire aiuto, consiglio o conforto a chi è in difficoltà",
                "Esempi" to "Un vecchio consigliere che offre saggezza e guida a un giovane leader durante tempi di crisi"
            )
            "COLLABORAZIONE" -> map["COLLABORAZIONE"] = mutableMapOf(
                "Funzione" to "Organizzare insieme attività o eventi, coordinare sforzi per obiettivi comuni",
                "Esempi" to "Membri di una gilda che pianificano una festa cittadina"
            )
            "AIUTO" -> map["AIUTO"] = mutableMapOf(
                "Funzione" to "Si aiuta una qualsiasi persona in difficoltà",
                "Esempi" to "Un cittadino ti chiede di richiamare il figlio per pranzare"
            )
            "OMICIDIO" -> map["OMICIDIO"] = mutableMapOf(
                "Funzione" to "E' stato ritrovato un cadavere"
            )
        }
        return map
    }

    //-1 negative, 0 neutral and 1 positive
    override fun isPositive(): Int {
        return when(name){
            "AMICIZIA" -> 1
            "MINACCIA" -> -1
            "SOSPETTO" -> -1
            "SUPPORTO" -> 1
            "COLLABORAZIONE" -> 1
            "TRADIMENTO" -> -1
            "SFIDA" -> 0
            "AIUTO" -> 1
            "OMICIDIO" -> 0
            else -> TODO("not implemented yet")
        }
    }
}
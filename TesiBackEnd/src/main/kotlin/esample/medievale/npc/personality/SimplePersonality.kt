package esample.medievale.npc.personality

import conceptualMap2.npc.Personality

class SimplePersonality(
    private val reliability: Int, // Grado di affidabilità
    private val extraversion: Int, // Livello di socievolezza
    private val willpower: Int, //capacità di gestire situazioni stressanti o fallimenti
    private val impulsivity: Int, //determina l'inclinazione a reagire senza riflettere
    private val determination: Int,
    private val charisma: Int,
    private val insight: Int, //Capacità di intuire intenzioni altrui
    private val eloquence: Int,
    private val unpredictability: Int,
    private val communicationStyle: CommunicationStyle
): Personality() {
    override fun toMap(): Map<String, Any>{
        val map = mutableMapOf<String, Any>()
        map["reliability"] = reliability
        map["extraversion"] = extraversion
        map["willpower"] = willpower
        map["impulsivity"] = impulsivity
        map["determination"] = determination
        map["charisma"] = charisma
        map["insight"] = insight
        map["eloquence"] = eloquence
        map["unpredictability"] = unpredictability
        map["communicationStyle"] = communicationStyle.toString()
        map["comments"] = mapOf(
            "valori" to "tutti i valori sono su una scala 1-10",
            "reliability" to "grado di affidabilità di una persona",
            "extraversion" to "Livello di socievolezza",
            "willpower" to "capacità di gestire situazioni stressanti o fallimenti",
            "impulsivity" to "determina l'inclinazione a reagire senza riflettere",
            "insight" to "Capacità di intuire intenzioni altrui",
            "eloquence" to "la sua capacità di discutere in modo fluente, elegante e persuasivo"
        )
        return map
    }

    override fun toString(): String {
        return "reliability=$reliability, extraversion=$extraversion, willpower=$willpower, impulsivity=$impulsivity, determination=$determination, charisma=$charisma, insight=$insight, eloquence=$eloquence, unpredictability=$unpredictability, communicationStyle=$communicationStyle"
    }
}
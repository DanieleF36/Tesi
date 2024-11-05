package esample.calcio.npc.footballer.personality

import conceptualMap2.npc.Personality

class FootballerPersonality(
    private val reliability: Int, // Grado di affidabilità
    private val extraversion: Int, // Livello di socievolezza e assertività
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
        return map
    }
}
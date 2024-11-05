package esample.calcio.npc.footballer

import conceptualMap2.Statistic

class Transfer(
    val from: String,
    val to: String,
    val amount: Int,
    val year: Int
) {
    fun update(stats: Statistic) {
        throw RuntimeException("You cant update a transfer")
    }

    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["from"] = from
        map["to"] = to
        map["amount"] = amount
        map["year"] = year
        return map
    }
}
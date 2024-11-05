package conceptualMap2

import conceptualMap2.event.Event


interface Statistic: Cloneable {
    fun update(stats: Statistic)
    fun toMap(): Map<String, Any>
    public override fun clone(): Statistic
}
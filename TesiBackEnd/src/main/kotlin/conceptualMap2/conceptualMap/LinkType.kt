package conceptualMap2.conceptualMap

abstract class LinkType(val value: Int){
    abstract fun increase(): LinkType
    abstract fun decrease(): LinkType
    abstract fun toMap(): Map<String, Any>
}
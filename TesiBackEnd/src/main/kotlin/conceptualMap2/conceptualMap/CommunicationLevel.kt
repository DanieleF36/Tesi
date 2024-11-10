package conceptualMap2.conceptualMap

abstract class CommunicationLevel(val name: String){
    abstract fun increase(): CommunicationLevel
    abstract fun decrease(): CommunicationLevel
}
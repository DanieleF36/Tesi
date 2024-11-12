package esample.medievale.npc.personality

class CommunicationStyle(val name: String) {
    companion object{
        val DIPLOMATICO = CommunicationStyle("Diplomatico")
        val PASSIVO = CommunicationStyle("Passivo")
        val AGGRESSIVO = CommunicationStyle("Aggressivo")
        val NEUTRALE = CommunicationStyle("Neutrale")
        val SARCASTICO = CommunicationStyle("Sarcastico")
        val VOLGARE = CommunicationStyle("Volgare")
    }
    override fun toString(): String {
        return name
    }
}
package conceptualMap2.npc.sample

import conceptualMap2.npc.personality.CommunicationStyle

class SampleCommunicationStyle(name: String) : CommunicationStyle(name) {
    companion object{
        val ASSERTIVE = SampleCommunicationStyle("assertive")
        val PASSIVE = SampleCommunicationStyle("passive")
        val AGGRESSIVE = SampleCommunicationStyle("aggressive")
    }

}
package esample.medievale.event

import conceptualMap2.event.ConfidentialityLevel

class ConfidentialityLevelImpl(value: Float): ConfidentialityLevel(value) {
    companion object{
        val SEGRETO = ConfidentialityLevelImpl(.05f)
        val NEUTRALE = ConfidentialityLevelImpl(.5F)
        val PUBBLICO = ConfidentialityLevelImpl(1F)
    }
    override fun toMap(): Map<String, Any> {
        return when(this){
            SEGRETO ->  mapOf(
                "Livello di confidenzialità" to "segreto",
                "Descrizione" to "Questo evento dovrebbe essere tenuto segreto e se ne può parlare solo con chi ne è a conoscenza"
            )
            NEUTRALE ->  mapOf(
                "Livello di confidenzialità" to "neutrale",
                "Descrizione" to "Questo evento può essere anche condiviso con altri, senza nessun tipo di problema"
            )
            PUBBLICO ->  mapOf(
                "Livello di confidenzialità" to "pubblico",
                "Descrizione" to "Questo evento deve essere condiviso con tutti"
            )
            else -> TODO("Not implemented yet")
        }
    }

}
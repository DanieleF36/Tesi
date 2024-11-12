package esample.medievale.realEsample.context.local

import esample.medievale.npc.knowledge.localContext.LocalContext
import esample.medievale.npc.knowledge.localContext.params.*

val aronathLocalContext = LocalContext(
    cityOrganization = CityOrganization(
        areas = "Mercanti, artigiani, residenziale",
        bigness = "Grande",
        plazas = "Piazza della Vittoria, Piazza del Mercato",
        markets = "Mercato Centrale, Mercato settimanale del Nord",
        cityBoundaries = "Mura fortificate con sei porte principali"
    ),
    politicalAuthorities = PoliticalAuthorities(
        royalFamily = "Casa di Vanthor: Re Eldred il Giusto, regnante dal trono di Aronath, è noto per la sua saggezza e equità. Governando con un occhio verso la prosperità del suo popolo, è supportato dalla Regina Alia e dai loro due giovani principi, Sirion e Mirena.",
        kingsHistory = listOf(
            "Re Alaric il Grande, amato per aver espanso i confini del regno e migliorato le infrastrutture.",
            "Re Borin il Saggio, rispettato per le sue politiche di pace e il suo mecenatismo delle arti e delle scienze."
        ),
        kingsAdvisers = "Consiglio del Re, composto da Il Maestro di Guerra Sir Beric, Il Cancelliere Lord Miron, e Il Magister delle Finanze Lady Thalia.",
        lordsOfNeighborhoods = listOf(
            "Lord Harwick, governatore del Distretto Commerciale, noto per la sua abilità negli affari e nella negoziazione.",
            "Lord Gavriel, governatore del Distretto Artigiano, rinomato per la sua gestione delle gilde e delle corporazioni artigiane.",
            "Lady Elaina, governatrice del Quartiere Residenziale, ammirata per il suo impegno nel migliorare le condizioni di vita dei suoi cittadini."
        )
    ),
    prominentPeople = listOf(
        ProminentPerson(
            name = "Sir Eldric il Valoroso",
            heroicGesture = "Difese la città contro l'invasione dei barbari del nord."
        ),
        ProminentPerson(
            name = "Dama Ysabella la Guaritrice",
            heroicGesture = "Famosa per aver curato una terribile epidemia che minacciava di decimare la città."
        ),
        ProminentPerson(
            name = "Maestro Lucan il Sapiente",
            heroicGesture = "Rispettato per la sua vasta conoscenza di storia e magia, servendo come consigliere del re in tempi di crisi."
        ),
        ProminentPerson(
            name = "Ser Jorah l'Intrepido",
            heroicGesture = "Notabile per le sue spedizioni oltre i confini del regno, portando a casa ricchezze e alleanze."
        )
    )
)

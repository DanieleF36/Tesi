package esample.medievale.realEsample.context.local

import esample.medievale.npc.knowledge.localContext.LocalContext
import esample.medievale.npc.knowledge.localContext.params.*

val braegorLocalContext = LocalContext(
    cityOrganization = CityOrganization(
        areas = "Quartiere dei Mercanti, Zona Residenziale, Porto",
        bigness = "Grande",
        plazas = "Piazza dei Navigatori, Piazza del Commercio",
        markets = "Mercato del Pesce, Mercato delle Spezie",
        cityBoundaries = "Porto sicuro circondato da mura"
    ),
    politicalAuthorities = PoliticalAuthorities(
        royalFamily = "Principe Mercante Arlon: Un governatore abile nel commercio e noto per la sua acuta intelligenza finanziaria. La sua famiglia, inclusi la Principessa Serena e il loro figlio, il giovane Lord Marlon, regna con una mano ferma ma giusta.",
        kingsHistory = listOf(
            "Principe Orin il Ricco, il cui regno vide un'enorme crescita economica",
            "Principe Myron il Navigatore, esploratore che stabilì rotte commerciali vitali"
        ),
        kingsAdvisers = "Consorte di mercanti anziani e saggi navigatori che consigliano il principe nelle sue decisioni",
        lordsOfNeighborhoods = listOf(
            "Signore del Porto, che controlla tutte le operazioni marittime e mantiene la sicurezza navale",
            "Maestro dei Mercanti, responsabile del benessere economico della zona commerciale"
        )
    ),
    prominentPeople = listOf(
        ProminentPerson(
            name = "Maestro Toren",
            heroicGesture = "Invenzioni che hanno migliorato significativamente le difese della città"
        ),
        ProminentPerson(
            name = "Capitano Vaelor, il Navigatore",
            heroicGesture = "Scoprì nuove terre oltre il mare, ampliando il commercio e le influenze di Braegor"
        ),
        ProminentPerson(
            name = "Signora Alise, La Benefattrice",
            heroicGesture = "Fondò molte scuole e ospizi per i meno fortunati, migliorando notevolmente la qualità della vita in città"
        )
    )
)

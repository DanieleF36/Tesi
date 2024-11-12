package esample.medievale.realEsample.context.local

import esample.medievale.npc.knowledge.localContext.LocalContext
import esample.medievale.npc.knowledge.localContext.params.*

val gelvanLocalContext = LocalContext(
    cityOrganization = CityOrganization(
        areas = "Centro Amministrativo, Quartiere dei Mercanti, Distretto Artigianale",
        bigness = "Media",
        plazas = "Piazza del Consiglio, Piazza del Mercato",
        markets = "Mercato di Gelvan, Fiera Artigiana Annuale",
        cityBoundaries = "Mura di pietra con due principali porte fortificate"
    ),
    politicalAuthorities = PoliticalAuthorities(
        royalFamily = "Monarca Elettivo: Attualmente governato da Re Garen il Saggio, scelto ogni dieci anni tra i nobili della città.",
        kingsHistory = listOf(
            "Re Alden il Pacifico, famoso per aver negoziato trattati di pace duraturi",
            "Re Eamon il Costruttore, noto per aver espanso e fortificato le mura della città"
        ),
        kingsAdvisers = "Consorte di Nobili Eletti che assistono il re nelle decisioni politiche e economiche",
        lordsOfNeighborhoods = listOf(
            "Governatore del Centro Amministrativo, custode delle leggi e delle politiche",
            "Signore del Quartiere dei Mercanti, regolatore del commercio e delle transazioni",
            "Maestro del Distretto Artigianale, promotore dell'artigianato e tutore delle tecniche tradizionali"
        )
    ),
    prominentPeople = listOf(
        ProminentPerson(
            name = "Garen il Saggio",
            heroicGesture = "Notato per la sua decisione di rinunciare al trono per il bene della pace"
        ),
        ProminentPerson(
            name = "Ser Derin, il Protettore",
            heroicGesture = "Salvò la città da una banda di ladri che minacciavano la sicurezza dei cittadini"
        )
    )
)

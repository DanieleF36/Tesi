package esample.medievale.realEsample.context.local

import esample.medievale.npc.knowledge.localContext.LocalContext
import esample.medievale.npc.knowledge.localContext.params.CityOrganization
import esample.medievale.npc.knowledge.localContext.params.PoliticalAuthorities
import esample.medievale.npc.knowledge.localContext.params.ProminentPerson

val cyrendilLocalContext = LocalContext(
    cityOrganization = CityOrganization(
        areas = "Collina Centrale, Distretto Commerciale, Quartiere Artigiano",
        bigness = "Media",
        plazas = "Piazza del Sole, Piazza dei Sogni",
        markets = "Mercato del Giovedì, Mercato delle Erbe",
        cityBoundaries = "Mura antiche, con tre principali porte d'accesso"
    ),
    politicalAuthorities = PoliticalAuthorities(
        royalFamily = "Consiglio dei Magistrati: Governati da un consiglio di saggi anziani, il presidente è il Magistrato Luken, noto per la sua giustizia equa e le riforme sociali.",
        kingsHistory = listOf(
            "Magistrato Corvin, celebre per la sua diplomazia e apertura commerciale.",
            "Magistrato Eliana, ammirata per aver migliorato l'infrastruttura cittadina."
        ),
        kingsAdvisers = "Sottoconsiglieri inclusi Il Custode delle Leggi, Il Tesoriere e Il Capo degli Artigiani.",
        lordsOfNeighborhoods = listOf(
            "Signore della Collina, governatore della zona alta e custode delle tradizioni",
            "Guardiano del Mercato, responsabile della sicurezza e regolamentazione dei commerci nel distretto commerciale",
            "Maestro degli Artigiani, supervisore del Quartiere Artigiano e regolatore delle gilde artigiane"
        )
    ),
    prominentPeople = listOf(
        ProminentPerson(
            name = "Dame Alia di Cyrendil",
            heroicGesture = "Negoziazione cruciale di trattati di pace con le città rivali"
        ),
        ProminentPerson(
            name = "Sir Mathos il Vigile",
            heroicGesture = "Salvò la città da un grande incendio organizzando rapidamente la brigata dei pompieri"
        ),
        ProminentPerson(
            name = "Maestra Elora, L'Innovatrice",
            heroicGesture = "Inventò un nuovo sistema di irrigazione che ha raddoppiato la produzione agricola della città"
        )
    )
)

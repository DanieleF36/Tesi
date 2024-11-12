package esample.medievale.realEsample.context.local

import esample.medievale.npc.knowledge.localContext.LocalContext
import esample.medievale.npc.knowledge.localContext.params.*

val ellesmereLocalContext = LocalContext(
    cityOrganization = CityOrganization(
        areas = "Giardini Reali, Quartiere dei Mercanti, Settore delle Arti",
        bigness = "Media",
        plazas = "Piazza della Regina, Piazza del Tramonto",
        markets = "Mercato delle Delizie, Mercato dell'Arte",
        cityBoundaries = "Mura eleganti decorate con arte e simboli culturali"
    ),
    politicalAuthorities = PoliticalAuthorities(
        royalFamily = "Regina Isolde la Benevola: Regna con una miscela di rigore e compassione, assistita dal Principe Consorte Alaric e dalla Principessa Lyra.",
        kingsHistory = listOf(
            "Re Cedric il Costruttore, noto per aver espanso e abbellito la città",
            "Regina Elara la Saggia, amata per le sue politiche di sostegno alle arti e alla cultura"
        ),
        kingsAdvisers = "Consiglio Reale che include il Vizir, il Maestro di Corte e il Capo delle Guardie",
        lordsOfNeighborhoods = listOf(
            "Lord Darian, custode dei Giardini Reali, responsabile della loro manutenzione e dell'organizzazione di eventi culturali",
            "Signore dei Mercanti, regolatore del commercio e delle attività economiche nel Quartiere dei Mercanti",
            "Maestro delle Arti, promotore delle attività artistiche e culturali nel Settore delle Arti"
        )
    ),
    prominentPeople = listOf(
        ProminentPerson(
            name = "Lady Mirabel",
            heroicGesture = "Organizzò aiuti significativi durante una grande carestia"
        ),
        ProminentPerson(
            name = "Sir Gareth il Protettore",
            heroicGesture = "Difese la città durante l'assedio senza perdere una sola vita"
        ),
        ProminentPerson(
            name = "Maestra Fiona, La Voce d'Oro",
            heroicGesture = "Con le sue performance ha portato fama e visitatori da tutto il regno"
        )
    )
)

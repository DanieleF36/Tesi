package esample.medievale.realEsample.context.local

import esample.medievale.npc.knowledge.localContext.LocalContext
import esample.medievale.npc.knowledge.localContext.params.*

val havenshireLocalContext = LocalContext(
    cityOrganization = CityOrganization(
        areas = "Porto, Quartiere Commerciale, Zona Residenziale",
        bigness = "Grande",
        plazas = "Piazza del Porto, Piazza Centrale",
        markets = "Mercato del Mare, Mercato Generale",
        cityBoundaries = "Barriera naturale del mare da un lato e mura cittadine dall'altro"
    ),
    politicalAuthorities = PoliticalAuthorities(
        royalFamily = "Doge Marten il Navigatore: Una figura carismatica che guida la città-repubblica con il supporto di una serie di consigli amministrativi.",
        kingsHistory = listOf(
            "Doge Silvan il Mercante, noto per aver espanso le rotte commerciali marittime",
            "Doge Elara la Visionaria, celebre per le sue riforme urbane e sociale"
        ),
        kingsAdvisers = "Consiglio di Doge composto da ammiragli, mercanti capi e anziani saggi",
        lordsOfNeighborhoods = listOf(
            "Capitano del Porto, responsabile della sicurezza e della gestione marittima",
            "Supervisore del Quartiere Commerciale, incaricato della regolamentazione economica e del benessere dei mercanti",
            "Guardiano della Zona Residenziale, protettore delle famiglie e regolatore delle questioni domestiche"
        )
    ),
    prominentPeople = listOf(
        ProminentPerson(
            name = "Amara dei Venti",
            heroicGesture = "Capitana di nave che scoprì nuove rotte commerciali, portando prosperità a Havenshire"
        ),
        ProminentPerson(
            name = "Maestro Jorin, l'Architetto",
            heroicGesture = "Disegnò e sovrintese la costruzione di nuove infrastrutture cruciali per la città"
        )
    )
)

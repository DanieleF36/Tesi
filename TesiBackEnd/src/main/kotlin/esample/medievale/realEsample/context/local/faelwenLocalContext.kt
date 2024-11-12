package esample.medievale.realEsample.context.local

import esample.medievale.npc.knowledge.localContext.LocalContext
import esample.medievale.npc.knowledge.localContext.params.*

val faelwenLocalContext = LocalContext(
    cityOrganization = CityOrganization(
        areas = "Foresta Urbana, Distretto del Lago, Via degli Artigiani",
        bigness = "Piccola",
        plazas = "Piazza dell'Albero Sacro, Piazza dell'Acqua",
        markets = "Mercato Verde, Mercato dell'Acquaforte",
        cityBoundaries = "Confine naturale formato da foreste dense e il lago centrale"
    ),
    politicalAuthorities = PoliticalAuthorities(
        royalFamily = "Sindaco Elwin il Verde: Leader eletto che ha un profondo rispetto per la natura e promuove politiche sostenibili.",
        kingsHistory = listOf(
            "Sindaco Arwen la Guardiana, pioniera nella conservazione ambientale",
            "Sindaco Beren l'Innovatore, noto per aver introdotto tecnologie verdi in città"
        ),
        kingsAdvisers = "Consiglieri composti da esperti in botanica, sostenibilità e commercio",
        lordsOfNeighborhoods = listOf(
            "Protettore della Foresta Urbana, incaricato della tutela e dello sviluppo sostenibile dell'area",
            "Guardiano del Distretto del Lago, responsabile della gestione delle risorse idriche e delle attività ricreative",
            "Capo degli Artigiani, supervisore delle gilde e promozione dell'artigianato locale"
        )
    ),
    prominentPeople = listOf(
        ProminentPerson(
            name = "Elden il Giusto",
            heroicGesture = "Giudicò equamente in numerosi conflitti, mantenendo la pace in città"
        ),
        ProminentPerson(
            name = "Dama Serin della Foglia",
            heroicGesture = "Riscoprì antiche tecniche di coltivazione che rivitalizzarono l'agricoltura locale"
        )
    )
)

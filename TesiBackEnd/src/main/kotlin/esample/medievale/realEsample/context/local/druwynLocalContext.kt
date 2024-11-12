package esample.medievale.realEsample.context.local

import esample.medievale.npc.knowledge.localContext.LocalContext
import esample.medievale.npc.knowledge.localContext.params.*

val druwynLocalContext = LocalContext(
    cityOrganization = CityOrganization(
        areas = "Distretto Commerciale, Quartiere Residenziale, Settore Industriale",
        bigness = "Grande",
        plazas = "Piazza del Commercio, Piazza della Forgia",
        markets = "Mercato Settimanale, Mercato Industriale",
        cityBoundaries = "Fortificazioni robuste che circondano la città con quattro principali porte d'accesso"
    ),
    politicalAuthorities = PoliticalAuthorities(
        royalFamily = "Consiglio di Druwyn: Un gruppo di anziani che governa con un approccio democratico, presieduto dal sindaco Tyrion, noto per le sue politiche progressiste.",
        kingsHistory = listOf(
            "Sindaco Alaric il Riformatore, amato per le sue politiche di modernizzazione urbana",
            "Sindaco Myra la Giusta, rispettata per il suo impegno verso l'equità sociale"
        ),
        kingsAdvisers = "Consorte di Consiglieri che includono il Capo degli Industriali, il Regolatore del Commercio e il Protettore dei Cittadini",
        lordsOfNeighborhoods = listOf(
            "Signore del Distretto Commerciale, sovrintendente all'economia e al commercio della zona",
            "Signore del Quartiere Residenziale, responsabile del benessere e della sicurezza dei residenti",
            "Maestro del Settore Industriale, gestore delle fabbriche e delle produzioni locali"
        )
    ),
    prominentPeople = listOf(
        ProminentPerson(
            name = "Sir Haleth",
            heroicGesture = "Eroe locale noto per aver salvato numerosi cittadini durante un grande incendio"
        ),
        ProminentPerson(
            name = "Lady Elora, la Filantropa",
            heroicGesture = "Fondatrice di numerose iniziative benefiche che supportano l'educazione e la sanità"
        )
    )
)

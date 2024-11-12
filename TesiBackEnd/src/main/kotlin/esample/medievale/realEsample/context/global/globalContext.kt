package esample.medievale.realEsample.context.global

import esample.medievale.link.Relationship
import esample.medievale.npc.knowledge.globalContetx.GlobalContext
import esample.medievale.npc.knowledge.globalContetx.params.*

// Local Geography instance
val geography = LocalGeography(
    nearbyCities = "Aronath, Braegor, Cyrendil, Druwyn, Ellesmere, Faelwen, Gelvan, Havenshire",
    mainRoutes = "Via Mercatorum tra Aronath e Braegor, Strada del Re tra Cyrendil e Druwyn, Sentiero dei Pellegrini tra Ellesmere e Faelwen"
)

// Local History instance including detailed events
val history = LocalHistory(
    events = listOf(
        HistoryEvent(description = "Battaglia di Aronath: una feroce lotta per il controllo delle miniere di ferro.", cityName = "Aronath"),
        HistoryEvent(description = "Fondazione di Braegor: fondata come avamposto militare, ora è un fiorente centro commerciale.", cityName = "Braegor"),
        HistoryEvent(description = "Patto del Sangue di Cyrendil: un antico accordo tra i clan della città che ancora determina la politica locale.", cityName = "Cyrendil"),
        HistoryEvent(description = "Assedio di Druwyn: resistette un assedio di sei mesi senza cadere.", cityName = "Druwyn"),
        HistoryEvent(description = "Il Miracolo di Ellesmere: la statua della dea madre lacrimò sangue, prevenendo una pestilenza.", cityName = "Ellesmere"),
        HistoryEvent(description = "La Notte delle Stelle Cadenti a Faelwen: una pioggia di stelle cadenti fu interpretata come un segno divino.", cityName = "Faelwen"),
        HistoryEvent(description = "La Rivolta di Gelvan: i cittadini rovesciarono il loro despota corrotto.", cityName = "Gelvan"),
        HistoryEvent(description = "La Fiera di Havenshire: un'antica fiera che ancora si tiene ogni cinque anni, attirando commercianti da tutto il regno.", cityName = "Havenshire")
    ),
    localMyths = "Leggende raccontano del Drago di Cyrendil, che dorme nelle caverne sotto la città, del Santo di Ellesmere che cammina invisibile tra il suo popolo, e delle creature delle foreste vicino a Faelwen, guardiani antichi della natura."
)

// Religion instance with detailed organization and description
val religion = Religion(
    name = "Culto del Sole Antico",
    organization = "Tempio centrale a Ellesmere con santuari minori in tutte le città. La religione è guidata dall'Alto Sacerdote di Ellesmere, assistito da un concilio di saggi sacerdoti provenienti da ogni città.",
    description = "Il Culto del Sole Antico adora il sole come fonte di vita, giustizia e rinnovamento. I suoi insegnamenti enfatizzano la purezza, la lealtà e il sacrificio personale per il bene comune. La festa del Solstizio d'Estate è il momento più sacro dell'anno, celebrato con grandi fuochi e danze che durano tutta la notte."
)

// City Relationships with detailed descriptions
val relationships = listOf(
    CityRelationship(cityName = "Aronath e Cyrendil", relationship = Relationship.ALLEATO_pos, descriptions = "Aronath e Cyrendil sono alleati stretti, con un patto che rafforza la loro posizione economica e militare nella regione."),
    CityRelationship(cityName = "Cyrendil e Gelvan", relationship = Relationship.NEUTRALE_pos, descriptions = "Le relazioni tra Cyrendil e Gelvan sono cordiali ma distanti, mantenendo una neutralità ben equilibrata senza alleanze formali."),
    CityRelationship(cityName = "Aronath e Braegor", relationship = Relationship.CONFLITTO_neg, descriptions = "Aronath e Braegor sono coinvolti in un conflitto aperto, con frequenti scaramucce al confine e dispute per le risorse."),
    CityRelationship(cityName = "Aronath e Faelwen", relationship = Relationship.NEUTRALE_pos, descriptions = "Nonostante la mancanza di alleanze formali, Aronath e Faelwen non hanno conflitti attivi e occasionalmente commerciano beni."),
    CityRelationship(cityName = "Aronath e Ellesmere", relationship = Relationship.NEMICO_neg, descriptions = "Aronath e Ellesmere sono nemici giurati, con una lunga storia di ostilità e conflitti violenti."),
    CityRelationship(cityName = "Cyrendil e Braegor", relationship = Relationship.RIVALE_neg, descriptions = "Cyrendil e Braegor competono per l'influenza nella regione, spesso in disaccordo su questioni politiche e territoriali."),
    CityRelationship(cityName = "Braegor e Ellesmere", relationship = Relationship.NEMICO_neg, descriptions = "Braegor ed Ellesmere condividono una rivalità acerrima, con una storia di tradimenti e battaglie."),
    CityRelationship(cityName = "Braegor e Druwyn", relationship = Relationship.ALLEATO_pos, descriptions = "Braegor e Druwyn hanno formato un'alleanza strategica che beneficia entrambe le città a livello di difesa e sviluppo economico."),
    CityRelationship(cityName = "Druwyn e Havenshire", relationship = Relationship.NEUTRALE_neg, descriptions = "Druwyn e Havenshire mantengono una neutralità cauta, senza intromettersi nelle rispettive politiche interne."),
    CityRelationship(cityName = "Faelwen e Druwyn", relationship = Relationship.NEUTRALE_pos, descriptions = "Faelwen e Druwyn non hanno legami forti ma condividono interessi commerciali occasionali, mantenendo una neutralità pacifica."),
    CityRelationship(cityName = "Ellesmere e Faelwen", relationship = Relationship.ALLEATO_neg, descriptions = "Ellesmere e Faelwen sono alleati nonostante alcuni disaccordi passati, uniti da obiettivi comuni contro nemici condivisi.")
)

// GlobalContext creation
val globalContext = GlobalContext(
    localGeography = geography,
    localHistory = history,
    religion = religion,
    citiesRelationship = relationships
)

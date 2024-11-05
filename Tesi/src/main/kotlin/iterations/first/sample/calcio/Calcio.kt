package iterations.first.sample.calcio

import iterations.first.Event
import iterations.first.NPC
import iterations.first.conceptualMap.CommonTaught
import iterations.first.conceptualMap.ConceptualMapImpl
import iterations.first.conceptualMap.link.FloatWeightCT
import iterations.first.event.SimpleEvent
import iterations.first.event.SimpleEventImportance
import iterations.first.event.SimpleEventType
import java.io.File

fun readFromFile(path: String): String{
    val fileContent: String = File(path).readText()
    return fileContent
}

fun interactionWithUser(npcs: List<NPC>){
    println("Ecco la lista di tutti gli npc con cui puoi interagire:")
    for (npc in npcs){
        print(npc.name+", ")
    }
    var name: String? = null
    while (name == null) {
        print("\nDigita nome e cognome dell'npc con il quale vuoi parlare: ")
        name = readlnOrNull()
    }
    var chosen: NPC? = null
    for (npc in npcs){
        if(npc.name == name){
            chosen = npc
            break
        }
    }
    if(chosen == null){
        println("Hai scelto la persona sbagliata")
        return
    }
    //TODO interazione tra NPC e utente

}

fun generateNPCs(): List<NPC> {
    /** Definition of major groups and their connections **/
    // Dirigenti
    val dirigenti = ConceptualMapImpl(
        CommonTaught(8, 8, 6, 4, 7, 5, 0),
        readFromFile("./backstory/dirigenti.txt"),
        "Dirigenti"
    )

    // Staff tecnico
    val staffTecnico = ConceptualMapImpl(
        CommonTaught(10, 10, 8, 2, 9, 8, 0),
        readFromFile("./backstory/staffTecnico.txt"),
        "Staff Tecnico"
    )
    // Link tra staff tecnico e dirigenti
    dirigenti.addLink(staffTecnico, FloatWeightCT(2f, 2f, 0f, 1f, 1f, 1f, 2f))

    // Giocatori
    val giocatori = ConceptualMapImpl(
        CommonTaught(8, 6, 5, 4, 5, 2, 3),
        readFromFile("./backstory/giocatori.txt"),
        "Giocatori"
    )
    // Link tra giocatori e dirigenti
    dirigenti.addLink(giocatori, FloatWeightCT(2f, 2f, 0f, 1f, 1f, 0f, 3f))
    // Link tra giocatori e staff tecnico
    staffTecnico.addLink(giocatori, FloatWeightCT(2f, 3f, 2f, 2f, 1f, 1f, 2f))

    // Staff di supporto
    val staffSupporto = ConceptualMapImpl(
        CommonTaught(8, 6, 2, 1, 2, 2, 1),
        readFromFile("./backstory/staffSupporto.txt"),
        "Staff di Supporto"
    )
    // Link tra staff di supporto e dirigenti
    dirigenti.addLink(staffSupporto, FloatWeightCT(2f, 2f, 0f, 1f, 0f, 0f, 1f))
    // Link tra staff di supporto e giocatori
    giocatori.addLink(staffSupporto, FloatWeightCT(2f, 1f, 1f, 0f, 2f, 0f, 0f))
    // Link tra staff di supporto e staff tecnico
    staffTecnico.addLink(staffSupporto, FloatWeightCT(1f, 1f, 1f, 1f, 1f, 0f, 1f))


    val npcs: MutableList<NPC> = mutableListOf()
    /** NPCs generation for Dirigenti **/

    /** NPCs generation for Staff tecnico **/

    return npcs
}

fun generateEvents(): List<Event>{
    val events = mutableListOf<Event>()

    // Discussione su obiettivi stagionali (Dirigenti)
    val discussioneObiettivi = SimpleEvent(
        type = SimpleEventType.OBIETTIVO,
        importance = SimpleEventImportance.IMPORTANTE,
        statistic = CommonTaught(5, 7, 0, 2, 6, 0, 0),
        propagationRange = 3,
        description = "Discussione sugli obiettivi stagionali con i Dirigenti."
    )
    events.add(discussioneObiettivi)

    // Richiesta di più tempo di gioco (Giocatori)
    val richiestaTempoGioco = SimpleEvent(
        type = SimpleEventType.RICHIESTA,
        importance = SimpleEventImportance.NORMALE,
        statistic = CommonTaught(4, 3, 6, 1, 2, 5, 0),
        propagationRange = 2,
        description = "Un giocatore chiede di avere più tempo di gioco."
    )
    events.add(richiestaTempoGioco)

    // Valutazione delle prestazioni (Dirigenti)
    val valutazionePrestazioni = SimpleEvent(
        type = SimpleEventType.RISPETTO,
        importance = SimpleEventImportance.IMPORTANTE,
        statistic = CommonTaught(6, 8, 0, 3, 7, 0, 0),
        propagationRange = 4,
        description = "I dirigenti valutano le prestazioni dell'allenatore."
    )
    events.add(valutazionePrestazioni)

    // Conflitto su una sostituzione (Giocatori)
    val conflittoSostituzione = SimpleEvent(
        type = SimpleEventType.CONFLITTO,
        importance = SimpleEventImportance.CRUCIALE,
        statistic = CommonTaught(0, 3, 0, 8, 0, 0, 7),
        propagationRange = 2,
        description = "Un giocatore è infuriato per una sostituzione fatta durante la partita."
    )
    events.add(conflittoSostituzione)

    // Richiesta di attrezzature (Staff di supporto)
    val richiestaAttrezzature = SimpleEvent(
        type = SimpleEventType.RICHIESTA,
        importance = SimpleEventImportance.BANALE,
        statistic = CommonTaught(2, 1, 3, 0, 0, 3, 0),
        propagationRange = 1,
        description = "Lo staff di supporto richiede nuove attrezzature per migliorare gli allenamenti."
    )
    events.add(richiestaAttrezzature)

    // Lode per buona tattica (Giocatori)
    val lodeTattica = SimpleEvent(
        type = SimpleEventType.LODE,
        importance = SimpleEventImportance.NORMALE,
        statistic = CommonTaught(7, 6, 8, 0, 7, 8, 0),
        propagationRange = 2,
        description = "Un giocatore loda l'allenatore per una tattica vincente."
    )
    events.add(lodeTattica)

    // Conflitto su strategia di gioco (Staff Tecnico)
    val conflittoStrategia = SimpleEvent(
        type = SimpleEventType.CONFLITTO,
        importance = SimpleEventImportance.IMPORTANTE,
        statistic = CommonTaught(0, 4, 1, 6, 0, 0, 4),
        propagationRange = 3,
        description = "Disaccordo con il secondo allenatore riguardo la strategia di gioco."
    )
    events.add(conflittoStrategia)

    // Supporto da parte di un giocatore influente
    val supportoGiocatoreInfluente = SimpleEvent(
        type = SimpleEventType.SUPPORTO,
        importance = SimpleEventImportance.IMPORTANTE,
        statistic = CommonTaught(6, 8, 7, 0, 7, 6, 0),
        propagationRange = 3,
        description = "Un giocatore influente esprime supporto per l'allenatore durante una discussione interna."
    )
    events.add(supportoGiocatoreInfluente)

    // Sospetto da parte del presidente (Dirigenti)
    val sospettoPresidente = SimpleEvent(
        type = SimpleEventType.SOSPETTO,
        importance = SimpleEventImportance.CRUCIALE,
        statistic = CommonTaught(0, 3, 0, 9, 0, 0, 6),
        propagationRange = 4,
        description = "Il presidente sospetta che l'allenatore non stia prendendo le giuste decisioni."
    )
    events.add(sospettoPresidente)

    // Conflitto per un infortunio non comunicato (Staff Tecnico - Giocatori)
    val conflittoInfortunio = SimpleEvent(
        type = SimpleEventType.CONFLITTO,
        importance = SimpleEventImportance.CRUCIALE,
        statistic = CommonTaught(0, 2, 0, 8, 0, 0, 7),
        propagationRange = 3,
        description = "Conflitto tra lo staff tecnico e i giocatori per un infortunio non comunicato."
    )
    events.add(conflittoInfortunio)

    events.add(
        SimpleEvent(
            type = SimpleEventType.SOSPETTO,
            importance = SimpleEventImportance.IMPORTANTE,
            statistic = CommonTaught(
                _respect = -3,
                _trust = -4,
                _affection = -2,
                _suspicion = 5,
                _admiration = -1,
                _friendship = -2,
                _anger = 4
            ),
            propagationRange = 2,
            description = "L'allenatore esclude un giocatore chiave dalla formazione, causando sospetto."
        )
    )

    events.add(
        SimpleEvent(
            type = SimpleEventType.AMMIRAZIONE,
            importance = SimpleEventImportance.CRUCIALE,
            statistic = CommonTaught(
                _respect = 4,
                _trust = 5,
                _affection = 3,
                _suspicion = -6,
                _admiration = 7,
                _friendship = 3,
                _anger = -2
            ),
            propagationRange = 3,
            description = "L'allenatore guida la squadra verso una vittoria importante e riceve ammirazione."
        )
    )

    events.add(
        SimpleEvent(
            type = SimpleEventType.FIDUCIA,
            importance = SimpleEventImportance.NORMALE,
            statistic = CommonTaught(
                _respect = 3,
                _trust = 4,
                _affection = 2,
                _suspicion = -1,
                _admiration = 3,
                _friendship = 2,
                _anger = -1
            ),
            propagationRange = 1,
            description = "Il giocatore dimostra fiducia nell'allenatore dopo essere stato incoraggiato."
        )
    )

    events.add(
        SimpleEvent(
            type = SimpleEventType.FIDUCIA,
            importance = SimpleEventImportance.IMPORTANTE,
            statistic = CommonTaught(
                _respect = -3,
                _trust = -4,
                _affection = -2,
                _suspicion = 5,
                _admiration = -2,
                _friendship = -3,
                _anger = 4
            ),
            propagationRange = 2,
            description = "Il giocatore perde fiducia nell'allenatore dopo un evento critico non affrontato."
        )
    )

    events.add(
        SimpleEvent(
            type = SimpleEventType.AMICIZIA,
            importance = SimpleEventImportance.BANALE,
            statistic = CommonTaught(
                _respect = 2,
                _trust = 2,
                _affection = 3,
                _suspicion = -2,
                _admiration = 2,
                _friendship = 4,
                _anger = -1
            ),
            propagationRange = 1,
            description = "L'allenatore costruisce un rapporto di amicizia con il giocatore."
        )
    )

    events.add(
        SimpleEvent(
            type = SimpleEventType.RISPETTO,
            importance = SimpleEventImportance.NORMALE,
            statistic = CommonTaught(
                _respect = 3,
                _trust = 2,
                _affection = 2,
                _suspicion = -1,
                _admiration = 3,
                _friendship = 2,
                _anger = -1
            ),
            propagationRange = 1,
            description = "Il giocatore mostra rispetto per l'allenatore dopo una buona gestione della squadra."
        )
    )

    events.add(
        SimpleEvent(
            type = SimpleEventType.RABBIA,
            importance = SimpleEventImportance.IMPORTANTE,
            statistic = CommonTaught(
                _respect = -2,
                _trust = -3,
                _affection = -2,
                _suspicion = 4,
                _admiration = -1,
                _friendship = -3,
                _anger = 5
            ),
            propagationRange = 2,
            description = "Il giocatore si arrabbia con l'allenatore per una decisione controversa."
        )
    )

    return events.toList()
}

fun main(){
    val npcs = generateNPCs()
    interactionWithUser(npcs)
}
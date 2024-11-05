package esample.calcio.event

import conceptualMap2.event.Event
import esample.calcio.event.impl.FootballEI
import esample.calcio.event.impl.FootballET
import conceptualMap2.event.GlobalEvent
import esample.calcio.npc.footballer.personality.SimpleMood
import esample.calcio.npc.npcs.presidente

// Creazione degli oggetti Event

// Utilizzabili da tutti
val parlareMale = Event(
    type = FootballET.RABBIA ,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(-0.1f, -0.2f, -0.3f, 0.1f, 0.2f, 0.2f, 0.4f),//15
    description = "La persona si arrabbia con l'allenatore perché lo ha insultato o preso in giro durante la conversazione."
)

// Utilizzabili solo dai giocatori
val richiestaTempoGioco = Event(
    type = FootballET.APPREZZAMENTO ,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(-0.3f, -0.1f, 0f, 0f, 0.2f, 0.2f, 0.3f),//9
    description = "Un giocatore chiede di avere più tempo di gioco."
)

val conflittoSostituzione = Event(
    type = FootballET.RABBIA,
    importance = FootballEI.CRUCIALE,
    statistic = SimpleMood(-0.4f, -0.1f, -0.2f, 0.1f, 0.3f, 0.2f, 0.3f),//16
    description = "Un giocatore è infuriato per essere stato sostituito durante la partita."
)

val lodeTattica = Event(
    type = FootballET.APPREZZAMENTO,
    importance = FootballEI.BANALE,
    statistic = SimpleMood(0.1f, 0.1f, 0f, -0.1f, 0f, -0.1f, 0f),//3
    description = "Un giocatore loda l'allenatore per una tattica vincente."
)

val eventoCriticheTattica = Event(
    type = FootballET.CRITICA,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(-0.1f, 0f, -0.1f, 0.2f, 0.1f, 0.1f, 0f),//6
    description = "Un giocatore esprime critiche costruttive all'allenatore riguardo a determinate scelte."
)

val apprezzamentoGiocatore = Event(
    type = FootballET.APPREZZAMENTO,
    importance = FootballEI.BANALE,
    statistic = SimpleMood(0.1f, 0.1f, 0f, -0.1f, 0f, -0.1f, 0f),//3
    description = "Un giocatore esprime apprezzamento per il supporto ricevuto dall'allenatore."
)

val giocatorePerdeFiducia = Event(
    type = FootballET.DISAGIO,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(-0.2f, -0.1f, 0f, 0.1f, -0.2f, -0.1f, -0.2f),//9
    description = "Il giocatore perde fiducia nell'allenatore dopo una conversazione."
)

val soddisfazioneGiocatore = Event(
    type = FootballET.SODDISFAZIONE,
    importance = FootballEI.BANALE,
    statistic = SimpleMood(0.1f, 0.1f, 0f, -0.1f, 0f, -0.1f, 0f),//3
    description = "Un giocatore esprime soddisfazione per il piano di gioco proposto dall'allenatore."
)

val espressioneDisagio = Event(
    type = FootballET.DISAGIO,
    importance = FootballEI.NORMALE,
    statistic = SimpleMood(-0.1f, 0f, 0f, 0.2f, 0.1f, 0.1f, 0.1f),
    description = "Un giocatore manifesta disagio riguardo alla sua posizione in campo."
)

val conversazioneChiarificatrice = Event(
    type = FootballET.APPREZZAMENTO,
    importance = FootballEI.NORMALE,
    statistic = SimpleMood(0.1f, 0f, 0.1f, 0f, -0.1f, -0.1f, -0.1f),//5
    description = "L'allenatore e un giocatore hanno una conversazione chiarificatrice che aiuta a risolvere malintesi."
)

val sorpresa = Event(
    type = FootballET.SORPRESA,
    importance = FootballEI.NORMALE,
    statistic = SimpleMood(0f, 0.1f, 0f, 0.1f, 0f, 0.2f, 0f),//4
    description = "L'allenatore annuncia qualcosa di inatteso, ad esempio una nuova tattica, per la prossima partita."
)

val disaccordoTattico = Event(
    type = FootballET.DISACCORDO,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(-0.1f, -0.1f, -0.1f, 0.1f, 0.1f, 0.1f, 0.1f),
    description = "Un giocatore esprime disaccordo con la tattica scelta dall'allenatore."
)

val conflittoConGiocatore = Event(
    type = FootballET.RABBIA,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(-0.1f, -0.2f, -0.1f, 0.1f, 0.1f, 0f, 0.3f),//9
    description = "Un giocatore e l'allenatore hanno un acceso scambio di opinioni riguardo alla strategia di gioco."
)

// Utilizzabili solo dal presidente
val esonero = Event(
    FootballET.RABBIA,
    FootballEI.CRUCIALE,
    SimpleMood(-.5f,-1f,-1f,.5f, .7f, .8f,1f),
    "Dopo non aver vinto con il Catanzaro il presidente ha deciso di esonerare l'allenatore",
    presidente
)

val sospettoPresidente = Event(
    type = FootballET.DISACCORDO,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(-0.2f, -0.1f, -0.2f, 0f, 0f, 0.1f, 0.2f),//8
    description = "Il presidente sospetta che l'allenatore non stia prendendo le giuste decisioni."
)

val fiduciaPresidente = Event(
    type = FootballET.FIDUCIA,
    importance = FootballEI.NORMALE,
    statistic = SimpleMood(0.1f, 0.1f, 0f, -0.1f, -.10f, -0.1f, -0.1f),//6
    description = "Il presidente crede che l'allenatore stia prendendo le giuste decisioni."
)

val complimentiPresidente = Event(
    type = FootballET.APPREZZAMENTO,
    importance = FootballEI.NORMALE,
    statistic = SimpleMood(0.1f, 0.2f, 0f, -0.1f, -0.1f, -0.1f, -0.1f),
    description = "Il presidente esprime apprezzamento per l'impegno dell'allenatore."
)

// Utilizzabili solo dalla dirigenza
val valutazionePrestazioniPositivo = Event(
    type = FootballET.APPREZZAMENTO,
    importance = FootballEI.NORMALE,
    statistic = SimpleMood(0.1f, 0.1f, 0f, -0.1f, 0f, -0.1f, -0.1f),
    description = "I dirigenti valutano le prestazioni dell'allenatore in positivo."
)

val valutazionePrestazioniNegativo = Event(
    type = FootballET.CRITICA,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(0f, -0.1f, -0.1f, 0.2f, 0.1f, 02f, 0f),
    description = "I dirigenti valutano le prestazioni dell'allenatore in negativo."
)

val vittoriaImportante = Event(
    type = FootballET.APPREZZAMENTO,
    importance = FootballEI.NORMALE,
    statistic = SimpleMood(0.1f, 0.1f, 0f, 0f, 0f, -0.1f, -0.1f),
    description = "L'allenatore guida la squadra verso una vittoria importante e riceve ammirazione da parte della dirigenza."
)
//Evento staff tecnico
val conflittoStrategia = Event(
    type = FootballET.DISACCORDO,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(-0.1f, -0.1f, 0f, 0.1f, 0.2f, 0.2f, 0.1f),
    description = "Disaccordo con il secondo allenatore riguardo la strategia di gioco."
)

// Eventi pubblici
val riconoscimentoPubblico = GlobalEvent(
    type = FootballET.APPREZZAMENTO,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(0.1f, 0.2f, 0f, -0.1f, -.10f, -0.2f, -0.1f),
    description = "L'allenatore riceve un riconoscimento pubblico per il suo lavoro."
)

val vittoria = GlobalEvent(
    type = FootballET.APPREZZAMENTO,
    importance = FootballEI.IMPORTANTE,
    statistic = SimpleMood(0.1f, 0.1f, 0f, -0.2f, -0.1f, -0.2f, -0.1f),
    description = "La partita contro il Catanzaro è stata vinta."
)

val pareggio = GlobalEvent(
    type = FootballET.APPREZZAMENTO,
    importance = FootballEI.NORMALE,
    statistic = SimpleMood(0f, 0f, 0f, -0.1f, 0.1f, 0f, 0f),
    description = "La partita contro il Catanzaro è stata pareggiata."
)

val sconfitta = GlobalEvent(
    type = FootballET.RABBIA,
    importance = FootballEI.CRUCIALE,
    statistic = SimpleMood(-0.2f, -0.1f, -0.1f, 0.1f, 0.2f, 0.2f, 0.2f),
    description = "La partita contro il Catanzaro è stata persa."
)
//Eventi specifici
val convocazioneMancata = Event(
    type = FootballET.RABBIA,
    importance = FootballEI.CRUCIALE,
    statistic = SimpleMood(-0.2f, -0.3f, 0f, 0.2f, 0.2f, 0.3f, 0.4f),
    description = "Il presidente si arrabbia molto con l'allenatore(utente) dopo che questo non si presenta a una convocazione nel suo ufficio."
)

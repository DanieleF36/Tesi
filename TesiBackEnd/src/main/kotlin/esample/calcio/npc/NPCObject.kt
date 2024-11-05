package esample.calcio.npc

import conceptualMap2.event.Event
import conceptualMap2.npc.NPC
import conceptualMap2.npc.task.Task
import esample.calcio.event.impl.FootballEI
import esample.calcio.event.impl.FootballET
import esample.calcio.npc.footballer.context.GlobalContext
import esample.calcio.npc.footballer.context.LocalContext
import esample.calcio.npc.footballer.context.SimpleContext
import esample.calcio.npc.footballer.personality.SimpleMood
import esample.calcio.npc.npcs.*
import java.io.File

val globalContext = GlobalContext(
    topTeams = listOf("Juventus", "Manchester United", "Manchester City", "Arsenal", "Liverpool", "Real Madrid", "Bayern Monaco"),
    topPlayers = listOf("Lorenzo Guarnieri", "Natan", "Gianluca Marzano", "Milan Ilic", "Gianluca Quarto"),
    topManagers = listOf("Allenatore Juventus", "Cork", "Dominguez", "Domenicano"),
    lastInternationalWinnerTeams = listOf("Juventus (2049-2051)", "Arsenal (2052)", "Borussia Dortmund (2050)", "Bayern Monaco (2048)", "Real Madrid (2046-2047)"),
    generalDescription = "Il calcio inglese è il miglior calcio al mondo, con i migliori giocatori. "+
                         "Il calcio spagnolo è il secondo in posizione, anche se negli ultimi anni le loro squadre hanno giocato male in Champions League hanno comunque giocatori fortissimi"+
                         "Il calcio italiano è in terza posizione, generalmente tutte le squadre sono abbastanza scarse a livello internazionale tranne Juventus e Catanzare, che si fanno rispettare internazionalmente"
)

val localContext = LocalContext(
    players = listOf(
            "Por - Harvey Brennan",
            "DD - Jaud Kasereka",
            "DCD - Iñaki Sáenz",
            "DCS - Milovan Díaz",
            "DS - Pier Paolo Sperti",
            "M - Raúl Rosas",
            "CCD - Pascal Robert",
            "CCS - José Vicente Romero",
            "TD - Lorenzo Guarneri",
            "TS - Riccardo Marinaro",
            "PC - Jeremias",
            "POR - Ivano D'Amato",
            "DC - Luca Sipone",
            "DD - Pepijn Devos",
            "CC - Simone Lavopa",
            "CC - Graziano Dominin",
            "TD - Natan",
            "PC - Manuele Stefanini",
            "TS - Armando Bri",
            "M - Roberto Cerulli",
            "M - Marcello Dini",
            "TS - Roger Izquierdo",
            "DC - Léon Tuithof (capitano)",
    ),
    staff = listOf("vice allenatore - Alejandro Gómez", "Allenatore - Mister"),
    executives = listOf("presidente - Alessandro Rossi", "Elisa Depaoli"),
)

val actualContext = SimpleContext("Il mondo è ambientato nel 2052 e l'allenatore ha portato la squadra a vincere 10 campionati di fila e svariate coppe, tra cui 4 champions league. In questo nuovo anno la juventus sta andando male, si ritrova a gennaio in decima posizione con 5 vittorie, 10 sconfitte e 3 pareggi. La prossima partita è molto importante, è contro la prima della classe, il Catanzaro a Torino, e la juve non vince da 5 partite. L'aria che tira nello spogliatoio è molto pesante ed è chiaro che sia la squadra che la dirigenza stiano pensando che sia arrivato il momento di cambiare aria.")

val metaContext = SimpleContext("L'utente è l'allenatore della juventus")

const val path = "C:\\Users\\danie\\Documents\\Tesi\\TesiBackEnd\\src\\main\\kotlin\\esample\\calcio\\npc\\backstory\\"

fun getNPCs(): HashSet<NPC>{
    presidente.tasks.add(Task("Si deve decidere se esonerare o meno l'allenatore dopo la partita con il Catanzaro"){
        //println("\ndentro task ${it.description}\n")
        if(it.description.contains("persa") || it.description.contains("pareggio"))
            presidente.addEvent(
                Event(
                    FootballET.RABBIA,
                    FootballEI.CRUCIALE,
                    SimpleMood(-.5f,-1f,-1f,.5f, .7f, .8f,1f),
                    "Dopo non aver vinto con il Catanzaro il presidente ha deciso di esonerare l'allenatore e deve comunicarglielo convocandolo nel suo ufficio",
                    presidente
                )
            )
    })
    return hashSetOf(presidente, brennan, damato, romero, guarnieri)
}

fun readFile(filePath: String): String {
    return File(filePath).readText()
}
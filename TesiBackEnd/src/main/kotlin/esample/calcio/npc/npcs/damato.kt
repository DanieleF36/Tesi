package esample.calcio.npc.npcs

import conceptualMap2.npc.knowledge.Knowledge
import conceptualMap2.npc.task.Task
import esample.calcio.conceptualMap.calciatori
import esample.calcio.npc.*
import esample.calcio.npc.footballer.Footballer
import esample.calcio.npc.footballer.GameRole
import esample.calcio.npc.footballer.PlayerStats
import esample.calcio.npc.footballer.Transfer
import esample.calcio.npc.footballer.personality.FootballerPersonality
import esample.calcio.npc.footballer.personality.SimpleCommunicationStyle
import esample.calcio.npc.footballer.personality.SimpleEmotion

val damato = Footballer(
    age = 36,
    name = "Ivano D'Amato",
    group = calciatori,
    context = Knowledge(globalContext, localContext, actualContext, metaContext),
    story = readFile(path +"giocatori\\damato.txt"),
    personality= FootballerPersonality(6, 6, 7, 7, 5, 3, 8, 8, SimpleCommunicationStyle.PASSIVO, SimpleEmotion.HAPPINESS, mapOf(), 5, 7, 5 ),
    seasonalStats= listOf(PlayerStats(34, 28, 0, 0, 0.1f, 2036), PlayerStats(38, 41, 0, 0, 6.1f, 2037), PlayerStats(0, 0, 0, 0, 0f, 2038), PlayerStats(2, 1, 0, 0, 6f, 2039), PlayerStats(3, 2, 0, 0, 6f, 2040), PlayerStats(36, 50, 0, 0, 6.05f, 2041), PlayerStats(36, 27, 0, 0, 6.3f, 2042), PlayerStats(37, 60, 0, 0, 6.2f, 2043), PlayerStats(38, 27, 0, 0, 6.45f, 2044), PlayerStats(40, 26, 0, 0, 6.35f, 2046), PlayerStats(37, 26, 0, 0, 6.35f, 2047), PlayerStats(37, 52, 0, 0, 6.1f, 2048), PlayerStats(37, 40, 0, 0, 6.15f, 2049), PlayerStats(0, 0, 0, 0, 0f, 2050), PlayerStats(0, 0, 0, 0, 0f, 2051)), nationalStats = PlayerStats(0, 0, 0, 0, 0f, -1),
    transfer = listOf(Transfer("Roma", "SPAL", 63000, 2040), Transfer( "SPAL","Benevento", 0, 2041), Transfer("Benevento", "Juventus", 1000000, 2050)),
    role= GameRole.PT
)

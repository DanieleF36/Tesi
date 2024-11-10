package esample.calcio.conceptualMap.objects.nodes

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Fellowship
import esample.calcio.conceptualMap.CommonThoughtImpl
import esample.calcio.conceptualMap.ConceptualMapImpl
import esample.calcio.conceptualMap.readFile

val dirigenti: ConceptualMap = ConceptualMapImpl(
    name = "Dirigenti",
    description = readFile("..\\..\\..\\backstory\\dirigenti.txt"),
    commonThought = CommonThoughtImpl(.2f, .8f, .8f),
    commonThoughtOnGroups = mutableMapOf(
        "Staff Tecnico" to CommonThoughtImpl(.2f, .6f, .9f),
        "Calciatori" to CommonThoughtImpl(.5f, .8f, .7f),
        "Staff di Supporto" to CommonThoughtImpl(0f, .5f, -.9f),
    ),
    fellowship = Fellowship.MID
)


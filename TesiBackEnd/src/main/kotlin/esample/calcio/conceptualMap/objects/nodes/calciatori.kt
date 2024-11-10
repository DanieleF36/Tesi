package esample.calcio.conceptualMap.objects.nodes

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Fellowship
import esample.calcio.conceptualMap.CommonThoughtImpl
import esample.calcio.conceptualMap.ConceptualMapImpl
import esample.calcio.conceptualMap.readFile

val calciatori: ConceptualMap = ConceptualMapImpl(
    name = "Calciatori",
    description = readFile("..\\..\\..\\backstory\\calciatori.txt"),
    commonThought = CommonThoughtImpl(.7f, 1f, .9f),
    commonThoughtOnGroups = mutableMapOf(
        "Staff Tecnico" to CommonThoughtImpl(.4f, .8f, .9f),
        "Dirigenti" to CommonThoughtImpl(.5f, 1f, .2f),
        "Staff di Supporto" to CommonThoughtImpl(.1f, .7f, -.8f),
    ),
    fellowship = Fellowship.HIGH
)
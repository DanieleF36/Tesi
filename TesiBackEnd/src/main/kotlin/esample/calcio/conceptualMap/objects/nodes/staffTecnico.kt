package esample.calcio.conceptualMap.objects.nodes

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Fellowship
import esample.calcio.conceptualMap.CommonThoughtImpl
import esample.calcio.conceptualMap.ConceptualMapImpl
import esample.calcio.conceptualMap.readFile

val staffTecnico: ConceptualMap = ConceptualMapImpl(
    name = "Staff Tecnico",
    description = readFile("..\\..\\..\\backstory\\staffTecnico.txt"),
    commonThought = CommonThoughtImpl(1f, 1f, .1f),
    commonThoughtOnGroups = mutableMapOf(
        "Dirigenti" to CommonThoughtImpl(.4f, .9f, .3f),
        "Calciatori" to CommonThoughtImpl(.9f, .9f, .8f),
        "Staff di Supporto" to CommonThoughtImpl(0.8f, .7f, -1f),
    ),
    fellowship = Fellowship.VERY_HIGH
)
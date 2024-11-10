package esample.calcio.conceptualMap.objects.nodes

import conceptualMap2.conceptualMap.ConceptualMap
import conceptualMap2.conceptualMap.Fellowship
import esample.calcio.conceptualMap.CommonThoughtImpl
import esample.calcio.conceptualMap.ConceptualMapImpl
import esample.calcio.conceptualMap.readFile

val staffSupporto: ConceptualMap = ConceptualMapImpl(
    name = "Staff di Supporto",
    description = readFile("..\\..\\..\\backstory\\staffSupporto.txt"),
    commonThought = CommonThoughtImpl(0.1f, 1f, .1f),
    commonThoughtOnGroups = mutableMapOf(
        "Staff Tecnico" to CommonThoughtImpl(.2f, .9f, .1f),
        "Calciatori" to CommonThoughtImpl(.3f, 1f, -.6f),
        "Dirigenti" to CommonThoughtImpl(0f, 1f, 0f),
    ),
    fellowship = Fellowship.VERY_LOW
)
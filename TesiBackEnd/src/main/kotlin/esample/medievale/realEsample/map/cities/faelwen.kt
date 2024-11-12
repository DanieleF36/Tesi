package esample.medievale.realEsample.map.cities

import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.GroupSize
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.conceptualMap.ConceptualMapImpl
import esample.medievale.link.Relationship

val faelwenConceptualMap = ConceptualMapImpl(
    name = "Faelwen",
    description = "Abitanti profondamente connessi con la natura, celebri per la loro saggezza ecologica e la sostenibilità delle loro pratiche.",
    commonThoughtOnPlayer = CommonThoughtImpl(.5f, .5f, .5f, .5f, .5f),
    commonThoughtOnGroups = mutableMapOf(
        "Aronath" to CommonThoughtImpl(.6f, .4f, .7f, .9f, .4f),
        "Braegor" to CommonThoughtImpl(.4f, .5f, .8f, .8f, .4f),
        "Cyrendil" to CommonThoughtImpl(.4f, .4f, .5f, .5f, .4f),
        "Ellesmere" to CommonThoughtImpl(.2f, .7f, .7f, .7f, .7f),
        "Druwyn" to CommonThoughtImpl(.6f, .5f, .5f, .6f, .5f),
        "Gelvan" to CommonThoughtImpl(.2f, .3f, .4f, .3f, .5f),
        "Havenshire" to CommonThoughtImpl(.2f, .5f, .5f, .3f, .5f)
    ),
    fellowship = Fellowship.HIGH,
    groupSize = GroupSize.MEGA,
    relationships = mutableMapOf(
        "Ellesmere" to Relationship.ALLEATO_neg,
        "Aronath" to Relationship.NEUTRALE_pos,
        "Druwyn" to Relationship.NEUTRALE_pos
    )
)

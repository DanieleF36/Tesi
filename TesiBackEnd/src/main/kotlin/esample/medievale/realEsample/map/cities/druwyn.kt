package esample.medievale.realEsample.map.cities

import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.GroupSize
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.conceptualMap.ConceptualMapImpl
import esample.medievale.link.Relationship

val druwynConceptualMap = ConceptualMapImpl(
    name = "Druwyn",
    description = "Cittadini laboriosi e resilienti, abituati alla dura vita di frontiera e alle frequenti tensioni commerciali e territoriali.",
    commonThoughtOnPlayer = CommonThoughtImpl(.5f, .5f, .5f, .5f, .5f),
    commonThoughtOnGroups = mutableMapOf(
        "Aronath" to CommonThoughtImpl(.8f, .1f, .8f, .9f, 0f),
        "Braegor" to CommonThoughtImpl(.2f, .9f, 1f, 1f, 1f),
        "Cyrendil" to CommonThoughtImpl(.7f, .2f, .4f, .7f, .1f),
        "Ellesmere" to CommonThoughtImpl(.5f, .1f, .6f, .6f, .1f),
        "Faelwen" to CommonThoughtImpl(.3f, .6f, .6f, .4f, .7f),
        "Gelvan" to CommonThoughtImpl(.4f, .2f, .4f, .2f, .1f),
        "Havenshire" to CommonThoughtImpl(.3f, .5f, .6f, .6f, .4f)
    ),
    fellowship = Fellowship.LOW, // High cohesion among citizens
    groupSize = GroupSize.MEGA, // Druwyn is a large city
    relationships = mutableMapOf(
        "Aronath" to Relationship.NEUTRALE_pos,
        "Braegor" to Relationship.ALLEATO_pos,
        "Cyrendil" to Relationship.NEUTRALE_pos
    )
)

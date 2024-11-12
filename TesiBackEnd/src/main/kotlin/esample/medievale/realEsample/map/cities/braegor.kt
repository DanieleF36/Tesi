package esample.medievale.realEsample.map.cities

import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.GroupSize
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.conceptualMap.ConceptualMapImpl
import esample.medievale.link.Relationship

val braegorConceptualMap = ConceptualMapImpl(
    name = "Braegor",
    description = "Un popolo fiero e marittimo, eccellenti navigatori e commercianti astuti.",
    commonThoughtOnPlayer = CommonThoughtImpl(.5f, .5f, .5f, .5f, .5f),
    commonThoughtOnGroups = mutableMapOf(
        "Aronath" to CommonThoughtImpl(.7f, .1f, .6f, .9f, 0f),
        "Cyrendil" to CommonThoughtImpl(.5f, .2f, .3f, .1f, 0f),
        "Ellesmere" to CommonThoughtImpl(.8f, .1f, .5f, .3f, .1f),
        "Faelwen" to CommonThoughtImpl(.3f, .5f, .4f, .4f, .6f),
        "Druwyn" to CommonThoughtImpl(.1f, .9f, .8f, .3f, 1f),
        "Gelvan" to CommonThoughtImpl(.1f, .2f, .3f, .1f, .1f),
        "Havenshire" to CommonThoughtImpl(.2f, .5f, .6f, .2f, .4f)
    ),
    fellowship = Fellowship.HIGH, // High cohesion among citizens
    groupSize = GroupSize.PETA, // Braegor is a large city
    relationships = mutableMapOf(
        "Aronath" to Relationship.CONFLITTO_neg,
        "Ellesmere" to Relationship.NEMICO_neg,
        "Druwyn" to Relationship.ALLEATO_pos
    )
)

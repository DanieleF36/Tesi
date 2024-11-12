package esample.medievale.realEsample.map.cities

import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.GroupSize
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.conceptualMap.ConceptualMapImpl
import esample.medievale.link.Relationship

val cyrendilConceptualMap = ConceptualMapImpl(
    name = "Cyrendil",
    description = "Abitanti saggi e pacifici, noti per la loro diplomazia e capacità di negoziazione.",
    commonThoughtOnPlayer = CommonThoughtImpl(.5f, .5f, .5f, .5f, .5f),
    commonThoughtOnGroups = mutableMapOf(
        "Aronath" to CommonThoughtImpl(.1f, .7f, .9f, .9f, .8f),
        "Braegor" to CommonThoughtImpl(.9f, .1f, .8f, .9f, .1f),
        "Ellesmere" to CommonThoughtImpl(.8f, .3f, .8f, .5f, .2f),
        "Faelwen" to CommonThoughtImpl(.5f, .5f, .4f, .4f, .6f),
        "Druwyn" to CommonThoughtImpl(.7f, .1f, .4f, .2f, .1f),
        "Gelvan" to CommonThoughtImpl(.2f, .8f, .6f, .3f, .7f),
        "Havenshire" to CommonThoughtImpl(.3f, .1f, .5f, .5f, .6f)
    ),
    fellowship = Fellowship.VERY_HIGH, // High cohesion among citizens
    groupSize = GroupSize.GIGA, // Cyrendil is a large city
    relationships = mutableMapOf(
        "Aronath" to Relationship.ALLEATO_pos,
        "Gelvan" to Relationship.NEUTRALE_pos,
    )
)

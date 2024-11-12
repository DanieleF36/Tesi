package esample.medievale.realEsample.map.cities

import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.GroupSize
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.conceptualMap.ConceptualMapImpl
import esample.medievale.link.Relationship

val gelvanConceptualMap = ConceptualMapImpl(
    name = "Gelvan",
    description = "Abitanti pragmatici e riservati, noti per la loro abilità nell'artigianato e nel commercio, nonché per la loro gestione astuta delle risorse naturali.",
    commonThoughtOnPlayer = CommonThoughtImpl(.5f, .5f, .5f, .5f, .5f),
    commonThoughtOnGroups = mutableMapOf(
        "Aronath" to CommonThoughtImpl(.5f, .4f, .9f, .9f, .9f),
        "Braegor" to CommonThoughtImpl(.8f, .2f, .8f, .7f, .1f),
        "Cyrendil" to CommonThoughtImpl(.2f, .6f, .6f, .5f, 1f),
        "Ellesmere" to CommonThoughtImpl(.6f, .2f, .2f, .5f, 0f),
        "Faelwen" to CommonThoughtImpl(.3f, .3f, .4f, .3f, .6f),
        "Druwyn" to CommonThoughtImpl(.6f, .2f, .4f, .1f, .1f),
        "Havenshire" to CommonThoughtImpl(.3f, .1f, .2f, 0f, .1f)
    ),
    fellowship = Fellowship.LOW,
    groupSize = GroupSize.ENORME,
    relationships = mutableMapOf(
        "Cyrendil" to Relationship.NEUTRALE_pos,
    )
)
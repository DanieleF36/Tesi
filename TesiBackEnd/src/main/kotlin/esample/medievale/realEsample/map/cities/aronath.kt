package esample.medievale.realEsample.map.cities

import conceptualMap2.conceptualMap.CommonThought
import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.GroupSize
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.conceptualMap.ConceptualMapImpl
import esample.medievale.link.Relationship

val aronathConceptualMap = ConceptualMapImpl(
    name = "Aronath",
    description = "Cittadini orgogliosi e resilienti, abituati a vivere in una città spesso al centro di conflitti ma anche di prosperità commerciale.",
    commonThoughtOnPlayer = CommonThoughtImpl(.5f, .5f, .5f, .5f, .5f),
    commonThoughtOnGroups = mutableMapOf(
        "Cyrendil" to CommonThoughtImpl(.1f, .7f, .7f, .6f, .7f),
        "Braegor" to CommonThoughtImpl(.7f, .3f, .6f, .9f, 0f),
        "Ellesmere" to CommonThoughtImpl(.6f, .1f, .2f, .2f, .4f),
        "Faelwen" to CommonThoughtImpl(.5f, .5f, .5f, .4f, .6f),
        "Druwyn" to CommonThoughtImpl(.3f, .4f, .4f, .3f, .1f),
        "Gelvan" to CommonThoughtImpl(.2f, .5f, .4f, .3f, .6f),
        "Havenshire" to CommonThoughtImpl(.4f, .5f, .5f, .4f, .5f)
    ),
    fellowship = Fellowship.MID,
    groupSize = GroupSize.PETA,
    relationships = mutableMapOf(
        "Cyrendil" to Relationship.ALLEATO_pos,
        "Braegor" to Relationship.CONFLITTO_neg,
        "Ellesmere" to Relationship.NEMICO_neg,
        "Druwyn" to Relationship.NEUTRALE_pos
    ),
)


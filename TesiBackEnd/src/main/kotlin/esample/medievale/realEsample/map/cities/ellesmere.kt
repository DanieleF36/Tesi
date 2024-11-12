package esample.medievale.realEsample.map.cities

import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.GroupSize
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.conceptualMap.ConceptualMapImpl
import esample.medievale.link.Relationship

val ellesmereConceptualMap = ConceptualMapImpl(
    name = "Ellesmere",
    description = "Cittadini creativi e raffinati, con una forte inclinazione per le arti e la cultura, supportati da un governo illuminato che valorizza l'educazione e le iniziative culturali.",
    commonThoughtOnPlayer = CommonThoughtImpl(.5f, .5f, .5f, .5f, .5f),
    commonThoughtOnGroups = mutableMapOf(
        "Aronath" to CommonThoughtImpl(.5f, .1f, .2f, .8f, .1f),
        "Braegor" to CommonThoughtImpl(.8f, .1f, .2f, .8f, .1f),
        "Cyrendil" to CommonThoughtImpl(.2f, .3f, .3f, .3f, .1f),
        "Faelwen" to CommonThoughtImpl(.1f, .7f, .7f, .6f, .8f),
        "Druwyn" to CommonThoughtImpl(.6f, .2f, .5f, .5f, .1f),
        "Gelvan" to CommonThoughtImpl(.1f, .3f, .5f, .2f, .6f),
        "Havenshire" to CommonThoughtImpl(.2f, .4f, .5f, .3f, .6f)
    ),
    fellowship = Fellowship.HIGH,
    groupSize = GroupSize.MEGA,
    relationships = mutableMapOf(
        "Aronath" to Relationship.NEMICO_neg,
        "Braegor" to Relationship.NEMICO_neg,
        "Faelwen" to Relationship.ALLEATO_neg,
    )
)

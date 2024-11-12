package esample.medievale.realEsample.map.cities

import conceptualMap2.conceptualMap.Fellowship
import conceptualMap2.conceptualMap.GroupSize
import esample.medievale.conceptualMap.CommonThoughtImpl
import esample.medievale.conceptualMap.ConceptualMapImpl
import esample.medievale.link.Relationship

val havenshireConceptualMap = ConceptualMapImpl(
    name = "Havenshire",
    description = "Cittadini intraprendenti e audaci, noti per la loro grande abilità nel commercio marittimo e nella navigazione, con una forte tradizione di indipendenza e un governo repubblicano stabile.",
    commonThoughtOnPlayer = CommonThoughtImpl(.5f, .5f, .5f, .5f, .5f),
    commonThoughtOnGroups = mutableMapOf(
        "Aronath" to CommonThoughtImpl(.8f, .2f, .8f, .6f, .3f),
        "Braegor" to CommonThoughtImpl(.4f, .6f, .9f, .9f, .8f),
        "Cyrendil" to CommonThoughtImpl(.3f, .1f, .5f, .4f, .3f),
        "Ellesmere" to CommonThoughtImpl(.6f, .4f, .7f, .4f, .2f),
        "Faelwen" to CommonThoughtImpl(.2f, .5f, .6f, .4f, .6f),
        "Druwyn" to CommonThoughtImpl(.3f, .5f, .6f, .7f, .6f),
        "Gelvan" to CommonThoughtImpl(.1f, .1f, .3f, 0f, .1f)
    ),
    fellowship = Fellowship.VERY_HIGH,
    groupSize = GroupSize.MEDIO_GRANDE,
    relationships = mutableMapOf(
        "Druwyn" to Relationship.NEUTRALE_neg,
    )
)

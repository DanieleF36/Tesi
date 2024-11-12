package esample.medievale.npc

import kotlin.random.Random

enum class Job {
    CONTADINO,
    PASTORE,
    FABBRO,
    CALZOLAIO,
    FALEGNAME,
    LOCANDIERE,
    SARTO,
    MERCANTE,
    PESCIVENDOLO,
    MONACO,
    SOLDATO,
    STUDIOSO;
    companion object{
        fun random(): Job{
            return when(Random.nextInt(12)){
                0 -> CONTADINO
                1 -> PASTORE
                2 -> FABBRO
                3 -> CALZOLAIO
                4 -> FALEGNAME
                5 -> LOCANDIERE
                6 -> SARTO
                7 -> MERCANTE
                8 -> PESCIVENDOLO
                9 -> MONACO
                10 -> SOLDATO
                11 -> STUDIOSO
                else -> TODO("not implemented yet")
            }
        }
    }
}
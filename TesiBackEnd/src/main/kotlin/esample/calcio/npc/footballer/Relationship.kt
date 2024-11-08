package esample.calcio.npc.footballer

enum class Relationship {
    VERY_CLOSE,
    CLOSE,
    ACQUAINTANCE,
    NO_RELATIONSHIP,
    DISTANT,
    ADVERSARY,
    RIVAL;

    fun decrease(): Relationship{
        return when(this){
            VERY_CLOSE -> CLOSE
            CLOSE -> ACQUAINTANCE
            ACQUAINTANCE -> NO_RELATIONSHIP
            NO_RELATIONSHIP -> DISTANT
            DISTANT -> ADVERSARY
            ADVERSARY -> RIVAL
            RIVAL -> RIVAL
        }
    }

    fun increase(): Relationship{
        return when(this){
            VERY_CLOSE -> VERY_CLOSE
            CLOSE -> VERY_CLOSE
            ACQUAINTANCE -> CLOSE
            NO_RELATIONSHIP -> ACQUAINTANCE
            DISTANT -> NO_RELATIONSHIP
            ADVERSARY -> DISTANT
            RIVAL -> ADVERSARY
        }
    }
}
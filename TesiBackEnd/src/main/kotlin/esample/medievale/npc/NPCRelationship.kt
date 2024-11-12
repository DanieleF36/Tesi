package esample.medievale.npc

enum class NPCRelationship{
    VERY_CLOSE,
    CLOSE,
    NO_RELATIONSHIP,
    DISTANT,
    ADVERSARY;

    fun decrease(): NPCRelationship {
        return when(this){
            VERY_CLOSE -> CLOSE
            CLOSE -> DISTANT
            NO_RELATIONSHIP -> DISTANT
            DISTANT -> ADVERSARY
            ADVERSARY -> ADVERSARY
        }
    }

    fun increase(): NPCRelationship {
        return when(this){
            VERY_CLOSE -> VERY_CLOSE
            CLOSE -> VERY_CLOSE
            NO_RELATIONSHIP -> CLOSE
            DISTANT -> CLOSE
            ADVERSARY -> DISTANT
        }
    }
}
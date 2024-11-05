package conceptualMap2.npc

import kotlinx.serialization.Serializable

@Serializable
abstract class Personality{
    abstract fun toMap(): Map<String, Any>
}
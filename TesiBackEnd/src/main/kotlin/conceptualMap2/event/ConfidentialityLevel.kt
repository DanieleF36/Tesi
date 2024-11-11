package conceptualMap2.event

import kotlinx.serialization.Serializable

@Serializable
abstract class ConfidentialityLevel(val value: Float) {
    abstract fun toMap(): Map<String, Any>

    override fun toString(): String {
        return "ConfidentialityLevel=$value"
    }
}
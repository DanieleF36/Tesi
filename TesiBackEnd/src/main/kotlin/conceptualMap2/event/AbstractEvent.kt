package conceptualMap2.event

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
@Serializable
abstract class AbstractEvent(
    @Serializable(with = LocalDateTimeSerializer::class)
    val generatedTime: LocalDateTime,
) {
    abstract fun toMap(): Map<String, Any>
}
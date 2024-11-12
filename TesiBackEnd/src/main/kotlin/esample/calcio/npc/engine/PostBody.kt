package esample.calcio.npc.engine

import esample.medievale.npc.engine.Message
import kotlinx.serialization.Serializable

@Serializable
internal data class PostBody(
    val model : String,
    val messages: List<Message>,
)
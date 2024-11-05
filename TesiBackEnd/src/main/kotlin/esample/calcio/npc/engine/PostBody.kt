package esample.calcio.npc.engine

import kotlinx.serialization.Serializable

@Serializable
internal data class PostBody(
    val model : String,
    val messages: List<Message>,
)
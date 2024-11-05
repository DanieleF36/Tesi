package esample.calcio.npc.engine

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val message: Msg
)

@Serializable
data class Msg(
    val role: String,
    val content: String
)
package esample.medievale.npc.engine;

import kotlinx.serialization.Serializable;

@Serializable
internal enum class Role{
    user,
    system,
    assistant
}

@Serializable
internal data class Message(
    val role: Role,
    val content: String
) {
    override fun toString(): String {
        return "role=$role content='$content'"
    }
}
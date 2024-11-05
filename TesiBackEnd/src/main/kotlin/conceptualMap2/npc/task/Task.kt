package conceptualMap2.npc.task;

import conceptualMap2.event.Event


open class Task(
    val description: String,
    val action: (Event) -> Unit,
){
    override fun toString(): String {
        return "description='$description')"
    }

    open fun toMap(): Map<String, Any>{
        return mapOf("description" to description)
    }
}
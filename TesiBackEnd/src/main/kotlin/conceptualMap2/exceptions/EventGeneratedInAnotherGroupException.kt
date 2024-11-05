package conceptualMap2.exceptions

class EventGeneratedInAnotherGroupException(msg: String = "Tried to generate an event created from an NPC of another group") : Exception(msg) {
}
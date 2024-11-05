package conceptualMap2.exceptions

class GroupNameUniqueException(msg: String = "The group name is its identifier, it must be unique in the Map"): RuntimeException(msg)
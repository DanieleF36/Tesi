package conceptualMap2.npc.knowledge

import conceptualMap2.npc.sample.SampleContext

/**
 * This class describes the context
 * @sample SampleContext
 */
abstract class Context{
    abstract fun toMap(): Map<String, Any?>
}
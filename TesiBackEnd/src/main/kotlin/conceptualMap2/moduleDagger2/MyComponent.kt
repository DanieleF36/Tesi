package conceptualMap2.moduleDagger2

import conceptualMap2.npc.NPC
import dagger.Component

@Component(modules = [NPCEngineModule::class])
interface MyComponent {
    fun inject(target: NPC)
}
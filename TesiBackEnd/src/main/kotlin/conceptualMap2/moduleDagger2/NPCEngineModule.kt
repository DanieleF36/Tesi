package conceptualMap2.moduleDagger2

import conceptualMap2.npc.NPCEngine
import dagger.Module
import dagger.Provides
import esample.medievale.npc.engine.GPTEngine

@Module
class NPCEngineModule {
    @Provides
    fun provideNPCEngine(): NPCEngine {
        return GPTEngine()
    }
}
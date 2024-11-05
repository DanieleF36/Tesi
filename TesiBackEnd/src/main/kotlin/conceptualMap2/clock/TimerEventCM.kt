package conceptualMap2.clock

import conceptualMap2.event.Event
import observerInterfaces.classic.Observer

class TimerEventCM(
    val event: Event,
    private val npcTot: Int ,
    val cb: () -> Unit,
): Observer {
    private var npc = 0

    fun increment() = npc++

    override fun update() {
        if((npc > npcTot/2f))
            cb()
    }
}
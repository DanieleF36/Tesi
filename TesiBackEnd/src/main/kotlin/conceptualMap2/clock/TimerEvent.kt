package conceptualMap2.clock

import conceptualMap2.event.AbstractEvent
import conceptualMap2.event.Event
import observerInterfaces.classic.Observer
import java.time.Duration
import java.time.temporal.ChronoUnit

/**
 * Questo è unico in ogni singolo gruppo, dato che prima che si propaghi devi raggiungere il 50% dell'intero gruppo e poi si propaga anando a ricreare un oggetto per il nuvo gruppo
 */
open class TimerEvent(
    val event: AbstractEvent,
    val waitingTime: Duration,
    val cb: () -> Unit
): Observer {
    override fun update() {
        if( ChronoUnit.SECONDS.between(event.generatedTime.plus(waitingTime), Clock.getCurrentDateTime()) <=0)
            cb()
    }
}
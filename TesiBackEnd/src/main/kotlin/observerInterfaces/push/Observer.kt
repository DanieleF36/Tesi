package observerInterfaces.push

import conceptualMap2.clock.TimerEventCM

interface Observer {
    fun update(t: TimerEventCM)
}
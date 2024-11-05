package observerInterfaces.push

import conceptualMap2.clock.TimerEventCM

interface Subject {
    fun attach(o: Observer)
    fun detach(o: Observer)
    fun notifyObservers(t: TimerEventCM)
}
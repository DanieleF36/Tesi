package conceptualMap2.clock

import observerInterfaces.classic.Observer
import observerInterfaces.classic.Subject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object Clock: Subject {
    private val observers = mutableListOf<Observer>()

    private var currentTime: LocalDateTime =  LocalDateTime.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun advanceSeconds(seconds: Long) {
        currentTime = currentTime.plusSeconds(seconds)
    }

    fun advanceMinutes(minutes: Long) {
        currentTime = currentTime.plusMinutes(minutes)
    }

    fun advanceHours(hours: Long) {
        currentTime = currentTime.plusHours(hours)
    }

    fun advanceDays(days: Long) {
        currentTime = currentTime.plusDays(days)
    }

    fun advanceMonths(months: Long) {
        currentTime = currentTime.plusMonths(months)
    }

    fun advanceYears(years: Long) {
        currentTime = currentTime.plusYears(years)
    }

    fun getCurrentTime(): String {
        return currentTime.format(formatter)
    }

    fun getCurrentDateTime(): LocalDateTime {
        return currentTime
    }

    fun timeSince(otherTime: LocalDateTime, unit: ChronoUnit = ChronoUnit.SECONDS): Long {
        return unit.between(currentTime, otherTime)
    }

    override fun attach(o: Observer) {
        observers.add(o)
    }

    override fun detach(o: Observer) {
        observers.remove(o)
    }

    override fun notifyObservers() {
        observers.forEach { it.update() }
    }
}
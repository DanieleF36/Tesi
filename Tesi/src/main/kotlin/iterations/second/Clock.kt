package iterations.second

import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.math.min

sealed class TimeComponent{
    class MILLISECONDS(val millis: Int) : TimeComponent()
    class SECONDS(val seconds: Int) : TimeComponent()
    class MINUTES(val minutes: Int) : TimeComponent()
    class HOURS(val hours: Int) : TimeComponent()
    class DAYS(val days: Int) : TimeComponent()
}

class Time(
    var millis: Int = 0,
    var seconds: Int = 0,
    var minutes: Int = 0,
    var hours: Int = 0,
): Comparable<Time>{
    init {
        require(millis in 0..999)
        require(seconds in 0..59)
        require(minutes in 0..59)
        require(hours in 0..23)
    }

    override fun compareTo(other: Time): Int {
        if(hours > other.hours)
            return 1
        else if(hours < other.hours)
            return -1
        else if(minutes > other.minutes)
            return 1
        else if(minutes < other.minutes)
            return -1
        else if(seconds > other.seconds)
            return 1
        else if(seconds < other.seconds)
            return -1
        else if(millis > other.millis)
            return 1
        else if(millis < other.millis)
            return -1
        return 0
    }

    fun clone(): Time {
        return Time(millis, seconds, minutes, hours)
    }
}

class Date(
    val time: Time,
    var days: Int,
    var month: Int,
    var year: Int
): Comparable<Date>{
    init {
        require(month in 0..12)
        require((month in listOf(1,3,5,7,8,10,12) && days in 0 .. 31) ||
                (month in listOf(4,6,9,11) && days in 0 .. 30) ||
                (month == 2 && year%4==0 && days in 0 .. 29) ||
                (month == 2 && year%4!=0 && days in 0 .. 28))
    }
    fun addTime(time: TimeComponent){
        when(time){
            is TimeComponent.MILLISECONDS -> {
                val m = time.millis
                //millis needed to reach 1000
                val diff = 1000 - this.time.millis
                if(m < diff){
                    this.time.millis+=m
                }else {
                    this.time.millis = m%1000
                    addTime(TimeComponent.SECONDS(time.millis/1000))
                }
            }
            is TimeComponent.SECONDS -> {
                val s = time.seconds
                //seconds needed to reach 60
                val diff = 60 - this.time.seconds
                if(s < diff){
                    this.time.seconds+=s
                }else {
                    this.time.seconds = s%60
                    addTime(TimeComponent.MINUTES(time.seconds/60))
                }
            }
            is TimeComponent.MINUTES -> {
                val m = time.minutes
                //minutes needed to reach 60
                val diff = 60 - this.time.minutes
                if(m < diff){
                    this.time.minutes+=m
                }else {
                    this.time.minutes = m%60
                    addTime(TimeComponent.HOURS(time.minutes/60))
                }
            }
            is TimeComponent.HOURS -> {
                val h = time.hours
                //hours needed to reach 24
                val diff = 24 - this.time.hours
                if(h < diff){
                    this.time.hours+=h
                }else {
                    this.time.hours = h%24
                    addTime(TimeComponent.DAYS(time.hours/24))
                }
            }
            is TimeComponent.DAYS -> {
                var d = time.days
                do {
                    //days needed to reach last day of the month
                    val diff = if (month in listOf(1, 3, 5, 7, 8, 10, 12))
                        31 - days
                    else if (month in listOf(4, 6, 9, 11))
                        30 - days
                    else if (month == 2 && year % 4 == 0)
                        29 - days
                    else
                        28 - days

                    if (d <= diff) {
                        days + d
                    } else {
                        if (month != 12)
                            month++
                        else {
                            month = 1
                            year++
                        }
                        days = 1
                        d -= diff
                    }
                }while(d>0)
            }
        }
    }

    fun clone(): Date{
        return Date(time.clone(), days, month, year)
    }

    override fun compareTo(other: Date): Int {
        if(year > other.year)
            return 1
        else if(year < other.year)
            return -1
        else if(month > other.month)
            return 1
        else if(month < other.month)
            return -1
        else if(days > other.days)
            return 1
        else if(days < other.days)
            return -1
        return time.compareTo(other.time)
    }
}

class Clock(private val date: Date){
    private val lock = ReentrantLock()
    private val condition: Condition = lock.newCondition()
    fun goForward(time: TimeComponent){
        lock.lock()
        try {
            date.addTime(time)
            condition.signalAll()
        } finally {
            lock.unlock()
        }
    }
    fun getTime(): Time{
        return date.time.clone()
    }
    fun getDate():Date{
        return date.clone()
    }
    fun waitForChange() {
        lock.lock()
        try {
            condition.await()
        } finally {
            lock.unlock()
        }
    }
}
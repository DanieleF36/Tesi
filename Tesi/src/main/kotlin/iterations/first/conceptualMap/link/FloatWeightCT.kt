package iterations.first.conceptualMap.link

import iterations.first.Event
import iterations.first.Weight
import iterations.first.conceptualMap.CommonTaught
import kotlin.math.ceil

/**
 * FloatWeightCommonTaught
 * This class has the same parameter as CommonTaught but here they are considered a weight and it will be used as following:
 * new_anger = ceil(event_anger * weight_anger)
 */
class FloatWeightCT(
    private var _respect: Float,
    private var _trust: Float,
    private var _affection: Float,
    private var _suspicion: Float,
    private var _admiration: Float,
    private var _friendship: Float,
    private var _anger: Float
): Weight() {
    override fun update(event: Event) {
        if(event.statistic !is CommonTaught)
            throw IllegalArgumentException("CommonTaught event is required")
        val stats = event.statistic as CommonTaught
        stats.respect = ceil(stats.respect * _respect).toInt()
        stats.trust = ceil(stats.trust * _trust).toInt()
        stats.affection = ceil(stats.affection * _affection).toInt()
        stats.suspicion = ceil(stats.suspicion * _suspicion).toInt()
        stats.admiration = ceil(stats.admiration * _admiration).toInt()
        stats.friendship = ceil(stats.friendship * _friendship).toInt()
        stats.anger = ceil(stats.anger * _anger).toInt()
    }

}
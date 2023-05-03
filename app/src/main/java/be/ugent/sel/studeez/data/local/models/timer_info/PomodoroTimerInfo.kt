package be.ugent.sel.studeez.data.local.models.timer_info

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimerVisitor

class PomodoroTimerInfo(
    name: String,
    description: String,
    val studyTime: Int,
    val breakTime: Int,
    val repeats: Int,
    id: String = ""
):  TimerInfo(id, name, description) {


    override fun getFunctionalTimer(): FunctionalTimer {
        return FunctionalPomodoroTimer(studyTime, breakTime, repeats)
    }

    override fun asJson() : Map<String, Any> {
        return mapOf(
            "type" to "break",
            "name" to name,
            "description" to description,
            "studyTime" to studyTime,
            "breakTime" to breakTime,
            "repeats" to repeats,
        )
    }

    override fun <T> accept(visitor: TimerInfoVisitor<T>): T {
        return visitor.visitBreakTimerInfo(this)
    }

}
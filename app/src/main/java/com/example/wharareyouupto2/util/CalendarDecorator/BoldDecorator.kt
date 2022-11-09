package com.example.wharareyouupto2.util.CalendarDecorator

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import com.example.wharareyouupto2.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade


class BoldDecorator(context: Context) : DayViewDecorator {

    val myColor = ContextCompat.getColor(context, R.color.textcolor)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return true
    }
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: StyleSpan(Typeface.BOLD){})
        view?.addSpan(object: RelativeSizeSpan(1.4f){})
        view?.addSpan(object: ForegroundColorSpan(myColor){})
    }
}
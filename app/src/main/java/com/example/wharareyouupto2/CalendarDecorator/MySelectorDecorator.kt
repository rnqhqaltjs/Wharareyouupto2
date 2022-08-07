package com.example.wharareyouupto2.CalendarDecorator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import com.example.wharareyouupto2.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class MySelectorDecorator(context: Context) : DayViewDecorator {

    @SuppressLint("UseCompatLoadingForDrawables")
    private val drawable: Drawable? = context.getDrawable(R.drawable.calendar_background)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable!!)
    }

}
package com.example.ticketsearch

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class ChooseDates(private val context: Context) {

    val dateFormat = SimpleDateFormat("d MMM", Locale("ru"))
    val dayOfWeekFormat = SimpleDateFormat("E", Locale("ru"))

    fun updateDateText(date: Calendar, dateTextView: TextView, dayOfWeekTextView: TextView) {
        dateTextView.text = dateFormat.format(date.time)
        dayOfWeekTextView.text = dayOfWeekFormat.format(date.time)
    }

    fun showDatePicker(onDateSet: (Calendar) -> Unit) {
        val currentDate = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                onDateSet(selectedDate)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
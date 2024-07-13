package com.example.ticketsearch

import android.text.InputFilter
import android.text.Spanned

class CyrillicInputFilter : InputFilter {
    private val cyrillicRegex = Regex("^[А-Яа-яЁё]+$")

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source == null) return null

        val filtered = StringBuilder()
        for (i in start until end) {
            val char = source[i]
            if (cyrillicRegex.matches(char.toString())) {
                filtered.append(char)
            }
        }
        return if (filtered.length == end - start) null else filtered
    }
}
package com.example.ticketsearch

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.EditText

@SuppressLint("ClickableViewAccessibility")
fun EditText.setOnEndDrawableClickListener(action: () -> Unit){
    setOnTouchListener { _, event ->
        val rightDrawable = 2;

        if(event.action == MotionEvent.ACTION_UP) {
            if(event.rawX >= (right - compoundDrawables[rightDrawable].bounds.width())) {
                action()
                return@setOnTouchListener true
            }
        }

        false
    }
}
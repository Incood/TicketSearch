package com.example.ticketsearch

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.data.repository.PreferencesRepository
import kotlinx.coroutines.launch

class TextSaver(
    private val preferencesRepository: PreferencesRepository,
    private val lifecycleOwner: LifecycleOwner
) {

    fun saveAndRestoreText(editText: EditText, preferenceKey: String) {
        // Загрузка сохраненного значения
        lifecycleOwner.lifecycleScope.launch {
            preferencesRepository.getTextFlow(preferenceKey).collect { savedText ->
                if (editText.text.toString() != savedText) {
                    editText.setText(savedText)
                    editText.setSelection(savedText.length) // Установка курсора в конец текста
                }
            }
        }

        // Сохранение значения при изменении текста
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    preferencesRepository.saveText(preferenceKey, s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
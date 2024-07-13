package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Ticket
import com.example.domain.usecase.GetTicketsUseCase
import kotlinx.coroutines.launch
import android.util.Log

class TicketViewModel(private val getTicketsUseCase: GetTicketsUseCase) : ViewModel() {

    private val _tickets = MutableLiveData<List<Ticket>>()
    val tickets: LiveData<List<Ticket>> get() = _tickets

    fun loadTickets() {
        viewModelScope.launch {
            try {
                val result = getTicketsUseCase.execute()
                Log.d("TicketViewModel", "Tickets loaded: $result")
                _tickets.value = result
            } catch (e: Exception) {
                Log.e("TicketViewModel", "Exception: ${e.message}")
            }
        }
    }
}
package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetTicketsListUseCase
import kotlinx.coroutines.launch
import android.util.Log
import com.example.domain.model.Tickets

class TicketsListViewModel(private val getTicketsListUseCase: GetTicketsListUseCase) : ViewModel() {

    private val _tickets = MutableLiveData<List<Tickets>>()
    val tickets: LiveData<List<Tickets>> get() = _tickets

    fun loadTickets() {
        viewModelScope.launch {
            try {
                val result = getTicketsListUseCase.execute()
                Log.d("TicketsListViewModel", "Tickets loaded: $result")
                _tickets.value = result
            } catch (e: Exception) {
                Log.e("TicketsListViewModel", "Exception: ${e.message}")
            }
        }
    }
}
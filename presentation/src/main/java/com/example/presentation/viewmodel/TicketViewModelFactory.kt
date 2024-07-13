package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.GetTicketsUseCase

class TicketViewModelFactory(private val getTicketsUseCase: GetTicketsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TicketViewModel(getTicketsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
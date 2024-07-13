package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.GetTicketsListUseCase

class TicketsListViewModelFactory(private val getTicketsListUseCase: GetTicketsListUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicketsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TicketsListViewModel(getTicketsListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.GetOffersUseCase

class OffersViewModelFactory(private val getOffersUseCase: GetOffersUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OffersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OffersViewModel(getOffersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
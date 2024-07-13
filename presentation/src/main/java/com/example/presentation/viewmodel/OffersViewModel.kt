package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Offer
import com.example.domain.usecase.GetOffersUseCase
import kotlinx.coroutines.launch
import android.util.Log

class OffersViewModel(private val getOffersUseCase: GetOffersUseCase) : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> get() = _offers

    fun loadOffers() {
        viewModelScope.launch {
            try {
                val result = getOffersUseCase.execute()
                Log.d("OffersViewModel", "Offers loaded: $result")
                _offers.value = result
            } catch (e: Exception) {
                Log.e("OffersViewModel", "Exception: ${e.message}")
            }
        }
    }
}
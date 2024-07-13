package com.example.domain.usecase

import com.example.domain.model.Offer
import com.example.domain.repository.OfferRepository

class GetOffersUseCase(private val offerRepository: OfferRepository) {
    suspend fun execute(): List<Offer> {
        return offerRepository.getOffers()
    }
}
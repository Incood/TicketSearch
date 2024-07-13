package com.example.domain.usecase

import com.example.domain.model.Tickets
import com.example.domain.repository.TicketsListRepository

class GetTicketsListUseCase(private val ticketsListRepository: TicketsListRepository) {
    suspend fun execute(): List<Tickets> {
        return ticketsListRepository.getTickets()
    }
}
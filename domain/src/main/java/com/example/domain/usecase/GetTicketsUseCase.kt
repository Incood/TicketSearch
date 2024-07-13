package com.example.domain.usecase

import com.example.domain.model.Ticket
import com.example.domain.repository.TicketRepository

class GetTicketsUseCase(private val ticketRepository: TicketRepository) {
    suspend fun execute(): List<Ticket> {
        return ticketRepository.getTickets()
    }
}
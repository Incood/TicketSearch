package com.example.domain.repository

import com.example.domain.model.Tickets

interface TicketsListRepository {
    suspend fun getTickets(): List<Tickets>
}
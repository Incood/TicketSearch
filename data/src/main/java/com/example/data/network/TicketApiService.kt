package com.example.data.network

import com.example.domain.model.Ticket
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketApiService {
    @GET("uc")
    fun getTickets(
        @Query("id") id: String = "13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta",
        @Query("export") export: String = "download"
    ): Call<TicketsResponse>
}

data class TicketsResponse(
    val tickets_offers: List<Ticket>
)
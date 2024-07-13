package com.example.data.network

import com.example.domain.model.Tickets
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketsListApiService {
    @GET("uc")
    fun getTickets(
        @Query("id") id: String = "1HogOsz4hWkRwco4kud3isZHFQLUAwNBA",
        @Query("export") export: String = "download"
    ): Call<TicketsListResponse>
}

data class TicketsListResponse(
    val tickets: List<Tickets>
)
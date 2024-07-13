package com.example.data.repository

import android.util.Log
import com.example.data.network.RetrofitClientTickets
import com.example.domain.model.Ticket
import com.example.domain.repository.TicketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class TicketRepositoryImpl : TicketRepository {
    override suspend fun getTickets(): List<Ticket> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClientTickets.instance.getTickets().awaitResponse()
                Log.d("TicketRepositoryImpl", "Request URL: ${response.raw().request.url}")
                if (response.isSuccessful) {
                    Log.d("TicketRepositoryImpl", "Response successful: ${response.body()}")
                    response.body()?.tickets_offers ?: emptyList()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "No error message"
                    Log.e("TicketRepositoryImpl", "Response error: $errorBody, Code: ${response.code()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("TicketRepositoryImpl", "Exception: ${e.message}")
                emptyList()
            }
        }
    }
}
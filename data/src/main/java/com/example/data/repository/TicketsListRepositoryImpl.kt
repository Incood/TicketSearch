package com.example.data.repository

import android.util.Log
import com.example.data.network.RetrofitClientTicketsList
import com.example.domain.model.Tickets
import com.example.domain.repository.TicketsListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class TicketsListRepositoryImpl : TicketsListRepository {
    override suspend fun getTickets(): List<Tickets> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClientTicketsList.instance.getTickets().awaitResponse()
                Log.d("TicketsListRepositoryImpl", "Request URL: ${response.raw().request.url}")
                if (response.isSuccessful) {
                    Log.d("TicketsListRepositoryImpl", "Response successful: ${response.body()}")
                    response.body()?.tickets ?: emptyList()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "No error message"
                    Log.e("TicketsListRepositoryImpl", "Response error: $errorBody, Code: ${response.code()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("TicketsListRepositoryImpl", "Exception: ${e.message}")
                emptyList()
            }
        }
    }
}
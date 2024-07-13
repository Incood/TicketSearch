package com.example.data.network

import com.example.domain.model.Offer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("uc")
    fun getOffers(
        @Query("id") id: String = "1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav",
        @Query("export") export: String = "download"
    ): Call<OffersResponse>
}

data class OffersResponse(
    val offers: List<Offer>
)
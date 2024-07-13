package com.example.domain.model

data class Ticket(
    val id: Int,
    val title: String,
    val time_range: List<String>?,
    val price: Price
)


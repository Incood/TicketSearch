package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Tickets
import com.example.presentation.R

class TicketsListAdapter(private var tickets: List<Tickets>) : RecyclerView.Adapter<TicketsListAdapter.TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket_detail, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.bind(ticket)
    }

    override fun getItemCount(): Int = tickets.size

    fun updateTickets(newTickets: List<Tickets>) {
        tickets = newTickets
        notifyDataSetChanged()
    }

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val priceText: TextView = itemView.findViewById(R.id.price_text)
        private val departureTimeText: TextView = itemView.findViewById(R.id.departure_time_text)
        private val departureAirportText: TextView = itemView.findViewById(R.id.departure_airport_text)
        private val arrivalTimeText: TextView = itemView.findViewById(R.id.arrival_time_text)
        private val arrivalAirportText: TextView = itemView.findViewById(R.id.arrival_airport_text)
        private val inTheWayText: TextView = itemView.findViewById(R.id.in_the_way_text)
        private val badgeText: TextView = itemView.findViewById(R.id.badge_text)

        fun bind(ticket: Tickets) {
            priceText.text = "${ticket.price.value} ₽"
            departureTimeText.text = ticket.departure.date.substring(11, 16)
            departureAirportText.text = ticket.departure.airport
            arrivalTimeText.text = ticket.arrival.date.substring(11, 16)
            arrivalAirportText.text = ticket.arrival.airport
            inTheWayText.text = if (ticket.has_transfer) "С пересадкой" else "Без пересадок"
            badgeText.text = ticket.badge ?: ""
            badgeText.visibility = if (ticket.badge != null) View.VISIBLE else View.GONE
        }
    }
}
package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Ticket
import com.example.presentation.R

class TicketAdapter(private var tickets: List<Ticket>) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    private val icons = listOf(R.drawable.bg_circle_red, R.drawable.bg_circle_blue, R.drawable.bg_circle_white)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.bind(ticket, icons[position % icons.size])
    }

    override fun getItemCount(): Int = tickets.size

    fun updateTickets(newTickets: List<Ticket>) {
        tickets = newTickets
        notifyDataSetChanged()
    }

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.title_text)
        private val timeText: TextView = itemView.findViewById(R.id.time_text)
        private val priceText: TextView = itemView.findViewById(R.id.price_text)
        private val iconView: View = itemView.findViewById(R.id.image)

        fun bind(ticket: Ticket, iconRes: Int) {
            titleText.text = ticket.title
            timeText.text = ticket.time_range!!.joinToString(" ")
            priceText.text = String.format("%,d ₽", ticket.price.value)
            iconView.setBackgroundResource(iconRes)
        }
    }
}
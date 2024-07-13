package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Offer
import com.example.presentation.ImageMapping
import com.example.presentation.R

class OffersAdapter(private var offers: List<Offer>) : RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.bind(offer)
    }

    override fun getItemCount(): Int = offers.size

    fun updateOffers(newOffers: List<Offer>) {
        offers = newOffers
        notifyDataSetChanged()
    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)

        fun bind(offer: Offer) {
            titleTextView.text = offer.title
            locationTextView.text = offer.town
            priceTextView.text = itemView.context.getString(R.string.price_format, offer.price.value)
            val imageResource = ImageMapping.getImageResource(offer.id)
            Glide.with(itemView.context)
                .load(imageResource)
                .placeholder(R.drawable.ic_placeholder)
                .into(imageView)
        }
    }
}
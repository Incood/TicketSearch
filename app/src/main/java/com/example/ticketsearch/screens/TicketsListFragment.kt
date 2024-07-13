package com.example.ticketsearch.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.data.repository.PreferencesRepository
import com.example.data.repository.TicketsListRepositoryImpl
import com.example.domain.usecase.GetTicketsListUseCase
import com.example.presentation.adapter.TicketsListAdapter
import com.example.presentation.viewmodel.TicketsListViewModel
import com.example.presentation.viewmodel.TicketsListViewModelFactory
import com.example.ticketsearch.TextSaver

class TicketsListFragment : Fragment() {

    private lateinit var cityFromAndCityToText: TextView
    private lateinit var dataAndPlace: TextView
    private lateinit var textSaver: TextSaver
    private lateinit var preferencesRepository: PreferencesRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TicketsListAdapter

    private val viewModel: TicketsListViewModel by viewModels {
        TicketsListViewModelFactory(GetTicketsListUseCase(TicketsListRepositoryImpl()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tickets_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesRepository = PreferencesRepository(requireContext())
        textSaver = TextSaver(preferencesRepository, viewLifecycleOwner)

        cityFromAndCityToText = view.findViewById(R.id.city_from_and_city_to_text)
        dataAndPlace = view.findViewById(R.id.data_and_place)
        val backBtn: ImageView = view.findViewById(R.id.back_btn)
        recyclerView = view.findViewById(R.id.rcv_tickets)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Инициализация адаптера с пустым списком
        adapter = TicketsListAdapter(emptyList())
        recyclerView.adapter = adapter

        // Обновление данных в адаптере
        viewModel.tickets.observe(viewLifecycleOwner) { tickets ->
            adapter.updateTickets(tickets)
        }

        viewModel.loadTickets()

        val inputCityFrom = EditText(requireContext())
        val inputCityTo = EditText(requireContext())

        textSaver.saveAndRestoreText(inputCityFrom, "input_city_from")
        textSaver.saveAndRestoreText(inputCityTo, "input_city_to")

        cityFromAndCityToText.text = "${inputCityFrom.text}-${inputCityTo.text}"

        val selectedDate = arguments?.getString("selectedDate") ?: ""
        val numberOfSeats = arguments?.getInt("numberOfSeats") ?: 1

        dataAndPlace.text = "$selectedDate, $numberOfSeats ${if (numberOfSeats == 1) "пассажир" else "пассажира"}"

        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_ticketsListFragment_to_selectedCountryFragment)
        }
    }
}
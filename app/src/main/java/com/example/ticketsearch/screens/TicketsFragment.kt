package com.example.ticketsearch.screens

import OfferRepositoryImpl
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.data.repository.PreferencesRepository
import com.example.domain.usecase.GetOffersUseCase
import com.example.presentation.adapter.OffersAdapter
import com.example.presentation.viewmodel.OffersViewModel
import com.example.presentation.viewmodel.OffersViewModelFactory
import com.example.ticketsearch.CyrillicInputFilter
import com.example.ticketsearch.TextSaver

class TicketsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OffersAdapter
    private val viewModel: OffersViewModel by viewModels {
        OffersViewModelFactory(GetOffersUseCase(OfferRepositoryImpl()))
    }

    private lateinit var inputCityFrom: EditText
    private lateinit var inputCityTo: EditText

    private lateinit var textSaver: TextSaver
    private lateinit var preferencesRepository: PreferencesRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tickets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesRepository = PreferencesRepository(requireContext())
        textSaver = TextSaver(preferencesRepository, viewLifecycleOwner)

        recyclerView = view.findViewById(R.id.rv_offers)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Инициализация адаптера с пустым списком
        adapter = OffersAdapter(emptyList())
        recyclerView.adapter = adapter

        // Обновление данных в адаптере
        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            adapter.updateOffers(offers)
        }

        viewModel.loadOffers()

        inputCityFrom = view.findViewById(R.id.input_city_from)
        inputCityTo = view.findViewById(R.id.input_city_to)

        inputCityFrom.filters = arrayOf(CyrillicInputFilter())
        inputCityTo.filters = arrayOf(CyrillicInputFilter())

        // Установите обработчик нажатия до вызова textSaver.saveAndRestoreText
        inputCityTo.setOnClickListener {
            val bottomSheet = ChooseCountryBottomSheet()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        textSaver.saveAndRestoreText(inputCityFrom, "input_city_from")
        textSaver.saveAndRestoreText(inputCityTo, "input_city_to")
        inputCityTo.setText("")
    }
}
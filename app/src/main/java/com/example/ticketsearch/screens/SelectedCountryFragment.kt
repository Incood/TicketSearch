package com.example.ticketsearch.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.data.repository.PreferencesRepository
import com.example.data.repository.TicketRepositoryImpl
import com.example.domain.usecase.GetTicketsUseCase
import com.example.presentation.adapter.TicketAdapter
import com.example.presentation.viewmodel.TicketViewModel
import com.example.presentation.viewmodel.TicketViewModelFactory
import com.example.ticketsearch.ChooseDates
import com.example.ticketsearch.CyrillicInputFilter
import com.example.ticketsearch.TextSaver
import com.example.ticketsearch.setOnEndDrawableClickListener
import java.util.*

class SelectedCountryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var ticketViewModel: TicketViewModel

    private lateinit var inputCityFrom: EditText
    private lateinit var inputCityTo: EditText
    private lateinit var textSaver: TextSaver
    private lateinit var preferencesRepository: PreferencesRepository

    private lateinit var dayOfMonthAndMonthText: TextView
    private lateinit var dayOfWeekText: TextView
    private lateinit var backReelsChip: TextView
    private lateinit var chooseDates: ChooseDates

    private lateinit var backBtn: ImageView
    private lateinit var showAllTicketsBtn: AppCompatButton

    private var selectedDate: String = ""
    private var numberOfSeats: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_selected, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesRepository = PreferencesRepository(requireContext())
        textSaver = TextSaver(preferencesRepository, viewLifecycleOwner)

        recyclerView = view.findViewById(R.id.rcv_tickets)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        ticketAdapter = TicketAdapter(emptyList())
        recyclerView.adapter = ticketAdapter

        val ticketRepository = TicketRepositoryImpl()
        val getTicketsUseCase = GetTicketsUseCase(ticketRepository)
        val viewModelFactory = TicketViewModelFactory(getTicketsUseCase)
        ticketViewModel = ViewModelProvider(this, viewModelFactory).get(TicketViewModel::class.java)

        ticketViewModel.tickets.observe(viewLifecycleOwner) { tickets ->
            ticketAdapter.updateTickets(tickets.take(3))
        }

        ticketViewModel.loadTickets()

        inputCityFrom = view.findViewById(R.id.input_city_from)
        inputCityTo = view.findViewById(R.id.input_city_to)
        dayOfMonthAndMonthText = view.findViewById(R.id.day_of_month_and_month_text)
        dayOfWeekText = view.findViewById(R.id.day_of_week_text)
        backReelsChip = view.findViewById(R.id.back_reels_chip)
        backBtn = view.findViewById(R.id.back_btn)
        showAllTicketsBtn = view.findViewById(R.id.show_all_tickets_btn)

        chooseDates = ChooseDates(requireContext())

        inputCityFrom.filters = arrayOf(CyrillicInputFilter())
        inputCityTo.filters = arrayOf(CyrillicInputFilter())

        textSaver.saveAndRestoreText(inputCityFrom, "input_city_from")
        textSaver.saveAndRestoreText(inputCityTo, "input_city_to")

        inputCityFrom.setOnEndDrawableClickListener {
            swapText(inputCityFrom, inputCityTo)
        }

        inputCityTo.setOnEndDrawableClickListener {
            inputCityTo.setText("")
        }

        // Установить текущую дату по умолчанию
        val currentDate = Calendar.getInstance()
        chooseDates.updateDateText(currentDate, dayOfMonthAndMonthText, dayOfWeekText)
        selectedDate = chooseDates.dateFormat.format(currentDate.time)

        // Обработчик клика для выбора даты отправления
        view.findViewById<View>(R.id.day_of_month_and_month_text).setOnClickListener {
            chooseDates.showDatePicker { date ->
                chooseDates.updateDateText(date, dayOfMonthAndMonthText, dayOfWeekText)
                selectedDate = chooseDates.dateFormat.format(date.time)
            }
        }

        // Обработчик клика для выбора даты обратного билета
        backReelsChip.setOnClickListener {
            chooseDates.showDatePicker { date ->
                backReelsChip.text = chooseDates.dateFormat.format(date.time)
            }
        }

        backBtn.setOnClickListener {
            val bottomSheet = ChooseCountryBottomSheet()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        showAllTicketsBtn.setOnClickListener {
            showTicketsListScreen()
        }
    }

    private fun showTicketsListScreen() {
        val bundle = Bundle().apply {
            putString("selectedDate", selectedDate)
            putInt("numberOfSeats", numberOfSeats)
        }
        findNavController().navigate(R.id.action_selectedCountryFragment_to_ticketsListFragment, bundle)
    }

    private fun swapText(editText1: EditText, editText2: EditText) {
        val textHolder = editText1.text.toString()
        editText1.text = editText2.text
        editText2.setText(textHolder)
    }
}
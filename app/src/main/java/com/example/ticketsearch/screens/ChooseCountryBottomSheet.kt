package com.example.ticketsearch.screens

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.domain.model.City
import com.example.presentation.adapter.CityAdapter
import com.example.ticketsearch.CyrillicInputFilter
import androidx.navigation.fragment.findNavController
import com.example.data.repository.PreferencesRepository
import com.example.ticketsearch.TextSaver
import com.example.ticketsearch.setOnEndDrawableClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseCountryBottomSheet : BottomSheetDialogFragment() {

    private lateinit var inputCityFrom: EditText
    private lateinit var inputCityTo: EditText

    private lateinit var recyclerView: RecyclerView
    private lateinit var cityAdapter: CityAdapter

    private lateinit var textSaver: TextSaver
    private lateinit var preferencesRepository: PreferencesRepository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            setCancelable(true) // Позволяет закрыть диалог при нажатии на заглушку
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_choose_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesRepository = PreferencesRepository(requireContext())
        textSaver = TextSaver(preferencesRepository, viewLifecycleOwner)

        inputCityFrom = view.findViewById(R.id.input_city_from)
        inputCityTo = view.findViewById(R.id.input_city_to)
        recyclerView = view.findViewById(R.id.rcv_cities)

        textSaver.saveAndRestoreText(inputCityFrom, "input_city_from")
        textSaver.saveAndRestoreText(inputCityTo, "input_city_to")
        inputCityTo.setText("")

        inputCityTo.filters = arrayOf(CyrillicInputFilter())

        inputCityTo.setOnEndDrawableClickListener {
            inputCityTo.setText("")
        }

        val btnShortcutDifficultWay: LinearLayout = view.findViewById(R.id.btn_shortcut_difficult_way)
        val btnShortcutSomewhere: LinearLayout = view.findViewById(R.id.btn_shortcut_somewhere)
        val btnShortcutWeekends: LinearLayout = view.findViewById(R.id.btn_shortcut_weekends)
        val btnShortcutHotTickets: LinearLayout = view.findViewById(R.id.btn_shortcut_hot_tickets)

        btnShortcutDifficultWay.setOnClickListener {
            showPlaceholderScreen(getString(R.string.difficult_route))
            dismiss()
        }

        btnShortcutSomewhere.setOnClickListener {
            inputCityTo.setText(getString(R.string.anywhere))
        }

        btnShortcutWeekends.setOnClickListener {
            showPlaceholderScreen(getString(R.string.weekend))
            dismiss()
        }

        btnShortcutHotTickets.setOnClickListener {
            showPlaceholderScreen(getString(R.string.hot_tickets))
            dismiss()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        cityAdapter = CityAdapter(getCities()) { city ->
            inputCityTo.setText(city.cityName)
            showSelectedCountryScreen(city.cityName)
            dismiss()
        }
        recyclerView.adapter = cityAdapter
    }

    private fun getCities(): List<City> {
        return listOf(
            City(R.drawable.img_istanbul, getString(R.string.istanbul)),
            City(R.drawable.img_sochi, getString(R.string.sochi)),
            City(R.drawable.img_phuket, getString(R.string.phuket))
        )
    }

    private fun showPlaceholderScreen(screenName: String) {
        val bundle = Bundle().apply {
            putString("screenName", screenName)
        }
        findNavController().navigate(R.id.nav_placeholder, bundle)
    }

    private fun showSelectedCountryScreen(cityName: String) {
        textSaver.saveAndRestoreText(inputCityTo, "input_city_to")
        findNavController().navigate(R.id.nav_selected_country)
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        // Закрытие при нажатии на заглушку
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss()
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) { }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }
}
package ru.tweek.partylist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import ru.tweek.partylist.databinding.HolidayViewBinding
import java.util.Calendar


class HolidayView : Fragment() {

    private var _binding: HolidayViewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HolidayViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val holidayViewDestination = navController.graph.findNode(ru.tweek.partylist.R.id.holidayView) as NavDestination
        val viewModel = ViewModelProvider(requireActivity())[HolidaysViewModel::class.java]
        val holidaysList = viewModel.holidaysList
        val holiday = holidaysList.find { it.name == holidayViewDestination.label.toString() }
        if (holiday != null) {
            binding.date.text = holiday.date
            binding.description.text = holiday.description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
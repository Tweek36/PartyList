package ru.tweek.partylist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import ru.tweek.partylist.databinding.HolidayViewBinding
import java.util.Calendar


class HolidayView : Fragment() {

    private var _binding: HolidayViewBinding? = null

    private val binding get() = _binding!!

    private var holiday: Holiday? = null

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
        val holiday = Holiday.get(requireContext(), holidayViewDestination.label.toString())!!
        binding.date.text = "${holiday.date.get(Calendar.YEAR)}/${holiday.date.get(Calendar.MONTH) + 1}/${holiday.date.get(Calendar.DAY_OF_MONTH)}"
        binding.description.text = holiday.description
        val bundle = Bundle()
        binding.edit.setOnClickListener {
            bundle.putString("name", holiday.name)
            navController.navigate(ru.tweek.partylist.R.id.action_holidayView_to_secondFragment, bundle)
        }
        binding.delete.setOnClickListener {
            holiday.delete(requireContext())
            navController.navigate(ru.tweek.partylist.R.id.action_holidayView_to_firstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
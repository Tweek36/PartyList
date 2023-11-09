package ru.tweek.partylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.tweek.partylist.databinding.FragmentSecondBinding
import java.util.Calendar


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        if (arguments?.getString("name") != null) {
            val edit = Holiday.get(requireContext(), requireArguments().getString("name")!!)
            if (edit != null) {
                binding.holidayNameText.setText(edit.name)
                binding.datePicker.init(edit.date.get(Calendar.YEAR), edit.date.get(Calendar.MONTH), edit.date.get(Calendar.DAY_OF_MONTH), null)
                binding.holidayDescriptionText.setText(edit.description)
            }
        }
        binding.button.setOnClickListener {
            val name = binding.holidayNameText.text
            val year = binding.datePicker.year
            val month = binding.datePicker.month
            val day = binding.datePicker.dayOfMonth
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val description = binding.holidayDescriptionText.text
            val holiday = Holiday(name.toString(), description.toString(), calendar)
            holiday.save(requireContext())
            navController.navigate(R.id.action_secondFragment_to_firstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
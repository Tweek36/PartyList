package ru.tweek.partylist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.tweek.partylist.databinding.FragmentSecondBinding
import java.util.Calendar


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
                binding.datePicker.init(edit.date.year+1900, edit.date.month, edit.date.day, null)
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
            val date = calendar.time
            val description = binding.holidayDescriptionText.text
            val holiday = Holiday(name.toString(), description.toString(), date)
            Toast.makeText(requireContext(), holiday.name, Toast.LENGTH_SHORT).show()
            holiday.save(requireContext())
            Toast.makeText(requireContext(), Holiday.get(requireContext(), name.toString())?.name, Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_secondFragment_to_firstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
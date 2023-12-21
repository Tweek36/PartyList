package ru.tweek.partylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.tweek.partylist.databinding.SettingsBinding
import androidx.fragment.app.viewModels

class Settings : Fragment() {

    private var _binding: SettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HolidaysViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        viewModel = ViewModelProvider(requireActivity()).get(HolidaysViewModel::class.java)
        binding.colorChange.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        binding.dateShow.isChecked = viewModel.showDate
        binding.dateShow.setOnCheckedChangeListener { _, isChecked ->
            viewModel.showDate = isChecked
        }
        binding.colorChange.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
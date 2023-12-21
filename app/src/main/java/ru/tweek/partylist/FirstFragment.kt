package ru.tweek.partylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import ru.tweek.partylist.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val viewModel = ViewModelProvider(requireActivity())[HolidaysViewModel::class.java]
        val holidaysList = viewModel.holidaysList
        val results: MutableList<String> = holidaysList.map { it.name }.toMutableList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, results)
        val list = binding.list
        list.adapter = adapter
        list.setOnItemClickListener { _, _, position, _ ->
            val text = list.getItemAtPosition(position).toString()
            val holidayViewDestination = navController.graph.findNode(R.id.holidayView) as NavDestination
            holidayViewDestination.label = text
            navController.navigate(R.id.action_firstFragment_to_holidayView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
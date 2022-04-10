package com.minux.reminder.feature_reminder.presentation.reminders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.minux.reminder.R
import com.minux.reminder.databinding.FragmentRemindersBinding
import com.minux.reminder.feature_reminder.presentation.main.ReminderViewModel

class RemindersFragment: Fragment(R.layout.fragment_reminders) {
    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReminderViewModel by activityViewModels()
    private lateinit var reminderAdapter: ReminderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRemindersBinding.bind(view)
        initUI()
        startObserving()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initUI() {
        binding.remindersAddBtn.setOnClickListener{
            findNavController().navigate(R.id.reminderFragment)
        }

        reminderAdapter = ReminderAdapter(requireActivity()) {
            findNavController().navigate(R.id.reminderFragment)
        }

        binding.remindersRecyclerview.adapter = reminderAdapter
    }

    private fun startObserving() {
        viewModel.remindersUiState.observe(requireActivity()) {
            reminderAdapter.submitList(it.reminders)
        }
    }
}
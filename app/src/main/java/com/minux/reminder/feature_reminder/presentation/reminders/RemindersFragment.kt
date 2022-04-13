package com.minux.reminder.feature_reminder.presentation.reminders

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.minux.reminder.R
import com.minux.reminder.core.util.Constants.TAG_APP
import com.minux.reminder.databinding.FragmentRemindersBinding
import com.minux.reminder.feature_reminder.presentation.alarm.AlarmReceiver
import com.minux.reminder.feature_reminder.presentation.main.ReminderViewModel
import java.util.*

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
            viewModel.changeReminderViewType(null)
            findNavController().navigate(R.id.reminderFragment)
        }

        reminderAdapter = ReminderAdapter(requireActivity(), {
            viewModel.changeReminderViewType(it)
            findNavController().navigate(R.id.reminderFragment)
        }, {
            viewModel.updateReminder(it)
        })

        binding.remindersRecyclerview.adapter = reminderAdapter
    }

    private fun startObserving() {
        viewModel.remindersUiState.observe(requireActivity()) {
            reminderAdapter.submitList(it.reminders)
        }
    }
}
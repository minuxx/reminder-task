package com.minux.reminder.feature_reminder.presentation.alarm

import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.minux.reminder.R
import com.minux.reminder.databinding.FragmentRemindersBinding
import com.minux.reminder.feature_reminder.presentation.main.ReminderViewModel

class AlarmFragment: Fragment(R.layout.fragment_reminders) {
    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReminderViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRemindersBinding.bind(view)
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initUI() {
//        binding.remindersAddBtn.setOnClickListener{
//            viewModel.changeReminderViewType(null)
//            findNavController().navigate(R.id.reminderFragment)
//        }
//
//        reminderAdapter = ReminderAdapter(requireActivity(), {
//            viewModel.changeReminderViewType(it)
//            findNavController().navigate(R.id.reminderFragment)
//        }, {
//            viewModel.updateReminder(it)
//        })
//
//        binding.remindersRecyclerview.adapter = reminderAdapter
    }

    private fun stopAlarm() {
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager


    }
}
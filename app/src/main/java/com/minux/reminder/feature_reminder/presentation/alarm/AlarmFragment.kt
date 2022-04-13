package com.minux.reminder.feature_reminder.presentation.alarm

import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.minux.reminder.R
import com.minux.reminder.core.util.Constants.TAG_APP
import com.minux.reminder.databinding.FragmentAlaramBinding
import com.minux.reminder.feature_reminder.presentation.main.ReminderViewModel

class AlarmFragment: Fragment(R.layout.fragment_alaram) {
    private var _binding: FragmentAlaramBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReminderViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAlaramBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initUI() {

    }

    private fun stopAlarm() {
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager


    }
}
package com.minux.reminder.feature_reminder.presentation.alarm

import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.minux.reminder.R
import com.minux.reminder.core.util.Constants
import com.minux.reminder.core.util.Constants.EXTRA_ALARM_ON_OFF
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
        binding.alarmDismissBtn.setOnClickListener{
            dismissAlarm()
        }
    }

    private fun dismissAlarm() {
        val alarmIntent = Intent(context, AlarmService::class.java).apply {
            putExtra(EXTRA_ALARM_ON_OFF, false)
        }

        context?.startService(alarmIntent)
        findNavController().popBackStack()
    }
}
package com.minux.reminder.feature_reminder.presentation.reminder

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.minux.reminder.R
import com.minux.reminder.core.util.Constants.TAG_APP
import com.minux.reminder.databinding.FragmentReminderBinding
import com.minux.reminder.feature_reminder.presentation.main.ReminderViewModel

class ReminderFragment: Fragment(R.layout.fragment_reminder) {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReminderViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReminderBinding.bind(view)
        startObserving()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startObserving() {
        viewModel.reminderUiState.observe(requireActivity()) {
            Log.d(TAG_APP, "TYPE_VIEW: ${it.viewType}, ${it.reminder.toString()}")
        }
    }
}
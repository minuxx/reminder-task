package com.minux.reminder.feature_reminder.presentation.reminder

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.minux.reminder.R
import com.minux.reminder.databinding.FragmentReminderBinding

class ReminderFragment: Fragment(R.layout.fragment_reminder) {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReminderBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
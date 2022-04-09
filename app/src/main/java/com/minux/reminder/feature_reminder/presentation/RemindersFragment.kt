package com.minux.reminder.feature_reminder.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.minux.reminder.R
import com.minux.reminder.databinding.FragmentRemindersBinding

class RemindersFragment: Fragment(R.layout.fragment_reminders) {
    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRemindersBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
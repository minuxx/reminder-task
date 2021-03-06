package com.minux.reminder.feature_reminder.presentation.reminder

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.minux.reminder.R
import com.minux.reminder.core.util.Constants.TYPE_VIEW_EDIT
import com.minux.reminder.core.util.Constants.TYPE_VIEW_NEW
import com.minux.reminder.core.util.TimeFormatUtil
import com.minux.reminder.core.util.UiEvent
import com.minux.reminder.databinding.FragmentReminderBinding
import com.minux.reminder.feature_reminder.domain.model.Reminder
import com.minux.reminder.feature_reminder.presentation.main.ReminderViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReminderFragment: Fragment(R.layout.fragment_reminder) {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReminderViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReminderBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        initUi()
        observeEventFlow()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initUi() {
        binding.reminderSaveBtn.setOnClickListener{
            val hour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) binding.reminderTimepicker.hour
                       else binding.reminderTimepicker.currentHour
            val minute = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) binding.reminderTimepicker.minute
                         else binding.reminderTimepicker.currentMinute

            viewModel.reminderUiState.value?.let{
                if(it.viewType == TYPE_VIEW_NEW) {
                    viewModel.insertReminder(hour, minute)
                } else {
                    viewModel.updateReminder(Reminder(
                        id = it.id,
                        name = it.name.value ?: "",
                        time = TimeFormatUtil.getTimeFormat(hour, minute),
                        isActivated = it.isActivated
                    ))
                }
            }
        }

        viewModel.reminderUiState.observe(viewLifecycleOwner) {
            if (it.viewType == TYPE_VIEW_EDIT) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.reminderTimepicker.hour = it.hour
                } else {
                    binding.reminderTimepicker.currentHour = it.hour
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.reminderTimepicker.minute = it.minute
                } else {
                    binding.reminderTimepicker.currentMinute = it.minute
                }
            }
        }
    }

    private fun observeEventFlow() {
        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        findNavController().popBackStack()
                    }
                    is UiEvent.ShowToast -> {
                        Toast.makeText(requireActivity(), event.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }
}
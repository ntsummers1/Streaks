package com.ntsummers1.streaks.ui.tasks

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.ntsummers1.streaks.MainActivity
import com.ntsummers1.streaks.R
import com.ntsummers1.streaks.data.entity.Task
import kotlinx.android.synthetic.main.fragment_edit_task.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EditTaskFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: EditTaskViewModelFactory
    lateinit var viewModel: EditTaskViewModel

    private var saveTaskButton: MaterialButton? = null
    private var editTaskTitle: TextInputEditText? = null
    private var editTaskDescription: TextInputEditText? = null

    lateinit var editTaskStartDate: TextInputEditText
    lateinit var editTaskStartDateCalendar: Calendar

    lateinit var editTaskEndDate: TextInputEditText
    lateinit var editTaskEndDateCalendar: Calendar

    private var taskId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_edit_task, container, false)

        (activity as MainActivity).setupLowerLevelFragment()
        (activity as MainActivity).editTaskFragment(this)

        arguments?.let {
            taskId = it.getInt("taskId")
        }

        saveTaskButton = root.findViewById(R.id.save_task)
        editTaskTitle = root.findViewById(R.id.edit_task_title_input)
        editTaskDescription = root.findViewById(R.id.edit_task_description_input)

        editTaskStartDateCalendar = Calendar.getInstance()
        editTaskStartDate = root.findViewById(R.id.edit_task_start_date_input)

        val startDate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            editTaskStartDateCalendar.set(Calendar.YEAR, year)
            editTaskStartDateCalendar.set(Calendar.MONTH, monthOfYear)
            editTaskStartDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(editTaskStartDate, editTaskStartDateCalendar)
        }

        editTaskStartDate.setOnClickListener {
            DatePickerDialog(context, startDate,
                editTaskStartDateCalendar.get(Calendar.YEAR),
                editTaskStartDateCalendar.get(Calendar.MONTH),
                editTaskStartDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        editTaskEndDateCalendar = Calendar.getInstance()
        editTaskEndDate = root.findViewById(R.id.edit_task_end_date_input)

        val endDate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            editTaskEndDateCalendar.set(Calendar.YEAR, year)
            editTaskEndDateCalendar.set(Calendar.MONTH, monthOfYear)
            editTaskEndDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(editTaskEndDate, editTaskEndDateCalendar)
        }

        editTaskEndDate.setOnClickListener {
            DatePickerDialog(context, endDate,
                editTaskEndDateCalendar.get(Calendar.YEAR),
                editTaskEndDateCalendar.get(Calendar.MONTH),
                editTaskEndDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(EditTaskViewModel::class.java)

        initUi()
    }

    fun initUi() {

        GlobalScope.async {
            val task = viewModel.findTaskById(taskId)

            task.observe(this@EditTaskFragment, Observer {
                editTaskTitle?.setText(it.getTitle())
                editTaskDescription?.setText(it.getDescription())
            })
        }


        saveTaskButton?.setOnClickListener {
            val title = editTaskTitle?.text.toString()
            val description = editTaskDescription?.text?.toString()
            val startDate = editTaskStartDateCalendar.time
            val endDate = editTaskEndDateCalendar.time

            if (!title.isNullOrEmpty()) {
                GlobalScope.launch {
                    viewModel.updateTask(taskId, title, description, startDate, endDate)
                }
                view?.findNavController()?.
                    navigate(R.id.action_navigation_edit_task_to_navigation_todo)
            }
        }
    }

    private fun updateLabel(date: TextInputEditText, calendar: Calendar) {
        val myFormat = "MM/dd/yy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(calendar.getTime()))
    }
}
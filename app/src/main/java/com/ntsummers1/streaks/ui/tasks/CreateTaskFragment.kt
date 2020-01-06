package com.ntsummers1.streaks.ui.tasks

import android.app.DatePickerDialog
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_create_task.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateTaskFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: CreateTaskViewModelFactory
    lateinit var viewModel: CreateTaskViewModel

    private var addTaskButton: MaterialButton? = null
    private var createTaskTitle: TextInputEditText? = null
    private var createTaskDescription: TextInputEditText? = null

    lateinit var createTaskStartDate: TextInputEditText
    lateinit var createTaskStartDateCalendar: Calendar

    lateinit var createTaskEndDate: TextInputEditText
    lateinit var createTaskEndDateCalendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_task, container, false)

        (activity as MainActivity).setupLowerLevelFragment()
        (activity as MainActivity).createTaskFragment(this)

        addTaskButton = root.findViewById(R.id.add_task)
        createTaskTitle = root.findViewById(R.id.create_task_title_input)
        createTaskDescription = root.findViewById(R.id.create_task_description_input)

        createTaskStartDateCalendar = Calendar.getInstance()
        createTaskStartDate = root.findViewById(R.id.create_task_start_date_input)

        val startDate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            createTaskStartDateCalendar.set(Calendar.YEAR, year)
            createTaskStartDateCalendar.set(Calendar.MONTH, monthOfYear)
            createTaskStartDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(createTaskStartDate, createTaskStartDateCalendar)
        }

        createTaskStartDate.setOnClickListener {
            DatePickerDialog(context, startDate,
                createTaskStartDateCalendar.get(Calendar.YEAR),
                createTaskStartDateCalendar.get(Calendar.MONTH),
                createTaskStartDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        createTaskEndDateCalendar = Calendar.getInstance()
        createTaskEndDate = root.findViewById(R.id.create_task_end_date_input)

        val endDate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            createTaskEndDateCalendar.set(Calendar.YEAR, year)
            createTaskEndDateCalendar.set(Calendar.MONTH, monthOfYear)
            createTaskEndDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(createTaskEndDate, createTaskEndDateCalendar)
        }

        createTaskEndDate.setOnClickListener {
            DatePickerDialog(context, endDate,
                createTaskEndDateCalendar.get(Calendar.YEAR),
                createTaskEndDateCalendar.get(Calendar.MONTH),
                createTaskEndDateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CreateTaskViewModel::class.java)

        initUi()
    }

    fun initUi() {
        addTaskButton?.setOnClickListener {

            val title = createTaskTitle?.text.toString()
            val description = createTaskDescription?.text?.toString()
            val startDate = createTaskStartDateCalendar.time
            val endDate = createTaskEndDateCalendar.time

            if (!title.isNullOrEmpty()) {
                GlobalScope.launch {
                    val task = Task(title, startDate)
                    if (!description.isNullOrEmpty()) task.setDescription(description)
                    if (endDate != null) task.setEndDate(endDate)
                    viewModel.insertTask(task)
                }
                view?.findNavController()?.
                    navigate(R.id.action_navigation_create_task_to_navigation_todo)
            }
        }
    }

    private fun updateLabel(date: TextInputEditText, calendar: Calendar) {
        val myFormat = "MM/dd/yy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(calendar.getTime()))
    }
}
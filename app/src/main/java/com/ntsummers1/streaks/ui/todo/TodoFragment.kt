package com.ntsummers1.streaks.ui.todo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ntsummers1.streaks.MainActivity
import com.ntsummers1.streaks.R
import com.ntsummers1.streaks.data.entity.Task
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class TodoFragment : Fragment() {
    private var taskAdapter = TaskRecyclerViewAdapter()
    lateinit var editText: EditText
    lateinit var myCalendar: Calendar
    lateinit var dateBackButton: Button
    lateinit var dateForwardButton: Button

    @Inject
    lateinit var toDoViewModelFactory: ToDoViewModelFactory
    lateinit var viewModel: TodoViewModel

    lateinit var todoDate: LiveData<Calendar>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_todo, container, false)

        (activity as MainActivity).todoFragment(this)

        (activity as MainActivity).setupHighLevelFragment(
            root.findViewById(R.id.todo_collapsing_toolbar),
            resources.getString(R.string.todo_header)
        )

        val createFAB: FloatingActionButton = root.findViewById(R.id.create_task)
        createFAB.setOnClickListener {
            root.findNavController().navigate(R.id.action_navigation_todo_to_navigation_create_task)
        }

        myCalendar = Calendar.getInstance()

        editText = root.findViewById(R.id.todo_date) as EditText
        updateLabel()

        val date = OnDateSetListener {view, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }

        editText.setOnClickListener {
            DatePickerDialog(context, date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        dateBackButton = root.findViewById(R.id.todo_date_back) as Button
        dateForwardButton = root.findViewById(R.id.todo_date_forward) as Button

        dateBackButton.setOnClickListener {
            moveDateBackward()
        }

        dateForwardButton.setOnClickListener {
            moveDateForward()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, toDoViewModelFactory)
            .get(TodoViewModel::class.java)

        task_recycler_view.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        task_recycler_view.adapter = taskAdapter

        LinearSnapHelper().attachToRecyclerView(task_recycler_view)

        task_recycler_view.setNestedScrollingEnabled(false)

        val itemDecor = DividerItemDecoration(context, HORIZONTAL)
        task_recycler_view.addItemDecoration(itemDecor)

        initUI()
    }

    private fun initUI() = GlobalScope.launch(Dispatchers.Main) {
        val tasks = viewModel.findByDate(myCalendar.time).await()

        tasks.observe(this@TodoFragment, Observer {
            taskAdapter.updateTasks(it)
        })
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(sdf.format(myCalendar.getTime()))
    }

    private fun moveDateForward() {
        myCalendar.add(Calendar.DAY_OF_MONTH, 1)
        updateLabel()
    }

    private fun moveDateBackward() {
        myCalendar.add(Calendar.DAY_OF_MONTH, -1)
        updateLabel()
    }
}
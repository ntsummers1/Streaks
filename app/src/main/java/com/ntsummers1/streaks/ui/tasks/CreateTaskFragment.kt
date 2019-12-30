package com.ntsummers1.streaks.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import javax.inject.Inject

class CreateTaskFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: CreateTaskViewModelFactory
    lateinit var viewModel: CreateTaskViewModel

    private var addTaskButton: MaterialButton? = null
    private var createTaskTitle: TextInputEditText? = null
    private var createTaskDescription: TextInputEditText? = null

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

            val title = createTaskTitle?.text?.toString()
            val description = createTaskDescription?.text?.toString()

            if (!title.isNullOrEmpty() and !description.isNullOrEmpty()) {
                GlobalScope.launch {
                    viewModel.insertTask(Task(title, description))
                }
                view?.findNavController()?.
                    navigate(R.id.action_navigation_create_task_to_navigation_todo)
            }
        }
    }
}
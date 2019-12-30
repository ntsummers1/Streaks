package com.ntsummers1.streaks.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ntsummers1.streaks.MainActivity
import com.ntsummers1.streaks.R
import com.ntsummers1.streaks.data.entity.Task
import com.ntsummers1.streaks.dependencyinjection.AppModule
import com.ntsummers1.streaks.dependencyinjection.DaggerAppComponent
import com.ntsummers1.streaks.dependencyinjection.RoomModule
import dagger.android.AndroidInjection.inject
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoFragment : Fragment() {
    private var taskAdapter = TaskRecyclerViewAdapter()

    @Inject
    lateinit var toDoViewModelFactory: ToDoViewModelFactory
    lateinit var viewModel: TodoViewModel

    var button: Button? = null
    var delbutton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_todo, container, false)

        (activity as MainActivity).daggerFragment(this)

        (activity as MainActivity).setupHighLevelFragment(
            root.findViewById(R.id.todo_collapsing_toolbar),
            resources.getString(R.string.todo_header)
        )

        button = root.findViewById(R.id.createtask) as Button

        delbutton = root.findViewById(R.id.deleteTasks) as Button

        button?.setOnClickListener {
            GlobalScope.launch {
                viewModel.insertTask(Task("Task", "Description"))
            }
        }

        delbutton?.setOnClickListener {
            GlobalScope.launch {
                viewModel.deleteTasks()
            }
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

        initUI()
    }

    private fun initUI() = GlobalScope.launch(Dispatchers.Main) {
        val tasks = viewModel.tasks.await()

        tasks.observe(this@TodoFragment, Observer {
            if (it == null) return@Observer
            taskAdapter.updateTasks(it)
        })
    }
}
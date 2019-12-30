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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoFragment : Fragment() {
    private var taskAdapter = TaskRecyclerViewAdapter()

    @Inject
    lateinit var toDoViewModelFactory: ToDoViewModelFactory

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

        initUi()
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView(task_recycler_view,
            LinearLayoutManager.HORIZONTAL, taskAdapter)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: Int,
        adapter: RecyclerView.Adapter<*>
    ) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), layoutManager, false)
        recyclerView.adapter = adapter
        LinearSnapHelper().attachToRecyclerView(recyclerView)
        recyclerView.setNestedScrollingEnabled(false)
    }



    private fun initUi() {
        val viewModel = ViewModelProviders.of(this, toDoViewModelFactory)
            .get(TodoViewModel::class.java)

        viewModel.getTasks.observe(this, Observer { tasks ->
            taskAdapter.updateTasks(tasks)
        })

        button?.setOnClickListener {
            GlobalScope.launch {
                viewModel.insertTask(Task("Shit", "Balls"))
            }
        }

        delbutton?.setOnClickListener {
                viewModel.deleteTasks()
        }
    }
}
package com.ntsummers1.streaks.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ntsummers1.streaks.MainActivity
import com.ntsummers1.streaks.R
import kotlinx.android.synthetic.main.fragment_create_task.*
import javax.inject.Inject

class CreateTaskFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: CreateTaskViewModelFactory
    lateinit var viewModel: CreateTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_task, container, false)

        (activity as MainActivity).setupLowerLevelFragment()
        (activity as MainActivity).createTaskFragment(this)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CreateTaskViewModel::class.java)

        initUi()
    }

    fun initUi() {
        viewModel.text.observe(this, Observer {
            text_create_task_header.text = it
        })
    }
}
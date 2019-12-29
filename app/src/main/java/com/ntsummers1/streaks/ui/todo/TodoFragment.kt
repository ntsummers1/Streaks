package com.ntsummers1.streaks.ui.todo

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

class TodoFragment : Fragment() {

    private lateinit var todoViewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        todoViewModel =
            ViewModelProviders.of(this).get(TodoViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_todo, container, false)

        (activity as MainActivity).setupHighLevelFragment(
            root.findViewById(R.id.todo_collapsing_toolbar),
            resources.getString(R.string.todo_header)
        )

        return root
    }
}
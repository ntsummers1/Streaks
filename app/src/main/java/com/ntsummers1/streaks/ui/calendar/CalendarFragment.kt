package com.ntsummers1.streaks.ui.calendar

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

class CalendarFragment : Fragment() {

    private lateinit var calendarViewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        calendarViewModel =
            ViewModelProviders.of(this).get(CalendarViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_calendar, container, false)

        (activity as MainActivity).setupHighLevelFragment(
            root.findViewById(R.id.calendar_collapsing_toolbar),
            resources.getString(R.string.calendar_header)
        )

        return root
    }
}
package com.ntsummers1.streaks.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntsummers1.streaks.R
import com.ntsummers1.streaks.data.entity.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskRecyclerViewAdapter: RecyclerView.Adapter<TaskRecyclerViewAdapter.RepositoryViewHolder>() {

    private var tasks: List<Task> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_task, parent, false)
        return RepositoryViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bindRepository(tasks[position])
    }

    fun updateTasks(repos: List<Task>) {
        tasks = repos
        notifyDataSetChanged()
    }

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindRepository(task: Task) {
            itemView.task_name.text = task.getTitle()
            itemView.task_description.text = task.getDescription()

//            itemView.home_fragment_event_day.text =
//                "${DateFormat.format("d", event.startsAt)}"
//            itemView.home_fragment_event_month.text =
//                "${DateFormat.format("MMM", event.startsAt)}"
//            itemView.home_fragment_event_name.text = event.name
//            itemView.home_fragment_event_time_location.text =
//                "${DateFormat.format("hh:mm a", event.startsAt)} • ${event.location}"
//
//            itemView.setOnClickListener {
//                val bundle = Bundle()
//                bundle.putSerializable("EVENT", EventViewModel(event))
//                itemView.findNavController().navigate(
//                    R.id.action_nav_today_to_eventDetailFragment, bundle)
        }
    }
}
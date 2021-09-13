package com.harris.evaldo.todolist.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harris.evaldo.todolist.R
import com.harris.evaldo.todolist.databinding.ItemTaskBinding
import com.harris.evaldo.todolist.model.Task

class TaskListAdapter: ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCalBack()) {
    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bing(getItem(position))

    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root) {
        var cont = 0
        @SuppressLint("SetTextI18n")
        fun bing(item: Task?) {
            binding.textTitulo.text = item?.titulo
            binding.textDesc.text = item?.desc
            binding.textDate.text = "${item?.data} ${item?.hora}"

            binding.appEditView.setOnClickListener {
                popup(item)
            }

            itemView.setOnClickListener{
                if (cont == 0){
                    cont = 1
                    binding.textDesc.isVisible = true
                }else{
                    cont = 0
                    binding.textDesc.isVisible = false
                }
            }

        }

        private fun popup(item: Task?) {
            val appEdit = binding.appEditView
            val popupMenu = PopupMenu(appEdit.context, appEdit)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener{
                when(it.itemId){
                    R.id.menu_edit -> listenerEdit(item!!)
                    R.id.menu_delet -> listenerDelete(item!!)

                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }
}
class DiffCalBack: DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

}



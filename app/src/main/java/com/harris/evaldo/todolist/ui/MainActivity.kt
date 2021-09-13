package com.harris.evaldo.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harris.evaldo.todolist.R
import com.harris.evaldo.todolist.databasesource.TaskDataSource
import com.harris.evaldo.todolist.databinding.ActivityMainBinding
import com.harris.evaldo.todolist.databinding.ItemTaskBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    companion object{
        private const val CREATE_NEW_TASK = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AddTask()
    }
    fun AddTask(){
        binding.floatingActionButton.setOnClickListener{
            startActivityForResult(Intent(this, addTaskActivity::class.java), CREATE_NEW_TASK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_NEW_TASK){
            binding.listTask.adapter = adapter
            adapter.submitList(TaskDataSource.getList())
        }
    }
}
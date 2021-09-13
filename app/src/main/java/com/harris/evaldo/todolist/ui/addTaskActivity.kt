package com.harris.evaldo.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.renderscript.ScriptGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.harris.evaldo.todolist.databasesource.TaskDataSource
import com.harris.evaldo.todolist.databinding.ActivityAddTaskBinding
import com.harris.evaldo.todolist.extension.format
import com.harris.evaldo.todolist.extension.text
import com.harris.evaldo.todolist.model.Task
import java.util.*

class addTaskActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertLinesters()
    }

    private fun insertLinesters() {
        //var titulo2 = binding.textData.editText

        binding.textData.editText?.setOnClickListener{
            var data = MaterialDatePicker.Builder.datePicker().build()
            data.addOnPositiveButtonClickListener {
                var timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time)* -1
                binding.textData.text = Date(it + offset).format()
            }
            data.show(supportFragmentManager, "TAG_DATA")
        }


        binding.textHora.editText?.setOnClickListener{
            var hora = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
            hora.addOnPositiveButtonClickListener {
                val minuto = if (hora.minute in 0..9) "0${hora.minute}" else hora.minute
                val hour = if (hora.hour in 0..9) "0${hora.hour}" else hora.hour
                binding.textHora.text = "${hour}:${minuto}"
            }
            hora.show(supportFragmentManager, "TAG_HORA")
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnCriar.setOnClickListener {
           val task = Task(
               titulo = binding.editTextTitle.text.toString(),
               desc = binding.editTextDesc.text.toString(),
               data = binding.textData.text,
               hora = binding.textHora.text,
               minutos = binding.textHora.text
           )
            TaskDataSource.insertTask(task)
            Toast.makeText(this, "Tarefa ${task.titulo} criada com sucesso!",Toast.LENGTH_LONG).show()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
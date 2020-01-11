package com.example.todolist


import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        dbHandler = DBHandler(this)

        recyclerView.layoutManager = LinearLayoutManager(this)

        activity_main_clear_all.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val deleteAllConfirmationView = layoutInflater.inflate(R.layout.delete_all_confirmation, null)
            dialog.setView(deleteAllConfirmationView)
            dialog.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                dbHandler.clearAllTasks()
                refresh()
            }

            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }


        activity_main_add.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val entertaskview = layoutInflater.inflate(R.layout.enter_new_task, null)
            val taskName    = entertaskview.findViewById<EditText>(R.id.editTodo)
            val taskDescription = entertaskview.findViewById<EditText>(R.id.editTodoDescription)
            dialog.setView(entertaskview)
            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (taskName.text.isNotEmpty() && taskDescription.text.isNotEmpty()) {
                    val task = Task()
                    task.title = taskName.text.toString()
                    task.description = taskDescription.text.toString()
                    dbHandler.addTask(task)
                    refresh()
                }
                else
                {
                     
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                //do nothing
            }
            dialog.show()
        }




    }

    override fun onResume() {
        refresh()
        super.onResume()
    }

    private fun refresh() {
        recyclerView.adapter = TaskViewAdapter(this, dbHandler.getTasks())


        if (dbHandler.getTasks().size > 0)
            textNotTask.visibility = View.GONE
        else
            textNotTask.visibility = View.VISIBLE
    }
}

package com.example.todolist

import android.content.ClipDescription
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.enter_new_task.*
import kotlinx.android.synthetic.main.item_isolated.*
import org.w3c.dom.Text

class IsolateActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_isolated)
        setSupportActionBar(isolatedViewTitle)
        dbHandler = DBHandler(this)


        val title: String = intent.getStringExtra(INTENT_TITLE)
        val description: String = intent.getStringExtra(INTENT_DESCRIPTION)
        val id : Long = intent.getLongExtra(INTENT_ID, -1)

        val actionBar = supportActionBar
        actionBar?.setTitle(title)
        val describe : TextView = findViewById(R.id.isolatedViewDescription)
        describe.setText(description)

        actionBar!!.setDisplayHomeAsUpEnabled(true)

        isolatedDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val deleteConfirmationView = layoutInflater.inflate(R.layout.delete_confirmation, null)
            dialog.setView(deleteConfirmationView)
            dialog.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                dbHandler.deleteTask(id)
                onBackPressed()
            }

            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }

            dialog.show()
        }

        isolatedEdit.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val entertaskview = layoutInflater.inflate(R.layout.enter_new_task, null)
            val taskName    = entertaskview.findViewById<EditText>(R.id.editTodo)
            taskName.setText(title)
            val taskDescription = entertaskview.findViewById<EditText>(R.id.editTodoDescription)
            taskDescription.setText(description)
            dialog.setView(entertaskview)
            dialog.setPositiveButton("Save Changes") { _: DialogInterface, _: Int ->
                if (taskName.text.isNotEmpty() && taskName.text.length <= 30 && taskDescription.text.isNotEmpty()) {
                    val task = Task()
                    task.title = taskName.text.toString()
                    task.description = taskDescription.text.toString()
                    dbHandler.updateTask(task, id)
                    onBackPressed()
//                    refresh()
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

    private fun refresh() {
//      recyclerView.adapter = TaskViewAdapter(this, dbHandler.getTasks())

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
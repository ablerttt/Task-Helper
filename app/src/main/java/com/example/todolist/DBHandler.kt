package com.example.todolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHandler (val context: Context) : SQLiteOpenHelper( context, DB_NAME, null, DB_VERSION) {

    private val createTaskTable  = "CREATE TABLE $TABLE_TASKS (" +
            " $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " $COL_TITLE VARCHAR," +
            " $COL_DESCRIPTION VARCHAR," +
            " $COL_DATE_CREATED datetime DEFAULT CURRENT_TIMESTAMP);"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTaskTable)
        print("Table was created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    fun addTask( newTask : Task ) : Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put( COL_TITLE, newTask.title )
        cv.put( COL_DESCRIPTION, newTask.description + " ")
        val b : String = cv.getAsString(COL_DESCRIPTION)
        val result = db.insert(TABLE_TASKS, null, cv)
        return result != (-1).toLong()
    }

    fun updateTask( changedTask: Task, id : Long) : Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put( COL_ID, id )
        cv.put( COL_TITLE, changedTask.title)
        cv.put( COL_DESCRIPTION, changedTask.description  )
        val result = db.update( TABLE_TASKS, cv, "$COL_ID=$id", null)
        return result != (-1)
    }

    fun deleteTask ( id : Long ) : Boolean {
        val db = writableDatabase
        val result = db.delete( TABLE_TASKS, "$COL_ID=$id", null)
        return result != -1
    }

    fun clearAllTasks() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_TASKS")
    }



    fun getTasks() : MutableList<Task> {
        val result : MutableList<Task> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * FROM $TABLE_TASKS", null)

        if (queryResult.moveToFirst()) {
            do {
                val task = Task()
                task.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                task.title = queryResult.getString(queryResult.getColumnIndex(COL_TITLE))
                task.description = queryResult.getString(queryResult.getColumnIndex(COL_DESCRIPTION))
                result.add(task)
            } while ( queryResult.moveToNext() )
        }
        queryResult.close()
        return result
    }

}
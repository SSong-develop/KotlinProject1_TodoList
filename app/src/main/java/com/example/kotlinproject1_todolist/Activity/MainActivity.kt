package com.example.kotlinproject1_todolist.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.kotlinproject1_todolist.Adapter.TodoAdapter
import com.example.kotlinproject1_todolist.Database.AppDatabase
import com.example.kotlinproject1_todolist.Decoration.RecyclerVIewItemDecoration
import com.example.kotlinproject1_todolist.Model.Todo
import com.example.kotlinproject1_todolist.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_task_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**

 */
class MainActivity : AppCompatActivity() {

    val todo = arrayListOf<Todo>()
    private lateinit var mDb : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        setSupportActionBar(main_toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        mDb = AppDatabase.getInstance(this@MainActivity)

        add_button.setOnClickListener {
            // AlertDialog ON
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.add_task_dialog, null)

            // View Widget
            val Todo_createbtn = view.findViewById<MaterialButton>(R.id.Todo_createbtn)
            val title_add = view.findViewById<TextInputEditText>(R.id.title_add)
            val comment = view.findViewById<TextInputEditText>(R.id.comment)

            // Dialog create
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("New task")
                .setNegativeButton("취소") { dialog, which ->
                    Toast.makeText(this@MainActivity, "cancel", Toast.LENGTH_LONG).show()
                }.create()
            alertDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)


            Todo_createbtn.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    mDb.todoDao().insert(Todo(title_add?.text.toString(),comment?.text.toString()))
                }
                alertDialog.dismiss()
            }
            alertDialog.setView(view)
            alertDialog.show()
        }

        todo_list.apply {
            layoutManager = GridLayoutManager(this@MainActivity,2)
            addItemDecoration(RecyclerVIewItemDecoration(10,10))
            mDb.todoDao().getAll().observe(this@MainActivity , Observer{
                adapter = TodoAdapter(it)
            })
        }
    }
}
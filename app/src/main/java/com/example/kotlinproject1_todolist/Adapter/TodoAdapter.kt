package com.example.kotlinproject1_todolist.Adapter

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.kotlinproject1_todolist.Database.AppDatabase
import com.example.kotlinproject1_todolist.Database.TodoDao
import com.example.kotlinproject1_todolist.Model.Todo
import com.example.kotlinproject1_todolist.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.todolist_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class TodoAdapter(val items: List<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: Todo) {
            view.Todo_title.text = item.title
            view.Todo_comment.text = item.comment
            view.setOnClickListener(listener)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder { // 원하는 RecyclerView item Inflate
        val inflateView =
            LayoutInflater.from(parent.context).inflate(R.layout.todolist_item, parent, false)
        return TodoViewHolder(inflateView)
    }

    override fun onBindViewHolder(
        holder: TodoAdapter.TodoViewHolder,
        position: Int
    ) { // RecyclerView item에 data set
        val item = items[position]
        val listener = View.OnClickListener {
            // delete Dialog Come up!
            val alertDialog = AlertDialog.Builder(it.context)
                .setTitle("Remove Todo??")
                .setPositiveButton("예"){ dialog, which ->
                   CoroutineScope(Dispatchers.IO).launch{
                       val db = AppDatabase.getInstance(it.context)
                       db.todoDao().delete(item)
                   }
                }
                .setNegativeButton("아니요"){dialog, which ->
                    Toast.makeText(it.context,"Cancel",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }.create()
            alertDialog.show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}
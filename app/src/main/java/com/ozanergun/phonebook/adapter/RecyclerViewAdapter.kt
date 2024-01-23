package com.ozanergun.hw2.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.ozanergun.hw2.R
import com.ozanergun.hw2.db.Contact
import com.ozanergun.hw2.db.ContactDatabase
import com.ozanergun.hw2.util.Constants
import com.ozanergun.hw2.view.SecondActivity

class RecyclerViewAdapter(private val context: Context, private val recyclerItemValues: MutableList<Contact>) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewItemHolder>() {
    lateinit var customDialog: Dialog
    lateinit var btnClose: Button
    lateinit var btnDelete: Button
    lateinit var btnUpdate: Button
    companion object {
        const val TYPE_ODD_ITEM = 1
        const val TYPE_EVEN_ITEM = 0
    }
    private val contactDB: ContactDatabase by lazy {
        Room.databaseBuilder(context, ContactDatabase::class.java, Constants.DATABASENAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View
        //val itemView: View = inflator.inflate(R.layout.item_layout, viewGroup, false)
        //return RecyclerViewItemHolder(itemView)
        if (viewType == TYPE_EVEN_ITEM) {
            itemView = inflator.inflate(R.layout.item_layout, viewGroup, false)
            return RecyclerViewItemHolder(itemView)
        } else{
            itemView = inflator.inflate(R.layout.item_layout2, viewGroup, false)
            return RecyclerViewItemHolder(itemView)
        }
    }

    override fun onBindViewHolder(recyclerViewItemHolder: RecyclerViewItemHolder, position: Int) {
        val item = recyclerItemValues[position]
        val ch: Char
        ch = item.full_name.first()
        recyclerViewItemHolder.initialView.text = ch.toString()
        recyclerViewItemHolder.contactName.text = item.full_name
        recyclerViewItemHolder.contactNumber.text = "+90 5"+item.phone_no.toString()
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    override fun getItemViewType(position: Int): Int {
        val sc = recyclerItemValues[position]
        return if (position % 2 == 0) TYPE_EVEN_ITEM else TYPE_ODD_ITEM
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
        var initialView: TextView
        var contactName: TextView
        var contactNumber: TextView
        init {
            initialView = itemView.findViewById(R.id.initialView)
            contactName = itemView.findViewById(R.id.contactName)
            contactNumber = itemView.findViewById(R.id.contactNumber)
            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(view: View): Boolean {
            //Toast.makeText(view.context, "${contactName.text} is long clicked", Toast.LENGTH_SHORT).show()
            customDialog = Dialog(context)
            customDialog.setContentView(R.layout.custom_dialog)
            customDialog!!.show()
            btnClose = customDialog!!.findViewById<Button>(R.id.btnClose)
            btnClose.setOnClickListener {
                customDialog!!.dismiss()
            }
            btnDelete = customDialog!!.findViewById<Button>(R.id.btnDelete)
            btnDelete.setOnClickListener {
                val id: Int
                id = contactNumber.text.toString().replace("+90 5", "").toInt()
                val contact = Contact(id, "${contactName.text}")
                contactDB.contactDao().deleteContact(contact)
                customDialog!!.dismiss()
                (context as SecondActivity).finish()
                Toast.makeText(view.context, "Contact successfully deleted!", Toast.LENGTH_SHORT).show()
            }
            /*btnUpdate = customDialog!!.findViewById<Button>(R.id.btnUpdate)
            btnUpdate.setOnClickListener {}*/
            return true
        }
    }
}

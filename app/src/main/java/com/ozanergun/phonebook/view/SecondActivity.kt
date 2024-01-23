package com.ozanergun.hw2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.ozanergun.hw2.adapter.RecyclerViewAdapter
import com.ozanergun.hw2.databinding.ActivitySecondBinding
import com.ozanergun.hw2.db.Contact
import com.ozanergun.hw2.db.ContactDatabase
import com.ozanergun.hw2.util.Constants

class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    var adapter: RecyclerViewAdapter?=null

    private val contactDB: ContactDatabase by lazy {
        Room.databaseBuilder(this, ContactDatabase::class.java, Constants.DATABASENAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        binding.contactRecycler.setLayoutManager(LinearLayoutManager(this))

        getData()

        binding.AddContact.setOnClickListener {
            finish()
        }
    }

    private fun displayCustomers(contacts: MutableList<Contact>) {
        adapter = RecyclerViewAdapter(this, contacts)
        binding.contactRecycler.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    fun getData(){
        if(contactDB.contactDao().readAllContacts().isNotEmpty()){
            displayCustomers(contactDB.contactDao().readAllContacts())
        }
        else{
            binding.contactRecycler.adapter = null
        }
    }
}
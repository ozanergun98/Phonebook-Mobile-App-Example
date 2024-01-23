package com.ozanergun.hw2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.room.Room
import com.ozanergun.hw2.databinding.ActivityMainBinding
import com.ozanergun.hw2.adapter.RecyclerViewAdapter
import com.ozanergun.hw2.db.Contact
import com.ozanergun.hw2.db.ContactDatabase
import com.ozanergun.hw2.util.Constants

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        binding.AddContact.setOnClickListener {
            val full_name = binding.editTextName.text.toString()
            val phone_no = binding.editTextNumber.text.toString()
            if (full_name!=""&&phone_no!=""){
                val contact = Contact(phone_no.toInt(), full_name)
                contactDB.contactDao().createContact(contact)
                binding.editTextName.setText("");
                binding.editTextNumber.setText("");
                Toast.makeText(this, "Contact successfully added!", Toast.LENGTH_SHORT).show()
            }else Toast.makeText(this, "Please don't leave empty fields!", Toast.LENGTH_SHORT).show()
        }

        binding.ContactList.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

}
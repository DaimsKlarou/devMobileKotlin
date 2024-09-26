package com.example.tp1_dev_mobile.contact

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.tp1_dev_mobile.ContactAdapter
import com.example.tp1_dev_mobile.HomeActivity
import com.example.tp1_dev_mobile.R
import com.example.tp1_dev_mobile.db.SiglDB

class AllContact : ComponentActivity() {
    private lateinit var listContact: ListView
    private lateinit var addContact : ImageView
    private lateinit var comeBack : ImageView
    private lateinit var settings : ImageView

    lateinit var db: SiglDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_contact)

        db = SiglDB(this)
        listContact = findViewById(R.id.listContact)

        comeBack = findViewById(R.id.retourner)
        comeBack.setImageResource(R.drawable.ic_arrow_32)

        settings = findViewById(R.id.settings)
        settings.setImageResource(R.drawable.ic_settings_32)

        addContact = findViewById(R.id.addContact)
        addContact.setImageResource(R.drawable.ic_add_32)

        addContact.setOnClickListener {
            val intent = Intent(this, NewContact::class.java)
            startActivity(intent)
        }

        settings.setOnClickListener {
            Intent(this, ContactActivity::class.java).apply {
                startActivity(this)
            }
        }

        try {
            val postArray = db.getAllContacts()
            Log.d("AllContactActivity", "Posts: $postArray")

            val adapter = ContactAdapter(this, R.layout.item_contact, postArray)

            listContact.adapter = adapter

        } catch (e: Exception) {
            Log.e("AllContactActivity", "Error getting contacts", e)
        }

    }

}
package com.example.tp1_dev_mobile.contact

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.SearchEvent
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.tp1_dev_mobile.ContactAdapter
import com.example.tp1_dev_mobile.HomeActivity
import com.example.tp1_dev_mobile.R
import com.example.tp1_dev_mobile.data.Contact
import com.example.tp1_dev_mobile.db.SiglDB

class AllContact : ComponentActivity() {
    private lateinit var listContact: ListView
    private lateinit var addContact : ImageView
    private lateinit var comeBack : ImageView
    private lateinit var settings : ImageView
    private lateinit var searchEvent: EditText
    private lateinit var contactArray : MutableList<Contact>
    private lateinit var adapter : ContactAdapter

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

        searchEvent = findViewById(R.id.search)

        addContact.setOnClickListener {
            val intent = Intent(this, NewContact::class.java)
            startActivity(intent)
        }

        settings.setOnClickListener {
            Intent(this, ContactActivity::class.java).apply {
                startActivity(this)
            }
        }

        searchEvent.addTextChangedListener(object : TextWatcher {
            var previousText: String = ""
            override fun afterTextChanged(s: Editable?) {
                // On récupère le texte actuel
                val currentText = s.toString().trim()

                // Si la longueur a diminué, on peut supposer que la touche backspace a été utilisée
                if (currentText.length < previousText.length) {
                    deleteSearch(currentText)
                    Log.d("AllContactActivity", "Suppression d'un caractère")
                }

                // Sauvegarder le texte actuel comme le "précédent" pour la prochaine comparaison
                previousText = currentText
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ne rien faire ici
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filtrer la liste à chaque changement de texte
                val searchText = s.toString().lowercase().trim()
                filterContacts(searchText)
            }
        })

        try {
            contactArray = db.getAllContacts()
            Log.d("AllContactActivity", "Contacts retrieved: $contactArray")
            adapter = ContactAdapter(this, R.layout.item_contact, contactArray)
            listContact.adapter = adapter

        } catch (e: Exception) {
            Log.e("AllContactActivity", "Error getting contacts", e)
        }

    }

    private fun deleteSearch(currentText: String) {
        adapter.clear()
        adapter.addAll(db.getAllContacts())

        filterContacts(currentText)
    }

    private fun filterContacts(searchText: String) {
        val filteredList = adapter.getItems().filter { contact ->
            contact.name.contains(searchText, ignoreCase = true) || contact.prenoms.contains(searchText, ignoreCase = true)
//            contact.name.lowercase().contains(searchText) || contact.prenoms.lowercase().contains(searchText)
        }
        adapter.clear()
        adapter.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }

}
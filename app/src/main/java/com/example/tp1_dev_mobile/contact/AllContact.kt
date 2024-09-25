package com.example.tp1_dev_mobile.contact

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp1_dev_mobile.ContactAdapter
import com.example.tp1_dev_mobile.HomeActivity
import com.example.tp1_dev_mobile.R
import com.example.tp1_dev_mobile.data.Contact
import com.example.tp1_dev_mobile.db.SiglDB
import java.lang.reflect.Array

class AllContact : ComponentActivity() {
    private lateinit var listContact: ListView
    lateinit var db: SiglDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_contact)

        db = SiglDB(this)

        listContact = findViewById(R.id.listContact)
        val postArray = arrayListOf(
            Contact("Charles", "Klarou", "0173535299", "Etudiant"),
            Contact("George", "Domi", "0114536899", "Etudiant"),
            Contact("Aude", "Camara", "0701203454", "Etudiant"),
            Contact("Estelle", "Djebre", "02030405", "Etudiant"),
            Contact("Franck", "Yoro", "0101020304", "Etudiant")
        )
        val adapter = ContactAdapter(this, R.layout.item_contact, postArray)

        listContact.adapter = adapter

        listContact.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->

                val favoriteContact = postArray[position]
                when (position) {
                    0 ->
                        Toast.makeText(this, "verification...", Toast.LENGTH_LONG).show()

                    1 ->
                        // Action pour le deuxième item
                        Intent(this, NewContact::class.java).also {
                            startActivity(it)
                        }

                    2 ->
                        // Action pour le troisième item
                        Intent(this, EditContact::class.java).also {
                            startActivity(it)
                        }

                    3 ->
                        // Action pour le quatrième item
                        Intent(this, HomeActivity::class.java).also {
                            startActivity(it)
                        }

                    else ->                 // Action pour tout autre item si nécessaire
                        Toast.makeText(applicationContext, "Autre item cliqué", Toast.LENGTH_SHORT)
                            .show()
                }
            }

    }

}
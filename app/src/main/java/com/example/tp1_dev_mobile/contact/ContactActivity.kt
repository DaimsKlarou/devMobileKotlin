package com.example.tp1_dev_mobile.contact

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.tp1_dev_mobile.HomeActivity
import com.example.tp1_dev_mobile.R


class ContactActivity : ComponentActivity() {
    private lateinit var imageView: ImageView
    private lateinit var listContactItem : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact)

        //Ajout du logo sur le haut de l'application
        imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.ic_arrow_32)

        listContactItem = findViewById(R.id.listContact)
//
//        listContactItem.setOnClickListener(new onItemClickListener {
//
//        })

        listContactItem.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            when (position) {
                0 ->
                    // Action pour le premier item
                    Intent(this, AllContact::class.java).also {
                        startActivity(it)
                    }

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
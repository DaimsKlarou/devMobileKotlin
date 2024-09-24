package com.example.tp1_dev_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class Historique : ComponentActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.historique)

        val postList = findViewById<ListView>(R.id.listPost)
        val postsArray = listOf("post 1", "post 2", "post 3", "post 4")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postsArray)
        postList.adapter = adapter

        //chargement de l'image de retour
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.ic_history_32)
    }

}
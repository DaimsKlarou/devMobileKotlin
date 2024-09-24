package com.example.tp1_dev_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class ConversionResult : ComponentActivity() {
    private lateinit var resultatText: TextView
    private lateinit var texteConversion : TextView
    private lateinit var gobackHome : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.result_consersion)

        //chargement de l'image de retour
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.ic_arrow_32)

        resultatText = findViewById(R.id.resultat)
        texteConversion = findViewById(R.id.message)
        gobackHome = findViewById(R.id.returnhome)

        val resultat = intent.getStringExtra("resultat")
        var devis = intent.getStringExtra("devis")
        val phrase = intent.getStringExtra("phrase")

        resultatText.text = "$resultat $devis"
        texteConversion.text = "${texteConversion.text} $phrase"

        btnEvent()
    }

    private fun goHome(){
        val intentHome = Intent(this, HomeActivity::class.java)
        startActivity(intentHome)
        finish()
    }

    private fun btnEvent(){
        gobackHome.setOnClickListener {
            goHome()
        }
    }
}
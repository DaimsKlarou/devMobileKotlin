package com.example.tp1_dev_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class HomeActivity : ComponentActivity() {
    private lateinit var btn_conversion: Button
    private lateinit var btn_historique: Button
    private lateinit var btn_retour: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_home)

        val textHello = findViewById<TextView>(R.id.TextHello)

        // 1: Recupere le nom de l'utilisateur
        val name = intent.getStringExtra("name")
        val prenoms = intent.getStringExtra("prenoms")


        // 2: l'envoyer dans le HomeView
        textHello.text = "Bienvenue $name $prenoms sur votre espace en MASTER M2 SIGL"

        //initialisation des variables de la vue
        btn_conversion = findViewById<Button>(R.id.btn_conversion)
        btn_historique = findViewById<Button>(R.id.btn_historique)
        btn_retour = findViewById<Button>(R.id.btn_retour)

        //ecouteur d'evenement
        eventButton()
    }

    private fun ConversionView(){
        val intentToConversion = Intent(this, Conversion::class.java)
        startActivity(intentToConversion)
    }

    private fun HistoriqueView(){
        val intentToHistorique = Intent(this, Historique::class.java)
        startActivity(intentToHistorique)
    }

    private fun ComeBack(){
        setResult(RESULT_OK)
        finish()
    }

    private fun eventButton(){
        btn_conversion.setOnClickListener {
            ConversionView()
        }

        btn_historique.setOnClickListener {
            HistoriqueView()
        }

        btn_retour.setOnClickListener {
            ComeBack()
        }
    }
}
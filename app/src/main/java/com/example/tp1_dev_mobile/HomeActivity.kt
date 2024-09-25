package com.example.tp1_dev_mobile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.tp1_dev_mobile.contact.ContactActivity
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

class HomeActivity : ComponentActivity() {
    private lateinit var btn_conversion: Button
    private lateinit var btn_historique: Button
    private lateinit var btn_retour: Button
    private lateinit var imageView : ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_home)

        val textHello = findViewById<TextView>(R.id.TextHello)


        //Ajout du logo sur le haut de l'application
        imageView = findViewById<ImageView>(R.id.logoimage)
        imageView.setImageResource(R.drawable.ic_contacts_32)

        // Récupérer le GifImageView
        val gifImageView = findViewById<GifImageView>(R.id.loginImage)

        // Créer une instance de GifDrawable à partir du drawable
        val gifDrawable = gifImageView.drawable as GifDrawable

        // Définir le nombre de boucles à 1 pour ne jouer qu'une seule fois
        gifDrawable.setLoopCount(1)

        // 1: Recupere le nom de l'utilisateur
        val name = intent.getStringExtra("name")
        val prenoms = intent.getStringExtra("prenoms")


        // 2: l'envoyer dans le HomeView
        textHello.text = "Bienvenue sur votre espace en MASTER M2 SIGL"

        //initialisation des variables de la vue
        btn_conversion = findViewById<Button>(R.id.btn_conversion)
        btn_historique = findViewById<Button>(R.id.btn_historique)
        btn_retour = findViewById<Button>(R.id.btn_retour)

        //ecouteur d'evenement
        eventButton()

        imageView.setOnClickListener {
            contactPage()
        }
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

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quitter l'application")
        builder.setMessage("Êtes-vous sûr de vouloir quitter ?")

        // Evenement pour faire l'application
        builder.setPositiveButton("Oui") { dialog, which ->
            finishAffinity()  // Ferme l'application
        }

        // evenement pour fermer la boite de dialogue
        builder.setNegativeButton("Non") { dialog, which ->
            dialog.dismiss()
        }

        // Afficher la boîte de dialogue
        builder.show()
    }

    private fun contactPage(){
        val valIntent = Intent(this, ContactActivity::class.java)
        startActivity(valIntent)
    }

    private fun eventButton(){
        btn_conversion.setOnClickListener {
            ConversionView()
        }

        btn_historique.setOnClickListener {
            HistoriqueView()
        }

        btn_retour.setOnClickListener {
            showExitConfirmationDialog()
        }

    }


}
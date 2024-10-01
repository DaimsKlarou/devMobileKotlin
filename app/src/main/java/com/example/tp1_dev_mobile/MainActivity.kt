package com.example.tp1_dev_mobile

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.tp1_dev_mobile.db.SiglDB

class MainActivity : ComponentActivity() {
    //VARIABLE DE GESTION DE LA BASE DE DONNEE
    lateinit var db : SiglDB

    //VARIABLE DE GESTION DES EVENEMENTS DE LA VUE
    private lateinit var prenoms: EditText
    private lateinit var btnAnnuler: Button
    private lateinit var btnValider: Button
    private lateinit var nameField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        db = SiglDB(this)

        // Initialiser les champs
        btnValider = findViewById(R.id.btnValider)
        btnAnnuler = findViewById(R.id.btnAnnuler)
        prenoms = findViewById(R.id.Prenoms)
        nameField = findViewById(R.id.Name)

        //Ajout du logo sur le haut de l'application
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.logo_sigl)

        // Configurer les écouteurs de clic
        btnEvent()
    }

    private fun homeView(name: String, prenoms: String) {
        val intentToHomeActivity = Intent(this, HomeActivity::class.java)
        intentToHomeActivity.putExtra("name", name)
        intentToHomeActivity.putExtra("prenoms", prenoms)
        startActivity(intentToHomeActivity)
    }

    // Récupérer les données entrées dans le formulaire
    private fun getName(): String {
        return nameField.text.toString()
    }

    private fun getPrenoms(): String {
        return prenoms.text.toString()
    }

    // Vérifier les données entrées et valider
    private fun validateInputs(name: String, prenoms: String): Boolean {
        return name.trim().isNotEmpty() && prenoms.trim().isNotEmpty()
    }

    private fun validateInputsCanceled(name: String, prenoms: String) : Boolean{
        return name.trim().isNotEmpty() || prenoms.trim().isNotEmpty()
    }

    // Afficher les données dans une boîte de dialogue
    private fun showDialog(name: String, firstName: String) {
        homeView(name, firstName)
    }

    // Réinitialiser les zones de saisie
    private fun clearInputFields() {
        nameField.text.clear()
        prenoms.text.clear()
    }

    // Gérer les erreurs
    private fun showErrorMessage() {
        Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
    }

    // Gérer les événements du clic pour le bouton Valider
    private fun onValidateButtonClick() {
        val name = getName()
        val firstName = getPrenoms()

//        if (validateInputs(name, firstName)) {
//            showDialog(name, firstName)
//            onCancelButtonClick()
//        } else {
//            showErrorMessage()
//        }

        showDialog(name, firstName)
        onCancelButtonClick()
    }

    // Gérer les événements du clic pour le bouton Annuler
    private fun onCancelButtonClick() {
        val name = getName()
        val prenoms = getPrenoms()

        if (validateInputsCanceled(name, prenoms)){
            clearInputFields()
        } else {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun btnEvent() {
        btnValider.setOnClickListener {
            onValidateButtonClick()
        }

        btnAnnuler.setOnClickListener {
            onCancelButtonClick()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}

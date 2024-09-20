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

class MainActivity : ComponentActivity() {
    private lateinit var prenoms: EditText
    private lateinit var btnAnnuler: Button
    private lateinit var btnValider: Button
    private lateinit var nameField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        // Initialiser les champs
        btnValider = findViewById(R.id.btnValider)
        btnAnnuler = findViewById(R.id.btnAnnuler)
        prenoms = findViewById(R.id.Prenoms)
        nameField = findViewById(R.id.Name)

        //Ajout du logo sur le haut de l'application
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.logo)

        // Configurer les écouteurs de clic
        btnEvent()

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
        return name.isNotEmpty() && prenoms.isNotEmpty()
    }

    // Afficher les données dans une boîte de dialogue
    private fun showDialog(name: String, firstName: String) {
        AlertDialog.Builder(this)
            .setTitle("Données saisies")
            .setMessage("Nom : $name\nPrénom : $firstName")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    // Réinitialiser les zones de saisie
    private fun clearInputFields() {
        nameField.text.clear()
        prenoms.text.clear()
    }

    // Gérer les erreurs
    private fun showErrorMessage() {
        Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
    }

    // Gérer les événements du clic pour le bouton Valider
    private fun onValidateButtonClick() {
        val name = getName()
        val firstName = getPrenoms()

        if (validateInputs(name, firstName)) {
            showDialog(name, firstName)
//            val intentToHomeActivity = Intent(this, HomeActivity::class.java)
//            startActivity(intentToHomeActivity) // Lance la nouvelle activité
            onCancelButtonClick()
        } else {
            showErrorMessage()
        }
    }

    // Gérer les événements du clic pour le bouton Annuler
    private fun onCancelButtonClick() {
        clearInputFields()
    }

    private fun btnEvent() {
        btnValider.setOnClickListener {
            onValidateButtonClick()
        }

        btnAnnuler.setOnClickListener {
            onCancelButtonClick()
        }
    }
}

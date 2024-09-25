package com.example.tp1_dev_mobile.contact

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp1_dev_mobile.R
import com.example.tp1_dev_mobile.data.Contact
import com.example.tp1_dev_mobile.db.SiglDB

class NewContact : ComponentActivity() {
    //initialisation de la base de donnee
    lateinit var db: SiglDB

    private lateinit var name : EditText
    private lateinit var prenoms : EditText
    private lateinit var numero : EditText
    private lateinit var profession :EditText

    private lateinit var btnValider : Button
    private lateinit var btnAnnuler : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_contact)

        db = SiglDB(this)

        name = findViewById(R.id.name)
        prenoms = findViewById(R.id.Prenoms)
        numero = findViewById(R.id.numero)
        profession = findViewById(R.id.profession)

        btnValider = findViewById(R.id.btnValider)
        btnAnnuler = findViewById(R.id.btnAnnuler)

        btnEvent()
    }

    private fun validatedValue(): Boolean {
        return name.text.toString().isNotEmpty() && numero.text.toString().isNotEmpty() && prenoms.text.toString().isNotEmpty() && profession.text.toString().isNotEmpty()
    }

    private fun btnEvent(){
        val nameText = name.text.toString()
        val numeroText = numero.text.toString()
        val professionText = profession.text.toString()

        btnValider.setOnClickListener {
            if(validatedValue()){
                val contact = Contact( name.text.toString(), prenoms.text.toString(), numero.text.toString(), profession.text.toString())
                val result_contact = db.addContact(contact)

                if(result_contact){
                    Toast.makeText(this, "Contact saved!!", Toast.LENGTH_LONG).show()
                    Intent(this, ContactActivity::class.java).also {
                        it.putExtra("name", nameText)
                        it.putExtra("numero", numeroText)
                        it.putExtra("profession", professionText)
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(this, "un probleme s'est produit lors de l'enregistrement", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "les champs de saisie nom et numero sont obligatoire!", Toast.LENGTH_LONG).show()
            }
        }

        btnAnnuler.setOnClickListener {
            Toast.makeText(this, "Le bouton annule a bien ete clicker", Toast.LENGTH_LONG).show()
        }
    }
}
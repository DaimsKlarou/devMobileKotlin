package com.example.tp1_dev_mobile.contact

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.tp1_dev_mobile.R
import com.example.tp1_dev_mobile.data.Contact
import com.example.tp1_dev_mobile.db.SiglDB
import pl.droidsonroids.gif.GifImageView

class EditContact : ComponentActivity() {
    //initialisation de la base de donnee
    lateinit var db: SiglDB

    private lateinit var name : EditText
    private lateinit var prenoms : EditText
    private lateinit var numero : EditText
    private lateinit var profession : EditText
    private lateinit var contactImage: GifImageView

    private lateinit var btnValider : Button
    private lateinit var btnAnnuler : Button
    lateinit var contact : Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_contact)

        db = SiglDB(this)

        name = findViewById(R.id.name)
        prenoms = findViewById(R.id.Prenoms)
        numero = findViewById(R.id.numero)
        profession = findViewById(R.id.professionShow)
        contactImage = findViewById(R.id.new_contact_image)

        btnValider = findViewById(R.id.btnValider)
        btnAnnuler = findViewById(R.id.btnAnnuler)

        // Récupérer l'objet Contact
        val contact = intent.getParcelableExtra<Contact>("contact")
        val contactId = contact?.id ?: -1

        if (contact != null) {
            Log.d("EditActivity", "Contact ID: $contactId")
            // Afficher les informations du contact
            name.setText(contact.name)
            prenoms.setText(contact.prenoms)
            numero.setText(contact.numero)
            profession.setText(contact.profession)
        } else {
            Log.e("EditActivity", "Contact not found in Intent")
        }

        btnValider.setOnClickListener {
            if (name.text.isNotEmpty() && prenoms.text.isNotEmpty() && numero.text.isNotEmpty() && profession.text.isNotEmpty()){
                Log.d("EditActivity", "Updating contact with ID: $contactId")
                val contactEdit = Contact(
                    name.text.toString(),
                    prenoms.text.toString(),
                    numero.text.toString(),
                    profession.text.toString(),
                    contactId)

                val result = db.updateContact(contactEdit)

                if (result){
                    Log.d("EditActivity", "Contact updated successfully")
                    Toast.makeText(this, "Contact Updated successfully!!", Toast.LENGTH_LONG).show()
                    setResult(RESULT_OK)
                    Intent(this, AllContact::class.java).apply {
                        startActivity(this)
                    }
                    finish()
                }else{
                    Toast.makeText(this, "Erreur lors de la mise à jour du contact", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }

        }

        btnAnnuler.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }

}
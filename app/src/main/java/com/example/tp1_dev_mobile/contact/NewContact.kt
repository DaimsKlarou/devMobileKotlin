package com.example.tp1_dev_mobile.contact

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.example.tp1_dev_mobile.R
import com.example.tp1_dev_mobile.data.Contact
import com.example.tp1_dev_mobile.db.SiglDB
import pl.droidsonroids.gif.GifImageView
import java.io.ByteArrayOutputStream

class NewContact : ComponentActivity() {
    //initialisation de la base de donnee
    lateinit var db: SiglDB

    private lateinit var name : EditText
    private lateinit var prenoms : EditText
    private lateinit var numero : EditText
    private lateinit var profession :EditText
    private lateinit var contactImage: GifImageView

    private lateinit var btnValider : Button
    private lateinit var btnAnnuler : Button
    private lateinit var bitmap : Bitmap

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

        val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { data ->
            val uri = data
            val inputStream = contentResolver.openInputStream(uri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            contactImage.setImageBitmap(bitmap)
        }

        contactImage.setOnClickListener {
           galleryLauncher.launch("image/*")
        }

        btnEvent()
    }

    private fun validatedValue(): Boolean {
        return name.text.toString().isNotEmpty() && numero.text.toString().isNotEmpty() && prenoms.text.toString().isNotEmpty() && profession.text.toString().isNotEmpty()
    }

    private fun btnEvent(){

        btnValider.setOnClickListener {

            if(validatedValue()){
                val image :ByteArray = getBytes(bitmap)
                val contact = Contact(name = name.text.toString(), prenoms =  prenoms.text.toString(), numero = numero.text.toString(), profession = profession.text.toString(), image = image)
                val resultatContact = db.addContact(contact)

                if(resultatContact){
                    Toast.makeText(this, "Contact saved!!", Toast.LENGTH_LONG).show()
                    Intent(this, AllContact::class.java).also {
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

    private fun getBytes(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}
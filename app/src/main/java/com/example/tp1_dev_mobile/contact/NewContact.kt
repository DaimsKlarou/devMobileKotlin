package com.example.tp1_dev_mobile.contact

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.tp1_dev_mobile.R
import com.example.tp1_dev_mobile.data.Contact
import com.example.tp1_dev_mobile.db.SiglDB
import pl.droidsonroids.gif.GifImageView

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

        contactImage.setOnClickListener {
            val IntenImg = Intent(Intent.ACTION_GET_CONTENT)
            IntenImg.type = "image/*"
            startActivityForResult(IntenImg, 100)
        }

        btnEvent()
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK){
            val uri = data?.data
            val inputStream = contentResolver.openInputStream(uri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            contactImage.setImageBitmap(bitmap)
        }
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
                val contact = Contact(name.text.toString(), prenoms.text.toString(), numero.text.toString(), profession.text.toString())
                val result_contact = db.addContact(contact)

                if(result_contact){
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
}
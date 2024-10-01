package com.example.tp1_dev_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp1_dev_mobile.R.drawable
import com.example.tp1_dev_mobile.contact.EditContact
import com.example.tp1_dev_mobile.data.Contact
import com.example.tp1_dev_mobile.db.SiglDB

class ShowContact : ComponentActivity() {
    private lateinit var nomComplet : TextView
    private lateinit var phone_number : TextView
    private lateinit var professionShow : TextView
    private lateinit var imageView2 : ImageView
    private lateinit var favorite : TextView
    private lateinit var edite : TextView
    private lateinit var shareContact : TextView
    private lateinit var contact: Contact

    lateinit var db: SiglDB

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_contact)
        db = SiglDB(this)

        nomComplet = findViewById(R.id.nomComplet)
        phone_number = findViewById(R.id.phone_number)
        professionShow = findViewById(R.id.professionShow)
        imageView2 = findViewById(R.id.imageView2)
        imageView2.setImageResource(drawable.logo)

        favorite = findViewById(R.id.favorite)
        edite = findViewById(R.id.edite)
        shareContact = findViewById(R.id.shareContact)

        contact = intent.getParcelableExtra<Contact>("contact")!!
        nomComplet.text = "${contact.name} ${contact.prenoms}"
        phone_number.text = contact.numero
        professionShow.text = contact.profession

        if (contact.favorite){
            val drawable = ContextCompat.getDrawable(this, R.drawable.favorite_star_32)
            favorite.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
        }
        if (contact.image.size > 1) {
            imageView2.setImageBitmap(getBitmapFromByteArray(contact.image))
        }

        favorite.setOnClickListener {
            if (contact.favorite){
                val drawable = ContextCompat.getDrawable(this, R.drawable.star_outline_32)
                favorite.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                Toast.makeText(this, "${contact.name} n'est plus favori", Toast.LENGTH_SHORT).show()
            } else{
                val drawable = ContextCompat.getDrawable(this, R.drawable.favorite_star_32)
                favorite.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                Toast.makeText(this, "${contact.name} est maintenant en favori", Toast.LENGTH_SHORT).show()
            }
            contact.favorite = !contact.favorite
            db.updateContact(contact)
        }

        edite.setOnClickListener {
            Intent(this, EditContact::class.java).also {
                it.putExtra("contact", contact)
                startActivity(it)
            }
        }
    }

    private fun getBitmapFromByteArray(image: ByteArray): Bitmap? {
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        return bitmap
    }
}
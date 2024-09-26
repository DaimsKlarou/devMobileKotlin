package com.example.tp1_dev_mobile

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp1_dev_mobile.data.Contact

class ShowContact : ComponentActivity() {
    private lateinit var nomComplet : TextView
    private lateinit var phone_number : TextView
    private lateinit var professionShow : TextView
    private lateinit var imageView2 : ImageView
    private lateinit var favorite : TextView
    private lateinit var edite : TextView
    private lateinit var shareContact : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_contact)

        nomComplet = findViewById(R.id.nomComplet)
        phone_number = findViewById(R.id.phone_number)
        professionShow = findViewById(R.id.professionShow)
        imageView2 = findViewById(R.id.imageView2)

        imageView2.setImageResource(R.drawable.logo)

        val contact = intent.getParcelableExtra<Contact>("contact")

        if (contact != null) {
            nomComplet.text = "${contact.name} ${contact.prenoms}"
            phone_number.text = contact.numero
            professionShow.text = contact.profession
        }
    }
}
package com.example.tp1_dev_mobile

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.tp1_dev_mobile.contact.EditContact
import com.example.tp1_dev_mobile.data.Contact

class ContactAdapter(
    var mContext: Context,
    var resources: Int,
    var values: List<Contact>
) : ArrayAdapter<Contact>(mContext , resources, values) {
     lateinit var favoriteContact : ImageView

    // Stocke la position de l'élément cliqué
    private var selectedPosition: Int = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = values[position]

        Log.d("ContactAdapter", "getContact() called with position $position: ${contact.id} - ${contact.name}")

        val itemView = LayoutInflater.from(mContext).inflate(resources, parent, false)
        val name = itemView.findViewById<TextView>(R.id.name)
        val prenoms = itemView.findViewById<TextView>(R.id.prenoms)
        val numero = itemView.findViewById<TextView>(R.id.numero)
        val profession = itemView.findViewById<TextView>(R.id.profession)
        val optionDetail  = itemView.findViewById<LinearLayout>(R.id.optionContact)
        val infoContact = itemView.findViewById<ImageView>(R.id.infoContact)
        val editContact = itemView.findViewById<ImageView>(R.id.editContact)
        val favoriteContact = itemView.findViewById<ImageView>(R.id.favorite)


        name.text = contact.name
        prenoms.text = contact.prenoms
        numero.text = contact.numero
        profession.text = contact.profession

        // Contrôle la visibilité des options : seulement pour l'élément cliqué
        optionDetail.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE

        // Gère le clic sur un élément
        itemView.setOnClickListener {
            // Met à jour la position sélectionnée
            selectedPosition = if (selectedPosition == position) -1 else position
            // Recharge la liste pour appliquer la nouvelle visibilité
            notifyDataSetChanged()
        }

//        // Clic sur "infoContact"
//        infoContact.setOnClickListener {
//            // Lancer une nouvelle activité pour afficher les informations du contact
//            val intent = Intent(mContext, ContactInfoActivity::class.java)
//            intent.putExtra("CONTACT_NAME", post.name)
//            intent.putExtra("CONTACT_PRENOMS", post.prenoms)
//            intent.putExtra("CONTACT_NUMERO", post.numero)
//            intent.putExtra("CONTACT_PROFESSION", post.profession)
//            mContext.startActivity(intent)
//        }

        // Clic sur "editContact"
        editContact.setOnClickListener {
            // Lancer une activité pour éditer le contact
            val intent = Intent(mContext, EditContact::class.java)
            intent.putExtra("contact", contact)
            mContext.startActivity(intent)
        }

        // Clic sur "favoriteContact"
        favoriteContact.setOnClickListener {
            // Marquer le contact comme favori (ici c'est juste un exemple)
            Toast.makeText(mContext, "${contact.name} est maintenant en favori", Toast.LENGTH_SHORT).show()
        }
        return itemView
//        return super.getView(position, convertView, parent)
    }


}
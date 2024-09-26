package com.example.tp1_dev_mobile

import android.app.AlertDialog
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
import com.example.tp1_dev_mobile.db.SiglDB

class ContactAdapter(
    var mContext: Context,
    var resources: Int,
    var values: List<Contact>
) : ArrayAdapter<Contact>(mContext , resources, values) {
     lateinit var favoriteContact : ImageView
     var db = SiglDB(mContext)

    // Stocke la position de l'élément cliqué
    private var selectedPosition: Int = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = values[position]

        Log.d("ContactAdapter", "getContact() called with position $position: ${contact.id} - ${contact.name}")

        val itemView = LayoutInflater.from(mContext).inflate(resources, parent, false)
        val name = itemView.findViewById<TextView>(R.id.name)
        val prenoms = itemView.findViewById<TextView>(R.id.prenoms)
        val numero = itemView.findViewById<TextView>(R.id.numero)
        val profession = itemView.findViewById<TextView>(R.id.professionShow)
        val optionDetail  = itemView.findViewById<LinearLayout>(R.id.optionContact)
        val infoContact = itemView.findViewById<ImageView>(R.id.infoContact)
        val editContact = itemView.findViewById<ImageView>(R.id.editContact)
        val favoriteContact = itemView.findViewById<ImageView>(R.id.favorite)
        val deleteContact = itemView.findViewById<ImageView>(R.id.deleteContact)

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

        // Clic sur "editContact"
        editContact.setOnClickListener {
            // Lancer une activité pour éditer le contact
            val intent = Intent(mContext, EditContact::class.java)
            intent.putExtra("contact", contact)
            mContext.startActivity(intent)
        }

        var isFavorite = false

        // Clic sur "favoriteContact"
        favoriteContact.setOnClickListener {

            if (isFavorite){
                favoriteContact.setImageResource(R.drawable.ic_heart_plus_32)
                Toast.makeText(mContext, "${contact.name} n'est plus favori", Toast.LENGTH_SHORT).show()

            }else{
                favoriteContact.setImageResource(R.drawable.ic_heart_check_32)
                Toast.makeText(mContext, "${contact.name} est maintenant en favori", Toast.LENGTH_SHORT).show()

            }

            isFavorite = !isFavorite

            }

        deleteContact.setOnClickListener {

            val builder = AlertDialog.Builder(mContext)
            builder.setTitle("Delete contact")
            builder.setMessage("Êtes-vous sûr de vouloir supprimer ${contact.name} ?")

            builder.setPositiveButton("Oui") { dialog, which ->
                db.deleteContact(contact)
                values = values.filterIndexed { index, _ -> index != position }
                notifyDataSetChanged()
            }

            builder.setNegativeButton("Non") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }

        infoContact.setOnClickListener {
            Intent(mContext, ShowContact::class.java).also {
                it.putExtra("contact", contact)
                mContext.startActivity(it)
            }
        }

        //retourne l'element avec toute ses proprietes
        return itemView
    }


}
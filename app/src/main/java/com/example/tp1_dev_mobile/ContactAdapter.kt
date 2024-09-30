package com.example.tp1_dev_mobile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
        val contactImage = itemView.findViewById<ImageView>(R.id.contactImage)

        name.text = contact.name
        prenoms.text = contact.prenoms
        numero.text = contact.numero
        profession.text = contact.profession
        favoriteContact.setImageResource(if (contact.favorite) R.drawable.ic_heart_check_32 else R.drawable.ic_heart_plus_32)

        if (contact.image.isNotEmpty()) {
            val bitmap = getBitmapFromByteArray(contact.image)
            contactImage.setImageBitmap(bitmap)
        } else {
            contactImage.setImageResource(R.drawable.logo)
        }

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

        // Clic sur "favoriteContact"
        favoriteContact.setOnClickListener {
            //lancer une activité pour ajouter ou supprimer le contact des favoris
            contact.favorite = !contact.favorite

            if (contact.favorite){
                favoriteContact.setImageResource(R.drawable.ic_heart_check_32)
                Toast.makeText(mContext, "${contact.name} a été ajouté aux favoris", Toast.LENGTH_SHORT).show()
            } else {
                favoriteContact.setImageResource(R.drawable.ic_heart_plus_32)
                Toast.makeText(mContext, "${contact.name} a été supprimé des favoris", Toast.LENGTH_SHORT).show()
            }
            db.updateContact(contact)
        }

        deleteContact.setOnClickListener {

            val builder = AlertDialog.Builder(mContext)
            builder.setTitle("Delete contact")
            builder.setMessage("Êtes-vous sûr de vouloir supprimer ${contact.name} ?")

            builder.setPositiveButton("Oui") { dialog, which ->
                db.deleteContact(contact)
                values = values.filterIndexed { index, _ -> index != position }
                Toast.makeText(mContext, "${contact.name} a été supprimé", Toast.LENGTH_SHORT).show()
                Intent(mContext, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    mContext.startActivity(it)
                }
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

    private fun getBitmapFromByteArray(image: ByteArray): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        return bitmap
    }


}
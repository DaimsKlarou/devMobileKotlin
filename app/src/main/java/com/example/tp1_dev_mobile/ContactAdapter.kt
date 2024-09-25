package com.example.tp1_dev_mobile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import com.example.tp1_dev_mobile.data.Contact

class ContactAdapter (
    var mContext: Context,
    var resources : Int,
    var values : ArrayList<Contact>
) : ArrayAdapter<Contact>(mContext , resources, values) {
     lateinit var favoriteContact : ImageView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val post = values[position]

        val itemView = LayoutInflater.from(mContext).inflate(resources, parent, false)
        val name = itemView.findViewById<TextView>(R.id.name)
        val prenoms = itemView.findViewById<TextView>(R.id.prenoms)
        val numero = itemView.findViewById<TextView>(R.id.numero)
        val profession = itemView.findViewById<TextView>(R.id.profession)
        favoriteContact  = itemView.findViewById<ImageView>(R.id.favorite)


        name.text = post.name
        prenoms.text = post.prenoms
        numero.text = post.numero
        profession.text = post.profession

        return itemView
//        return super.getView(position, convertView, parent)
    }

    private fun addFavoriteContact(){

    }

}
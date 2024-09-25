package com.example.tp1_dev_mobile.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tp1_dev_mobile.data.Contact
import com.example.tp1_dev_mobile.data.History

class SiglDB (
     mContext :Context,
     name : String = DB_NAME,
     version : Int = DB_VERSION
) : SQLiteOpenHelper (
    mContext,
    name,
    null,
    version
) {
    override fun onCreate(db: SQLiteDatabase?) {
        //creation des tables
        val createTableUser = """
            CREATE TABLE $HISTORIQUE_TABLE_NAME (
                $HISTORIQUE_ID integer PRIMARY KEY,
                $TYPE varchar(25),
                $VALUE decimal,
                $DATE date
            )
        """.trimIndent()

        val createTableContacts = """
            CREATE TABLE $CONTACT_TABLE_NAME (
                $CONTACT_ID integer PRIMARY KEY,
                $NAME varchar(25) NOT NULL,
                $PRENOM varchar(50)
                $NUMERO varchar(13) NOT NULL,
                $PROFESSION varchar(50)
            )
        """.trimIndent()

        db?.execSQL(createTableUser)
        db?.execSQL(createTableContacts)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //suppression des anciennes tables et creations des nouvelles tables
        db?.execSQL("DROP TABLE IF EXISTS $HISTORIQUE_TABLE_NAME ")
        onCreate(db)
    }

    fun addContact( contact : Contact) : Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, contact.name)
        values.put(PRENOM, contact.prenoms)
        values.put(NUMERO, contact.numero)
        values.put(PROFESSION, contact.profession)

        //insert into comtacts(id, name, prenoms, profession) values (contact.id, contact.name, contact.prenoms, contact.profession);
        val result = db.insert(CONTACT_TABLE_NAME, null, values).toInt()

        db.close()
       return result != -1
    }

    fun showContact(contact: Contact): Cursor? {
        // Ouvre la base de données en mode lecture
        val db = this.readableDatabase

        // Définir les colonnes à récupérer (par exemple "id", "name", etc.)
        val columns = arrayOf(CONTACT_ID, "name", "phone_number", "profession")

        // Définir la clause WHERE pour sélectionner le contact spécifique
        val selection = "$CONTACT_ID = ?"

        // Définir les arguments de la clause WHERE (dans ce cas, l'ID du contact)
        val selectionArgs = arrayOf(contact.id.toString())

        // Effectuer la requête SQL pour sélectionner le contact
        val cursor = db.query(
            "contacts_table",  // Nom de la table
            columns,           // Colonnes à récupérer
            selection,         // Clause WHERE
            selectionArgs,     // Arguments de la clause WHERE
            null,              // Groupement
            null,              // Filtrage
            null               // Tri
        )

        // Fermer la base de données après traitement
        cursor?.moveToFirst()

        db.close()

        return cursor // Retourne le curseur contenant les résultats
    }

    @SuppressLint("Range")
    fun getAllContacts(): List<Contact> {
        val contactList: MutableList<Contact> = ArrayList()

        // Ouvrir la base de données en mode lecture
        val db = this.readableDatabase

        // Requête SQL pour sélectionner tous les contacts
        val query = "SELECT * FROM $CONTACT_TABLE_NAME"

        // Exécuter la requête
        val cursor = db.rawQuery(query, null)

        // Vérifier si le curseur contient des résultats
        if (cursor.moveToFirst()) {
            do {
                // Récupérer les données pour chaque contact
                val id = cursor.getInt(cursor.getColumnIndex(CONTACT_ID))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val prenoms = cursor.getString(cursor.getColumnIndex("prenoms"))
                val profession = cursor.getString(cursor.getColumnIndex("profession"))
                val phone = cursor.getString(cursor.getColumnIndex("phone_number"))

                // Créer un objet Contact et l'ajouter à la liste
                val contact = Contact(id, name = name, prenoms =  prenoms , numero = phone, profession = profession)
                contactList.add(contact)

            } while (cursor.moveToNext())  // Passer au contact suivant
        }

        // Fermer le curseur et la base de données
        cursor.close()
        db.close()

        // Retourner la liste des contacts
        return contactList
    }


    companion object{
        private val DB_NAME = "sigl_db"
        private val DB_VERSION = 1
        private val HISTORIQUE_TABLE_NAME = "historique"
        private val HISTORIQUE_ID = "id"
        private val TYPE = "type"
        private val VALUE = "value"
        private val DATE = "date"

        //variable de contacts
        private val CONTACT_TABLE_NAME = "contacts"
        private val CONTACT_ID = "id"
        private val NAME = "name"
        private val PRENOM = "prenoms"
        private val NUMERO = "numero"
        private val PROFESSION = "profession"
    }

}
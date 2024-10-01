package com.example.tp1_dev_mobile.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
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
                $CONTACT_NAME varchar(25) NOT NULL,
                $CONTACT_PRENOMS varchar(50),
                $CONTACT_NUMERO varchar(13) NOT NULL,
                $CONTACT_PROFESSION varchar(50),
                $CONTACT_IMAGE blob,
                $CONTACT_FAVORITE boolean
            )
        """.trimIndent()

        db?.execSQL(createTableUser)
        db?.execSQL(createTableContacts)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //suppression des anciennes tables et creations des nouvelles tables
        db?.execSQL("DROP TABLE IF EXISTS $HISTORIQUE_TABLE_NAME ")
        db?.execSQL("DROP TABLE IF EXISTS $CONTACT_TABLE_NAME ")
        onCreate(db)
    }

    fun showContact(contact: Contact): Cursor? {
        // Ouvre la base de données en mode lecture
        val db = this.readableDatabase

        // Définir les colonnes à récupérer (par exemple "id", "name", etc.)
        val columns = arrayOf(CONTACT_ID, "name","prenoms",  "numero", "profession")

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
        Log.d("SiglDB", "Cursor: $cursor")

        // Retourne le curseur contenant les résultats
        return cursor
    }

    @SuppressLint("Range")
    fun getAllContacts(): MutableList<Contact> {
        val contactList: MutableList<Contact> = ArrayList()

        // Ouvrir la base de données en mode lecture
        val db = this.readableDatabase

        Log.d("SiglDB", "Database opened successfully")
        // Requête SQL pour sélectionner tous les contacts
        val query = "SELECT * FROM $CONTACT_TABLE_NAME ORDER BY $CONTACT_NAME ASC"

        Log.d("SiglDB", "Query executed: $query")
        // Exécuter la requête
        val cursor = db.rawQuery(query, null)

        // Vérifier si le curseur contient des résultats
        if (cursor.moveToFirst()) {
            Log.d("SiglDB", "Cursor has results")
            do {
                // Récupérer les données pour chaque contact
                val id = cursor.getInt(cursor.getColumnIndex(CONTACT_ID))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val prenoms = cursor.getString(cursor.getColumnIndex("prenoms"))
                val profession = cursor.getString(cursor.getColumnIndex("profession"))
                val phone = cursor.getString(cursor.getColumnIndex("numero"))
                val image = cursor.getBlob(cursor.getColumnIndex("image"))
                val favorite = cursor.getInt(cursor.getColumnIndex("favorite"))

                // Créer un objet Contact et l'ajouter à la liste
                val contact = Contact( name = name, prenoms =  prenoms , numero = phone, profession = profession, id = id, image = image, favorite = favorite == 1)
                contactList.add(contact)

            } while (cursor.moveToNext())  // Passer au contact suivant
        } else {
            Log.d("SiglDB", "Cursor is empty")
        }

        // Fermer le curseur et la base de données
        cursor.close()
        db.close()

        // Retourner la liste des contacts
        return contactList
    }

    fun addContact( contact : Contact) : Boolean{

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CONTACT_NAME, contact.name)
        values.put(CONTACT_PRENOMS, contact.prenoms)
        values.put(CONTACT_NUMERO, contact.numero)
        values.put(CONTACT_PROFESSION, contact.profession)
        values.put(CONTACT_IMAGE, contact.image)
        values.put(CONTACT_FAVORITE, contact.favorite)

        //insert into comtacts(id, name, prenoms, profession) values (contact.id, contact.name, contact.prenoms, contact.profession);
        val result = db.insert(CONTACT_TABLE_NAME, null, values).toInt()

        Log.d("SiglDB", "Adding contact with ID: ${contact.id}")

        db.close()
        return result > 0
    }

    fun updateContact(contact: Contact): Boolean {
        // Ouvre la base de données en mode écriture
        val db = this.writableDatabase

        // Crée un ContentValues pour stocker les nouvelles données
        val values = ContentValues().apply {
            put("name", contact.name)
            put("prenoms", contact.prenoms)
            put("numero", contact.numero)
            put("profession", contact.profession)
            put("image", contact.image)
            put("favorite", contact.favorite)
        }

        // Condition pour mettre à jour le contact basé sur l'ID
        val selection = "id = ?"
        val selectionArgs = arrayOf(contact.id.toString())

        // Essaye de mettre à jour le contact
        val result = db.update("contacts", values, selection, selectionArgs)

        Log.d("SiglDB", "Updating contact with ID: ${contact.id}")

        // Ferme la base de données
        db.close()

        // Vérifie si la mise à jour a réussi
        return result > 0  // Renvoie true si le contact a été mis à jour
    }

    fun deleteContact(contact: Contact) : Boolean{
        val db = this.writableDatabase

        val selection = "id = ?"
        val selectionArgs = arrayOf(contact.id.toString())

        val result = db.delete("contacts", selection, selectionArgs)
        Log.d("SiglDB", "deleted contact with ID: ${contact.id}")

        db.close()

        return result > 0
    }

    fun clearContactsTable(): Boolean {
        val db = this.writableDatabase
         val result = db.delete(CONTACT_TABLE_NAME, null,null)
        db.close()

        Log.d("SiglDB", "Clearing contacts table with ID: $result")
        return result > 0
    }


    companion object{
        private val DB_NAME = "sigl_db"
        private val DB_VERSION = 2
        private val HISTORIQUE_TABLE_NAME = "historique"
        private val HISTORIQUE_ID = "id"
        private val TYPE = "type"
        private val VALUE = "value"
        private val DATE = "date"

        //variable de contacts
        private val CONTACT_TABLE_NAME = "contacts"
        private val CONTACT_ID = "id"
        private val CONTACT_NAME = "name"
        private val CONTACT_PRENOMS = "prenoms"
        private val CONTACT_NUMERO = "numero"
        private val CONTACT_PROFESSION = "profession"
        private val CONTACT_IMAGE = "image"
        private val CONTACT_FAVORITE = "favorite"
    }

}
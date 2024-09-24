package com.example.tp1_dev_mobile.db

import android.content.Context
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
            CREATE TABLE $CONTACT (
                $CONTACT_ID integer PRIMARY KEY,
                $NAME varchar(25),
                $PRENOM varchar(50)
                $NUMERO varchar(13),
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
       return false
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
        private val CONTACT = "contacts"
        private val CONTACT_ID = "id"
        private val NAME = "name"
        private val PRENOM = "prenoms"
        private val NUMERO = "numero"
        private val PROFESSION = "profession"
    }

}
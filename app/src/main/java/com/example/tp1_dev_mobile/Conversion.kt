package com.example.tp1_dev_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class Conversion : ComponentActivity() {
    private lateinit var zonetext: EditText
    private lateinit var btn_conversion: Button
    private lateinit var btn_retour: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.conversion)

    }

    private fun ResultView(result : Float){

    }

}
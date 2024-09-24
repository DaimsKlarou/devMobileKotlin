package com.example.tp1_dev_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class Conversion : ComponentActivity() {
    private lateinit var zonetext: EditText
    private lateinit var btn_conversion: Button
    private lateinit var radio_cfa_euro: RadioButton
    private lateinit var radio_euro_cfa: RadioButton
    private var devis : String = ""
    private var montant: Float = 0.0f
    private var result: Float = 0.0f

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.conversion)

        //chargement de l'image de retour
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.ic_arrow_32)

        zonetext = findViewById(R.id.zoneTexte)
        btn_conversion = findViewById(R.id.btn_conversion)
        radio_euro_cfa = findViewById(R.id.radio_euro_cfa)
        radio_cfa_euro = findViewById(R.id.radio_cfa_euro)

        btnEvent()
    }

    private fun ValidOperation(): Boolean {
        return radio_cfa_euro.isChecked || radio_euro_cfa.isChecked
    }

    private fun getZoneText(): String {
        return zonetext.text.toString()
    }

    private fun ResultView(result : Float, devis : String, phrase: String){
        val intentToHomeActivity = Intent(this, ConversionResult::class.java)
        intentToHomeActivity.putExtra("resultat", result.toString())
        intentToHomeActivity.putExtra("devis", devis)
        intentToHomeActivity.putExtra("phrase", phrase)
        startActivity(intentToHomeActivity)
    }

    private fun ConversionCfaEuro(cost: String) : Float {
        var resultat = 0.0f
        try {
            montant = cost.toFloat()
            resultat = montant * 0.0015f
            return resultat
        } catch (e: NumberFormatException){
            zonetext.text.clear()
            Toast.makeText(this, "La valeur du montant  doit être des chiffres ", Toast.LENGTH_LONG).show()
            return 0.0f
        }
    }

    private fun ConversionEuroCfa(cost : String) : Float {
        var resultat = 0.0f
        try {
            montant = cost.toFloat()
            resultat = montant * 656
            return resultat
        } catch (e: NumberFormatException){
            zonetext.text.clear()
            Toast.makeText(this, "La valeur du montant  doit être des chiffres ", Toast.LENGTH_LONG).show()
            return 0.0f
        }
    }

    private fun onValidateButtonClick() {
        val montant = getZoneText()

        if (montant.trim().isNotEmpty()){
            if (ValidOperation()) {
                if(radio_cfa_euro.isChecked){
                    result = ConversionCfaEuro(montant)
                    devis = "€"
                    ResultView(result, devis, "Francs CFA -> Euro")
                } else{
                    result = ConversionEuroCfa(montant)
                    devis = "XOF"
                    ResultView(result, devis, "Euro -> Francs CFA")
                }
            } else {
                Toast.makeText(this, "Veuillez selectionner une methode de conversion ", Toast.LENGTH_LONG).show()
            }
        } else{
            Toast.makeText(this, "Veuillez entrer un montant a convertir", Toast.LENGTH_LONG).show()
        }

    }

    private fun btnEvent(){
        btn_conversion.setOnClickListener {
            onValidateButtonClick()
        }
    }

    fun btnRetour(view: View) {
        setResult(RESULT_OK)
        finish()
    }

}
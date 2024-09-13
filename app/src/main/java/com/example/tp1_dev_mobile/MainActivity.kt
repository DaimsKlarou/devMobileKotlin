package com.example.tp1_dev_mobile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.ResourcesCompat
import com.example.tp1_dev_mobile.ui.theme.Tp1_dev_mobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //defintion de variable utiliser dans ma view
        val btnValider = findViewById<Button>(R.id.btnValider)
        val btnAnnuler  = findViewById<Button>(R.id.btnAnnuler)
        val prenoms = findViewById<EditText>(R.id.Prenoms)
        val name = findViewById<EditText>(R.id.Name)

        btnValider.setOnClickListener{
            println("Hello World!!")
        }

//        setContent {
//            Tp1_dev_mobileTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Tp1_dev_mobileTheme {
        Greeting("Android")
    }
}
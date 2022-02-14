package com.example.capitalesafricajava

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.capitalesafricajava.R

class MainActivity : AppCompatActivity() {
    var juego: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val comenzar = findViewById<View>(R.id.botonComenzar) as Button
        val dbHandler = com.example.capitalesafricajava.DbHandler(this@MainActivity)
        dbHandler.deleteAll()
        comenzar.setOnClickListener {
            juego = Intent(this@MainActivity, Juego::class.java)
            startActivity(juego)
        }
    }
}
package com.example.capitalesafricajava

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Juego : AppCompatActivity() {
    var capitalesPaises = LinkedHashMap<String, String>()
    var paises: ArrayList<String>? = null
    var capitales: ArrayList<String>? = null
    var paisesNoJugados = ArrayList<String>()
    var puntuacion = 0
    var cantidad = 0
    var respuesta: String? = null
    var dbHandler = DbHandler(this@Juego)
    var control = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego)
        llenaMapa()
        ponePais()
        val jugar = findViewById<View>(R.id.btnJugar) as ImageButton
        val grupo = findViewById<View>(R.id.radioGroup) as RadioGroup
        val debug = findViewById<View>(R.id.textView3) as TextView
        val pais = findViewById<View>(R.id.txtPais) as TextView
        jugar.setOnClickListener {
            val checkedRadioButtonId = grupo.checkedRadioButtonId
            val seleccionado = findViewById<RadioButton>(checkedRadioButtonId)
            if (checkedRadioButtonId == -1) {
                // No item selected
            } else {
                cantidad++
                if (seleccionado.text == respuesta) {
                    puntuacion++
                    dbHandler.insertUserDetails(pais.text as String, respuesta, "bien")
                    try {
//                        sacaToast("Correcto!!", "GREEN")
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    if (cantidad <= 9) {
                        ponePais()
                    } else {
                        val resultado = Intent(this@Juego, Resultado::class.java)
                        startActivity(resultado)
                    }
                } else {
                    dbHandler.insertUserDetails(pais.text as String, respuesta, "mla")
                    try {
//                        sacaToast("Error", "RED")
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    if (cantidad <= 9) {
                        ponePais()
                    } else {
                        val resultado = Intent(this@Juego, Resultado::class.java)
                        startActivity(resultado)
                    }
                }
                grupo.clearCheck()
            }
        }
    }

    fun llenaMapa() {
        capitalesPaises["Marruecos"] = "Rabat"
        capitalesPaises["Egipto"] = "El Cairo"
        capitalesPaises["Mali"] = "Bamako"
        capitalesPaises["Niger"] = "Niamey"
        capitalesPaises["Angola"] = "Luanda"
        capitalesPaises["Bostwana"] = "Gaborone"
        capitalesPaises["Namibia"] = "Windhoek"
        capitalesPaises["Etiopía"] = "Addis Ababa"
        capitalesPaises["Nigeria"] = "Abuja"
        capitalesPaises["Chad"] = "N'Djamena"
        paises = ArrayList(capitalesPaises.keys)
        paisesNoJugados = ArrayList(capitalesPaises.keys)
        capitales = ArrayList(capitalesPaises.values)
    }

    fun ponePais() {
        var p = ""
        val radio1: String
        var radio2: String
        var radio3: String
        var radio4: String?
        val pais = findViewById<View>(R.id.txtPais) as TextView
        val r1 = findViewById<View>(R.id.radio1) as RadioButton
        val r2 = findViewById<View>(R.id.radio2) as RadioButton
        val r3 = findViewById<View>(R.id.radio3) as RadioButton
        val r4 = findViewById<View>(R.id.radio4) as RadioButton
        val aleat = (Math.random() * paisesNoJugados.size).toInt()
        p = paisesNoJugados[aleat]
        paisesNoJugados.removeAt(aleat)
        respuesta = capitalesPaises[p]
        pais.text = p
        radio1 = sacaCapital()
        r1.text = radio1
        do {
            radio2 = sacaCapital()
        } while (radio2 == radio1)
        r2.text = radio2
        do {
            radio3 = sacaCapital()
        } while ((radio2 == radio3) or (radio1 == radio3))
        r3.text = radio3
        do {
            radio4 =
                if (radio2 != capitalesPaises[p] && radio1 != capitalesPaises[p] && radio3 != capitalesPaises[p]
                ) {
                    capitalesPaises[p]
                } else {
                    sacaCapital()
                }
        } while ((radio2 == radio4) or (radio1 == radio4) or (radio3 == radio4))
        r4.text = radio4
    }

    fun sacaCapital(): String {
        val aleat = (Math.random() * 9).toInt()
        return capitales!![aleat]
    }

    // Función que saca los toast
    @Throws(InterruptedException::class)
    private fun sacaToast(texto: String, color: String) {
        val toast = Toast.makeText(
            applicationContext,
            texto,
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.CENTER_VERTICAL, 0, 0)
        toast.view!!.setBackgroundColor(Color.parseColor(color))
        toast.show()
    }
}
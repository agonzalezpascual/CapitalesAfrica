package com.example.capitalesafricajava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar

class Resultado : AppCompatActivity() {
    var aciertos = 0
    lateinit var estado: Array<Int?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultado)
        val listaResul = findViewById<View>(R.id.listaResultados) as ListView
        val dbHandler = DbHandler(this)
        val listaPais = dbHandler.GetUsers()
        estado = recogeEstado()
        sacaSnack(findViewById(R.id.resultado),
            "Total 10: " + aciertos + " Aciertos/" + (10 - aciertos) + " Errores",
            5000)
        val customCountryList = CustomCountryList(this, recogePaises(), recogeCapitales(), estado)
        listaResul.adapter = customCountryList
        //        ListAdapter adapter = new SimpleAdapter(this, listaPais, R.layout.row_layout,new String[]{"pais","capital","estado"}, new int[]{R.id.textViewCountry, R.id.textViewCapital, R.id.imageViewFlag});
//        listaResul.setAdapter(adapter);
    }

    fun recogePaises(): Array<String?> {
        val dbHandler = DbHandler(this)
        val listaPais = dbHandler.GetUsers()
        val paises = arrayOfNulls<String>(10)
        for (i in listaPais.indices) {
            paises[i] = listaPais[i]["pais"]
        }
        return paises
    }

    fun recogeCapitales(): Array<String?> {
        val dbHandler = DbHandler(this)
        val listaPais = dbHandler.GetUsers()
        val capitales = arrayOfNulls<String>(10)
        for (i in listaPais.indices) {
            capitales[i] = listaPais[i]["capital"]
        }
        return capitales
    }

    fun recogeEstado(): Array<Int?> {
        val dbHandler = DbHandler(this)
        val listaPais = dbHandler.GetUsers()
        val estados = arrayOfNulls<Int>(10)
        for (i in listaPais.indices) {
            if (listaPais[i]["estado"] == "bien") {
                estados[i] = R.drawable.bien
                aciertos++
            } else {
                estados[i] = R.drawable.mla
            }
        }
        return estados
    }

    fun sacaSnack(view: View?, stringId: String?, duration: Int) {
        val mySnackbar = Snackbar.make(view!!, stringId!!, duration)
        mySnackbar.show()
    }
}
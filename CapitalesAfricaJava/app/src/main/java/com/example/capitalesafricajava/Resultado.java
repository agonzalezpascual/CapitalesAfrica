package com.example.capitalesafricajava;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Resultado extends AppCompatActivity {

    int aciertos = 0;
    Integer[] estado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado);
        ListView listaResul = (ListView) findViewById(R.id.listaResultados);

        DbHandler dbHandler = new DbHandler(this);
        ArrayList<HashMap<String, String>> listaPais = dbHandler.GetUsers();
        estado = recogeEstado();
        sacaSnack(findViewById(R.id.resultado), "Total 10: " + aciertos + " Aciertos/" + (10 - aciertos) + " Errores", 5000);
        CustomCountryList customCountryList = new CustomCountryList(this, recogePaises(), recogeCapitales(), estado);
        listaResul.setAdapter(customCountryList);
//        ListAdapter adapter = new SimpleAdapter(this, listaPais, R.layout.row_layout,new String[]{"pais","capital","estado"}, new int[]{R.id.textViewCountry, R.id.textViewCapital, R.id.imageViewFlag});
//        listaResul.setAdapter(adapter);


    }

    public String[] recogePaises() {
        DbHandler dbHandler = new DbHandler(this);
        ArrayList<HashMap<String, String>> listaPais = dbHandler.GetUsers();
        String[] paises = new String[10];
        for (int i = 0; i < listaPais.size(); i++) {
            paises[i] = listaPais.get(i).get("pais");
        }
        return paises;
    }

    public String[] recogeCapitales() {
        DbHandler dbHandler = new DbHandler(this);
        ArrayList<HashMap<String, String>> listaPais = dbHandler.GetUsers();
        String[] capitales = new String[10];
        for (int i = 0; i < listaPais.size(); i++) {
            capitales[i] = listaPais.get(i).get("capital");
        }
        return capitales;

    }

    public Integer[] recogeEstado() {
        DbHandler dbHandler = new DbHandler(this);
        ArrayList<HashMap<String, String>> listaPais = dbHandler.GetUsers();
        Integer[] estados = new Integer[10];
        for (int i = 0; i < listaPais.size(); i++) {
            if (listaPais.get(i).get("estado").equals("bien")) {
                estados[i] = R.drawable.bien;
                aciertos++;
            } else {
                estados[i] = R.drawable.mla;
            }

        }
        return estados;

    }

    public void sacaSnack(View view, String stringId, int duration) {
        Snackbar mySnackbar = Snackbar.make(view, stringId, duration);
        mySnackbar.show();

    }
}

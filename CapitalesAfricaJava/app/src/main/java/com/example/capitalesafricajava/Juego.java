package com.example.capitalesafricajava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Juego extends AppCompatActivity {

    LinkedHashMap<String, String> capitalesPaises = new LinkedHashMap<>();
    ArrayList<String> paises;
    ArrayList<String> capitales;
    ArrayList<String> paisesNoJugados = new ArrayList<>();
    int puntuacion = 0;
    int cantidad = 0;
    String respuesta;
    DbHandler dbHandler = new DbHandler(Juego.this);
    boolean control = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        llenaMapa();
        ponePais();
        ImageButton jugar = (ImageButton) findViewById(R.id.btnJugar);
        RadioGroup grupo = (RadioGroup) findViewById(R.id.radioGroup);
        TextView debug = (TextView) findViewById(R.id.textView3);
        TextView pais = (TextView) findViewById(R.id.txtPais);

        jugar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int checkedRadioButtonId = grupo.getCheckedRadioButtonId();
                RadioButton seleccionado = findViewById(checkedRadioButtonId);
                if (checkedRadioButtonId == -1) {
                    // No item selected
                }
//                else if (cantidad == 9) {

//                }
                else {
                    cantidad++;
                    if (seleccionado.getText().equals(respuesta)) {
                        puntuacion++;
                        dbHandler.insertUserDetails((String) pais.getText(), respuesta, "bien");
                        try {
                            sacaToast("Correcto!!", "GREEN");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (!(cantidad > 9)) {
                            ponePais();
                        } else {
                            Intent resultado = new Intent(Juego.this, Resultado.class);
                            startActivity(resultado);
                        }

                    } else {
                        dbHandler.insertUserDetails((String) pais.getText(), respuesta, "mla");
                        try {
                            sacaToast("Error", "RED");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (!(cantidad > 9)) {
                            ponePais();
                        } else {
                            Intent resultado = new Intent(Juego.this, Resultado.class);
                            startActivity(resultado);
                        }
                    }
                    grupo.clearCheck();


                }
            }

        });
    }


    public void llenaMapa() {
        capitalesPaises.put("Marruecos", "Rabat");
        capitalesPaises.put("Egipto", "El Cairo");
        capitalesPaises.put("Mali", "Bamako");
        capitalesPaises.put("Niger", "Niamey");
        capitalesPaises.put("Angola", "Luanda");
        capitalesPaises.put("Bostwana", "Gaborone");
        capitalesPaises.put("Namibia", "Windhoek");
        capitalesPaises.put("Etiopía", "Addis Ababa");
        capitalesPaises.put("Nigeria", "Abuja");
        capitalesPaises.put("Chad", "N'Djamena");
        paises = new ArrayList<String>(capitalesPaises.keySet());
        paisesNoJugados = new ArrayList<String>(capitalesPaises.keySet());
        capitales = new ArrayList<String>(capitalesPaises.values());
    }

    public void ponePais() {

        String p = "";
        String radio1;
        String radio2;
        String radio3;
        String radio4;
        TextView pais = (TextView) findViewById(R.id.txtPais);
        RadioButton r1 = (RadioButton) findViewById(R.id.radio1);
        RadioButton r2 = (RadioButton) findViewById(R.id.radio2);
        RadioButton r3 = (RadioButton) findViewById(R.id.radio3);
        RadioButton r4 = (RadioButton) findViewById(R.id.radio4);
        int aleat = (int) (Math.random() * paisesNoJugados.size());
        p = paisesNoJugados.get(aleat);
        paisesNoJugados.remove(aleat);
        respuesta = capitalesPaises.get(p);
        pais.setText(p);
        radio1 = sacaCapital();
        r1.setText(radio1);
        do {
            radio2 = sacaCapital();
        } while (radio2.equals(radio1));
        r2.setText(radio2);
        do {
            radio3 = sacaCapital();
        } while (radio2.equals(radio3) | radio1.equals(radio3));

        r3.setText(radio3);

        do {
            if (!radio2.equals(capitalesPaises.get(p)) && !radio1.equals(capitalesPaises.get(p)) && !radio3.equals(capitalesPaises.get(p))) {
                radio4 = capitalesPaises.get(p);
            } else {
                radio4 = sacaCapital();
            }
        } while ((radio2.equals(radio4) | radio1.equals(radio4) | radio3.equals(radio4)));
        r4.setText(radio4);
    }

    public String sacaCapital() {
        int aleat = (int) (Math.random() * 9);
        return capitales.get(aleat);
    }

    // Función que saca los toast
    private void sacaToast(String texto, String color) throws InterruptedException {
        Toast toast = Toast.makeText(
                getApplicationContext(),
                texto,
                Toast.LENGTH_SHORT
        );
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.getView().setBackgroundColor(Color.parseColor(color));
        toast.show();

    }


}

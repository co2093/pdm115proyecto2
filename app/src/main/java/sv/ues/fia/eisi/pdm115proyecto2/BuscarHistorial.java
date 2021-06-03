package sv.ues.fia.eisi.pdm115proyecto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageButton;
import android.app.AlertDialog;
import android.speech.tts.TextToSpeech;

import java.util.List;
import java.util.Locale;

public class BuscarHistorial extends AppCompatActivity {

    EditText editTextBusqueda, titulo, contenido;
    Button busqueda, generar, regresar, escuchar;
    ControlDB helper;
    TextToSpeech t1;
    List<Contenidos> busquedaL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_historial);

        busqueda = findViewById(R.id.btnBuscar);
        generar = findViewById(R.id.btnGenerarDos);
        regresar = findViewById(R.id.btnRegresar);
        editTextBusqueda = findViewById(R.id.editTextBusqueda);
        titulo = findViewById(R.id.editTextTitle);
        contenido = findViewById(R.id.editTextContent);
        escuchar = findViewById(R.id.btnEscuchar);
        helper = new ControlDB(BuscarHistorial.this);






        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
            }
        });

        escuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contenido.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No hay texto que leer", Toast.LENGTH_SHORT).show();
                } else{
                    String toSpeak = contenido.getText().toString();

                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            t1.setLanguage(new Locale("spa"));
                            //  t1.setSpeechRate(1.0f);
                            t1.speak(toSpeak, TextToSpeech.QUEUE_ADD, null);
                            Toast.makeText(getApplicationContext(), "Playing", Toast.LENGTH_SHORT).show();
                        } else if (status == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(getApplicationContext(), "No soportado", Toast.LENGTH_SHORT).show();
                        } else if (status == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), "Descargar idioma Espanol", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
            }
        });

        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(titulo.getText().toString().isEmpty() || contenido.getText().toString().isEmpty()){
                    Toast.makeText(BuscarHistorial.this, "Realice una busqueda primero", Toast.LENGTH_SHORT).show();
                }else{

                    Intent intent = new Intent(v.getContext(), PdfActivity.class);
                    intent.putExtra("titulo", titulo.getText().toString());
                    intent.putExtra("contenido", contenido.getText().toString());
                    startActivityForResult(intent,0);
                }

            }
        });



        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(busqueda.getText().toString().isEmpty()){
                    Toast.makeText(BuscarHistorial.this, "Ingrese el titulo a buscar", Toast.LENGTH_SHORT).show();
                }else{
                    busquedaL = helper.consulta(editTextBusqueda.getText().toString());

                    if(busquedaL.isEmpty()){
                        Toast.makeText(BuscarHistorial.this, "No hay resultados para " + editTextBusqueda.getText().toString(), Toast.LENGTH_SHORT).show();

                    }else{
                        Contenidos contenidos = busquedaL.get(0);
                        titulo.setText(contenidos.getNombre());
                        contenido.setText(contenidos.getContenido());
                    }



                }
            }
        });


    }


}
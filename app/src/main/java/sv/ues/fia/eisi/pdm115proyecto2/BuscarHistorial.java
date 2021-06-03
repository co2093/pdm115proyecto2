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

import java.util.List;

public class BuscarHistorial extends AppCompatActivity {

    EditText editTextBusqueda, titulo, contenido;
    Button busqueda, generar, regresar;
    ControlDB helper;
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
        helper = new ControlDB(BuscarHistorial.this);



        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
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
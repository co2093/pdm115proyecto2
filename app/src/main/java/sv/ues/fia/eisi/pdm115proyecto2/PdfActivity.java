package sv.ues.fia.eisi.pdm115proyecto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfActivity extends AppCompatActivity {

    Button btnGenerar, btnInicio;
    EditText contentPdf, titulo;
    ControlDB helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        btnGenerar = findViewById(R.id.btnGenerar);
        btnInicio = findViewById(R.id.btnRegresar);
        contentPdf = findViewById(R.id.editTextPdfContent);
        titulo = findViewById(R.id.editTextTituloPDF);
        helper = new ControlDB(this);

        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF();
            }
        });

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


    }

    private void createPDF(){

        if(contentPdf.getText().toString().isEmpty() || titulo.getText().toString().isEmpty()){
            Toast.makeText(this, "Complete los campos del documentos", Toast.LENGTH_SHORT).show();
        }else{

            String contenidoPdf = contentPdf.getText().toString();

            PdfDocument myPdfDocument = new PdfDocument();
            Paint myPaint = new Paint();

            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(250,400,1).create();

            PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);

            Canvas canvas = myPage1.getCanvas();

            canvas.drawText(contenidoPdf,40,50, myPaint);
            myPdfDocument.finishPage(myPage1);

            // File file = new File(Environment.getExternalStorageDirectory(), "/myPdf.pdf");

            String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            File file= new File(this.getExternalFilesDir("/"), titulo.getText().toString()+currentDateandTime+".pdf");


            String title = titulo.getText().toString();
            String content = contentPdf.getText().toString();

            Contenidos contenidos = new Contenidos();
            contenidos.setNombre(title);
            contenidos.setContenido(content);
            helper.abrir();
            helper.insertar(contenidos);
            helper.cerrar();

            try {
                myPdfDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(this, "PDF generado", Toast.LENGTH_SHORT).show();
            }catch (IOException e ){
                e.printStackTrace();
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

            myPdfDocument.close();

        }



    }
}
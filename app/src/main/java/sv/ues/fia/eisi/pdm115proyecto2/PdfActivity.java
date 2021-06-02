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

public class PdfActivity extends AppCompatActivity {

    Button btnGenerar, btnInicio;
    EditText contentPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        btnGenerar = findViewById(R.id.btnGenerar);
        btnInicio = findViewById(R.id.btnRegresar);
        contentPdf = findViewById(R.id.editTextPdfContent);

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

        if(contentPdf.getText().toString().isEmpty()){
            Toast.makeText(this, "No hay texto para generar PDF", Toast.LENGTH_SHORT).show();
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

            File file= new File(this.getExternalFilesDir("/"), "prueba.pdf");


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
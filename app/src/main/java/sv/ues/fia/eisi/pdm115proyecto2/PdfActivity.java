package sv.ues.fia.eisi.pdm115proyecto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfActivity extends AppCompatActivity {

    Button btnInicio;
    Button mail, btnGenerar;
    EditText contentPdf, titulo;
    ControlDB helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        btnGenerar = findViewById(R.id.btnGenerar);
        btnInicio = findViewById(R.id.btnAtras);
        mail = findViewById(R.id.btnEmail);
        contentPdf = findViewById(R.id.editTextPdfContent);
        titulo = findViewById(R.id.editTextTituloPDF);
        helper = new ControlDB(this);


        Intent i = getIntent();
        String ttl = i.getStringExtra("titulo");
        String ctn = i.getStringExtra("contenido");

            titulo.setText(ttl);
            contentPdf.setText(ctn);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contentPdf.getText().toString().isEmpty() || titulo.getText().toString().isEmpty()){
                    Toast.makeText(PdfActivity.this, "Complete los campos del documentos", Toast.LENGTH_SHORT).show();
                }else {
                    Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                    selectorIntent.setData(Uri.parse("mailto:"));

                    final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, titulo.getText().toString());
                    emailIntent.putExtra(Intent.EXTRA_TEXT, contentPdf.getText().toString());
                    emailIntent.setSelector(selectorIntent);

                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            }
        });



        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPdf2();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


    }

/**
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
    **/

    public void createPdf2() throws FileNotFoundException {


        if(contentPdf.getText().toString().isEmpty() || titulo.getText().toString().isEmpty()){
            Toast.makeText(this, "Complete los campos del documentos", Toast.LENGTH_SHORT).show();
        }else {

            String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String contenidoPdf = contentPdf.getText().toString();

            String title = titulo.getText().toString();
            String content = contentPdf.getText().toString();

            Contenidos contenidos = new Contenidos();
            contenidos.setNombre(title);
            contenidos.setContenido(content);
            helper.abrir();
            helper.insertar(contenidos);
            helper.cerrar();

           String pdfPath = "/storage/emulated/0/Download";

          // File file = new File(pdfPath, titulo.getText().toString()+ currentDateandTime+".pdf");
            File file= new File(this.getExternalFilesDir("/"), titulo.getText().toString()+currentDateandTime+".pdf");
           // OutputStream outputStream = new FileOutputStream(file);

            PdfWriter pdfWriter = new PdfWriter(file);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument;
            pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            Paragraph paragraph2 = new Paragraph(title);
            document.add(paragraph2.setBold().setFontSize(16));
            Paragraph paragraph = new Paragraph(contenidoPdf);

            document.add(paragraph.setFontSize(14));
            document.close();
            Toast.makeText(PdfActivity.this, "Documento " + title + " creado.", Toast.LENGTH_SHORT).show();


        }
    }
}
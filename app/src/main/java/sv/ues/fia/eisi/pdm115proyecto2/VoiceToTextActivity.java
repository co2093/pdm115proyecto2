package sv.ues.fia.eisi.pdm115proyecto2;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceToTextActivity extends AppCompatActivity {

    EditText mTextTv;
    ImageButton mVoiceBtn;
    Button regresar, generar;
    private  static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_to_text);

        mTextTv = findViewById(R.id.textTv);
        mVoiceBtn = findViewById(R.id.voiceBtn);
        regresar = findViewById(R.id.inicio_voice);
        generar = findViewById(R.id.btnGenerateFromVoice);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
            }
        });

        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTextTv.getText().toString().isEmpty()){
                    Toast.makeText(VoiceToTextActivity.this, "Debe hablar para generar PDF", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(v.getContext(), PdfActivity.class);
                    intent.putExtra("contenido", mTextTv.getText().toString());
                    startActivityForResult(intent,0);
                }

            }
        });

        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });


    }

    private void speak() {
        // mostrar el diálogo de voz a texto
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga algo ahora");

        //iniciar
        try {
            //captura el error
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode == RESULT_OK && null!=data){
                    //OBTENER ARREGLO DE TEXTO DE VOZ
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTextTv.setText(result.get(0));
                }
                break;
            }
        }
    }
}
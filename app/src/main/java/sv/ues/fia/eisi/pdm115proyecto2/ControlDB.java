package sv.ues.fia.eisi.pdm115proyecto2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.audiofx.DynamicsProcessing;
import android.provider.ContactsContract;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ControlDB {

    private static final String [] camposContenido = new String[] {"id, contenido, nombre"};



    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHelper DBHelper;

    public ControlDB(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String BASE_DATOS = "proyecto2.s3db";
        private static final int version = 1;

        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {


                db.execSQL("CREATE TABLE contador(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre VARCHAR (128), contador INTEGER);");
                db.execSQL("CREATE TABLE contenidos(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, contenido VARCHAR (1024), nombre VARCHAR (128));");

                db.execSQL("CREATE TRIGGER actualizar_contador\n" +
                        "AFTER INSERT\n" +
                        "ON contenidos \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN \n" +
                        "        UPDATE contador SET contador = contador+1 WHERE contador.id == 1;\n" +
                        "END");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void abrir() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return;
    }

    public void cerrar() {
        DBHelper.close();
    }

    public String insertar(Contenidos contenidos){
            abrir();
            ContentValues contentValues = new ContentValues();
            contentValues.put("contenido", contenidos.getContenido());
            contentValues.put("nombre", contenidos.getNombre());

            db.insert("contenidos", null, contentValues);
            cerrar();

            return "Guardado";
    }

    public String insertar(Contador contador){
        abrir();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contador", contador.getContador());
        contentValues.put("nombre", contador.getNombreC());

        db.insert("contador", null, contentValues);
        cerrar();

        return "Guardado";
    }


    public List<Contenidos> consulta(String titulo){
        List<Contenidos> lista = new ArrayList<>();

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        String[] carnetd = {titulo};

        Cursor cursor = db.query("contenidos", camposContenido, "nombre = ?", carnetd, null,null,null);

        if(cursor.moveToFirst()){
            do {
                String contenido = cursor.getString(1);
                String nombre = cursor.getString(2);

                Contenidos contenidos =  new Contenidos(contenido, nombre);
                lista.add(contenidos);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }


    public String llenar(){
        abrir();

        String queryString = "SELECT * FROM contenidos";

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){

        }else{

            Contenidos contenidos = new Contenidos();
            contenidos.setContenido("Pruebas para generar pdf");
            contenidos.setNombre("Test");
            insertar(contenidos);
        }

        String queryString2 = "SELECT * FROM contador";

        Cursor cursor2 = db.rawQuery(queryString2, null);
        if(cursor2.moveToFirst()){

        }else{

            Contador contador = new Contador();
            contador.setContador(0);
            contador.setNombreC("PDF");
            insertar(contador);
        }


        cerrar();
        return "OK";
    }
}
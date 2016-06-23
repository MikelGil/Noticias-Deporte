package com.ertzil.deportistas;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mikel Gil on 18/03/2016.
 *
 *  Clase que se usa para hacer todo lo relacionado con la base de datos de noticias
 *
 *  Incluye:
 *      -   Crear o Inicializar la base de datos
 *      -   Creacion de la tabla noticiasDeporte
 *      -   Listar todas las noticias
 *      -   Borrar todas las noticias
 *
 *      -   Listar una noticia
 *      -   Borrar una noticia
 *      -   Insertar una nueva noticia
 *
 *      -   Contar numero de noticias
 *
 */
public class NoticiasDB extends SQLiteOpenHelper {

    private static NoticiasDB noticiasDB;

    public NoticiasDB(Context context) {
        super(context, "noticias", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE noticiasDeporte(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT, " +
                "contenido TEXT, " +
                "imagen TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public static void inicializarDB(Context context){
        noticiasDB = new NoticiasDB(context);
        /*Para borrar la tabla de la bbdd descomentar lo siguiente*/
        //borrarTodo();
    }
    public static Cursor listado(){
        SQLiteDatabase db = noticiasDB.getReadableDatabase();
        return db.rawQuery("SELECT * FROM noticiasDeporte",null);
    }
    public static Noticia elemento (int id){
        Noticia not = null;
        SQLiteDatabase db = noticiasDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM noticiasDeporte WHERE _id=" + id, null);
        if(cursor.moveToNext()){
            not=new Noticia();
            not.setTitulo(cursor.getString(1));
            not.setContenido(cursor.getString(2));
            not.setImagen(cursor.getString(3));
        }
        cursor.close();
        db.close();

        return not;
    }
    public static void borrar(int id){
        SQLiteDatabase db = noticiasDB.getWritableDatabase();
        db.execSQL("DELETE FROM noticiasDeporte WHERE _id=" + id);
        db.close();
    }
    public static long contarNoticias() {
        SQLiteDatabase db = noticiasDB.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, "noticiasDeporte");
        db.close();
        return cnt;
    }
    public static void nuevo(String titulo, String contenido, String imagen){
        try {
            SQLiteDatabase db = noticiasDB.getWritableDatabase();
            db.execSQL("INSERT INTO noticiasDeporte (titulo, contenido, imagen) VALUES ("
                    + "'" +titulo + "'"
                    + ",'" + contenido + "'"
                    + ",'" + imagen + "'"
                    + ")");

            db.close();
        }
        catch (Exception e){}
    }
    public static void borrarTodo(){
        SQLiteDatabase db = noticiasDB.getWritableDatabase();
        db.execSQL("DELETE FROM noticiasDeporte");
        db.close();
    }
}
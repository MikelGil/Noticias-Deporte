package com.ertzil.deportistas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Mikel Gil on 15/03/2016.
 *
 * Actividad NoticiaActivity a la que se le pasa el id de una noticia y se carga su contenido
 * desde la base de datos
 */

public class NoticiaActivity extends AppCompatActivity {

    //Variables
    private long id;
    Noticia noticia;

    TextView contenido,titulo;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        //Se obtiene el id y se hace una consulta a la bbdd
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id", -1);
        noticia = NoticiasDB.elemento((int) id);

        //Se añade una toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contenido = (TextView)findViewById(R.id.contenido);
        titulo = (TextView)findViewById(R.id.titulo);
        imagen = (ImageView)findViewById(R.id.imagen);

        setTitle("Berria");

        //Se ponen los datos de la noticia en la vista
        actualizarVista();
    }

    private void actualizarVista(){
        titulo.setText(noticia.getTitulo());
        contenido.setText(noticia.getContenido());
        Picasso.with(this).load(noticia.getImagen()).into(imagen);
    }

    //Metodo para borrar la noticia actual
    public void borrarNoticia(View v){
        NoticiasDB.borrar((int)id);
        Toast.makeText(NoticiaActivity.this, "Noticia borrar con éxito, vuelva a la página principal y ejecute la consulta otra vez", Toast.LENGTH_LONG).show();
    }
}

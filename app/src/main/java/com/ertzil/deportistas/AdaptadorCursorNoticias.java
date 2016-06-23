package com.ertzil.deportistas;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mikel Gil on 18/03/2016.
 */
public class AdaptadorCursorNoticias extends CursorAdapter{

    private LayoutInflater inflador; // Crea Layouts a partir del XML
    TextView titulo;
    ImageView imagen;
    List<Noticia> lista;
    Context contexto;

    //Se crea un cursor adaptador para luego usarlo en nuestra vista
    public AdaptadorCursorNoticias(Context context, Cursor c) {
        super(context, c, false);
        this.contexto = context;
    }

    //Se crea una vista nueva usando nuestro adaptador
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vista = inflador.inflate(R.layout.elemento_lista, parent, false);
        return vista;
    }

    //Usando la vista creada se rellena con los datos titulo e imagen
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        titulo = (TextView) view.findViewById(R.id.titulo);
        imagen = (ImageView) view.findViewById(R.id.imagen);
        titulo.setText(cursor.getString(cursor.getColumnIndex("titulo")));
        Picasso.with(contexto).load(cursor.getString(cursor.getColumnIndex("imagen"))).into(imagen);
    }


}

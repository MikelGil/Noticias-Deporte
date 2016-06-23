package com.ertzil.deportistas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Mikel Gil on 15/03/2016.
 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    List<Noticia> noticias;
    Noticia not;
    MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        NoticiasDB.inicializarDB(activity);

        noticias = new ArrayList<>();
    }

    //Metodo que hace la llamada para obtener un xml y tras tratar el Xml, se muestra en la ventana actual con un adaptador personalizado.
    public void HacerLlamada(View v){
        if (NoticiasDB.contarNoticias() == 0){
            ProcessJSON consulta = new ProcessJSON();
            consulta.execure("https://github.com/MikelGil/Noticias-Deporte/master/news.xml");
        }
        BaseAdapter adaptador = new AdaptadorCursorNoticias(activity,NoticiasDB.listado());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(activity);
    }

    //Metodo para procesar un archivo xml o json de forma asincrona
    private class ProcessJSON extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... strings){
            String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);

            // Return the data from specified url
            return stream;
        }

        protected void onPostExecute(String stream){
            if(stream !=null){
                try{
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();

                    Document doc = db.parse(new InputSource(new StringReader(stream)));
                    doc.getDocumentElement().normalize();

                    NodeList nodelist = doc.getElementsByTagName("new");
                    for (int i = 0; i < nodelist.getLength(); i++){
                        Node node = nodelist.item(i);
                        Element fstElmnt = (Element) node;

                        //Obtenemos el contenido de los nodos y los asignamos a variables
                        String titulo = getNode("title", fstElmnt);
                        String contenido = getNode("text", fstElmnt);
                        String imagen = getNode("image", fstElmnt);

                        //Creamos una nuevo objeto Noticia y le asignamos las variables anteriores. Además la añadimos al array de noticias.
                        not = new Noticia();
                        not.setContenido(contenido);
                        not.setTitulo(titulo);
                        not.setImagen(imagen);
                        noticias.add(not);

                        //Creamos una nueva noticia en nuestra bbdd
                        NoticiasDB.nuevo(titulo, contenido, imagen);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    //Metodo para obtener el contenido del nodo.
    private static String getNode(String sTag, Element eElement){
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }


    //Metodo para que al hacer click en una noticia, la abra en una nueva ventana y le pase el id de la noticia
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, NoticiaActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }
}

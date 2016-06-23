package com.ertzil.deportistas;

/**
 * Created by Mikel Gil on 15/03/2016.
 */
public class Noticia {

    public String titulo, contenido, imagen;

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Noticia(){}
}

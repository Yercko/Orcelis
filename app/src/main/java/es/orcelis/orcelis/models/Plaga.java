package es.orcelis.orcelis.models;

/**
 * Created by yercko on 07/05/2017.
 */

public class Plaga {
    public String id;
    public String nombre;
    public String nombre_latin;
    public String sintomas;
    public String descripcion;

    public Plaga(String id, String nombre, String nombre_latin, String sintomas, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.nombre_latin = nombre_latin;
        this.sintomas = sintomas;
        this.descripcion = descripcion;
    }
}

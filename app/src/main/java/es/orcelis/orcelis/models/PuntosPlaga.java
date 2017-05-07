package es.orcelis.orcelis.models;

/**
 * Created by yercko on 07/05/2017.
 */

public class PuntosPlaga {
    public String id;
    public String id_punto;
    public String id_problema;
    public String cantidad;
    public String id_unidad;

    public PuntosPlaga(String id, String id_punto, String id_problema, String cantidad, String id_unidad) {
        this.id = id;
        this.id_punto = id_punto;
        this.id_problema = id_problema;
        this.cantidad = cantidad;
        this.id_unidad = id_unidad;
    }
}

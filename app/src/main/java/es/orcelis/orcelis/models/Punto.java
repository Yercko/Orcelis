package es.orcelis.orcelis.models;

/**
 * Created by yercko on 07/05/2017.
 */

public class Punto {
    private String id;
    private String lat;
    private String lon;
    private String id_trip;
    private String fecha_hora;

    public Punto(String id, String lat, String lon, String id_trip, String fecha_hora) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.id_trip = id_trip;
        this.fecha_hora = fecha_hora;
    }
}

package es.orcelis.orcelis.models;

/**
 * Created by yercko on 07/05/2017.
 */

public class Trip {
    private String id;
    private String nombre;
    private String fecha_hora_inicio;
    private String fecha_hora_fin;
    private String id_usuario;
    private String id_tipo_cultivo;
    private String id_explotation;
    private String id_cultivo;
    //TODO aniadir el campo array de coordenadas

    public Trip(String id, String nombre, String fecha_hora_inicio, String fecha_hora_fin, String id_usuario, String id_tipo_cultivo, String id_explotation, String id_cultivo) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fecha_hora_fin;
        this.id_usuario = id_usuario;
        this.id_tipo_cultivo = id_tipo_cultivo;
        this.id_explotation = id_explotation;
        this.id_cultivo = id_cultivo;
    }
}

package es.orcelis.orcelis.models;

/**
 * Created by yercko on 07/05/2017.
 */

public class Foto {
    public String id;
    public String gravedad;
    public String nota;
    public String mime;

    public Foto(String id, String gravedad, String nota, String mime) {
        this.id = id;
        this.gravedad = gravedad;
        this.nota = nota;
        this.mime = mime;
    }
}

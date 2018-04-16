package es.orcelis.orcelis.models.datasync;

/**
 * Created by ymontero on 05/05/2017.
 */

public class TipoCultivo {
    public String id;
    public String nombre_cientifico;
    public TipoCultivo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre_cientifico() {
        return nombre_cientifico;
    }

    public void setNombre_cientifico(String nombre_cientifico) {
        this.nombre_cientifico = nombre_cientifico;
    }
}

package es.orcelis.orcelis.models.datasync;

import java.util.List;

/**
 * Created by yerckomontero on 25/10/17.
 */

public class Explotacion {
    public String id;
    public String nombre;
    public String observaciones;
    public List<Cultivo> cultivos;

    public Explotacion(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Cultivo> getCultivos() {
        return cultivos;
    }

    public void setCultivos(List<Cultivo> cultivos) {
        this.cultivos = cultivos;
    }
}

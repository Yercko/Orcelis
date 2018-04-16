package es.orcelis.orcelis.models.datasync;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by yerckomontero on 24/10/17.
 */

public class Inspeccion {
    public String id;
    public String fecha_hora;
    public Plaga plaga;
    public LatLng localizacion;
    public String foto;
    public String nota;


    public Inspeccion(String id) {
        this.id= id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public Plaga getPlaga() {
        return plaga;
    }

    public void setPlaga(Plaga plaga) {
        this.plaga = plaga;
    }

    public LatLng getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(LatLng localizacion) {
        this.localizacion = localizacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}

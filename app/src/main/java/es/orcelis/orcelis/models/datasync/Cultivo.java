package es.orcelis.orcelis.models.datasync;

import com.cocoahero.android.geojson.GeoJSONObject;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by yercko on 07/05/2017.
 */

public class Cultivo {
    public String id;
    public String nombre;
    public TipoCultivo tipo_cultivo;
    public JSONObject poligono;
    public Pais pais;
    public String region;
    public String observaciones;
    public List<Trip> trips;

    public Cultivo(){

    }
    public Cultivo(String id) {
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

    public TipoCultivo getTipo_cultivo() {
        return tipo_cultivo;
    }

    public void setTipo_cultivo(TipoCultivo tipo_cultivo) {
        this.tipo_cultivo = tipo_cultivo;
    }

    public JSONObject getPoligono() {
        return poligono;
    }

    public void setPoligono(JSONObject poligono) {
        this.poligono = poligono;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}

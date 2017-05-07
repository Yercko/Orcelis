package es.orcelis.orcelis.models;

import com.cocoahero.android.geojson.GeoJSONObject;

/**
 * Created by yercko on 07/05/2017.
 */

public class Cultivo {
    public String id;
    public GeoJSONObject geojson;

    public Cultivo(String id, GeoJSONObject geojson) {
        this.id = id;
        this.geojson = geojson;
    }
}

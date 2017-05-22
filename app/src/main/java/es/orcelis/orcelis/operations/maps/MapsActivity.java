package es.orcelis.orcelis.operations.maps;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import es.orcelis.orcelis.R;
import es.orcelis.orcelis.utils.views.PasosView;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener{

    private GoogleMap mMap;

    private PasosView pasosView;

    private LinearLayout trip_menu_botom;
    private ImageView next_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initViews();
        initValues();
        initListener();



    }

    public void initViews(){
        pasosView = (PasosView)findViewById(R.id.pasosView);
        trip_menu_botom = (LinearLayout)findViewById(R.id.include_menu_bottom);
        next_button = (ImageView)findViewById(R.id.next_button);
    }

    public void initValues(){

    }

    public void initListener(){
        pasosView.setOnDragListener(null);
        pasosView.setListenerClose(this);

        next_button.setOnClickListener(this);

        MaterialRippleLayout.on(pasosView)
                .rippleColor(Color.parseColor("#FF0000"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();

    }

   //TODO manage when map don't install
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(17.0f);
        googleMap.setBuildingsEnabled(true);

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style));

            if (!success) {
                Log.e("maps", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("maps", "Can't find style. Error: ", e);
        }
        LatLng orihuela = new LatLng(38.0654185, -0.8743761);
       // mMap.addMarker(new MarkerOptions().position(orihuela).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(orihuela));
        try {
            //TODO hacerlo en una Asyntask
            GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.google,getApplicationContext());
            for (GeoJsonFeature capa: layer.getFeatures()) {
                capa.getPolygonStyle().setFillColor(getResources().getColor(R.color.primary_darker_transparent));
                capa.getPolygonStyle().setStrokeColor(getResources().getColor(R.color.accent));
                capa.getPolygonStyle().setStrokeWidth(3.0f);
            }
            //addGeoJsonLayerToMap(layer);
            layer.addLayerToMap();

            layer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
                @Override
                public void onFeatureClick(GeoJsonFeature geoJsonFeature) {
                    if (geoJsonFeature !=null){
                        if (geoJsonFeature.getPolygonStyle() != null){
                            geoJsonFeature.getPolygonStyle().setFillColor(getResources().getColor(R.color.black));

                        }
                        pasosView.changecheck(true,0);
                        trip_menu_botom.setVisibility(View.VISIBLE);
                    }
                    /**
                     * Manejar else para contemplar pulsacion fuera del mapa, deseleccionar zona y ocultar menu inferior
                     * Solo cuando se esté en el PASO 1
                     */

                }
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close_pasos:
                pasosView.setVisibility(View.GONE);
                break;
            case R.id.next_button:
                pasosView.siguiente();
                //trip_menu_botom.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

/** TODO simulate Trip
     *  1. Rellenar formulario de Start Trip
     *      Boton+ cambiar la toolbar con cancelar, seleccione un cultivo y hecho,
     *      una vez seleccionado, hecho cambia de gris a amarillo,
     *
     *  2. Trackear puntos cada 10 seg aniadirlos al array de coordenadas
     *  3. Inspeccionar en la clase Foto
     *  4. Finalizar Trip
     *  5. Manejar Trip sin finalizar
     *
     *
     *  **manejar lo anterior almacenando cada paso en la base de datos y controlando que cuando se cierre la app
     *  quede todo almacenado, y solo cuando se finalice, se envie el servidor
     */

}

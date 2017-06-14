package es.orcelis.orcelis.operations.maps;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
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
import es.orcelis.orcelis.operations.cultivos.OpcionesCultivosListDialogFragment;
import es.orcelis.orcelis.operations.login.LoginActivity;
import es.orcelis.orcelis.utils.DialogManager;
import es.orcelis.orcelis.utils.views.PasosView;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener,OpcionesCultivosListDialogFragment.Listener{

    private GoogleMap mMap;

    private PasosView pasosView;

    private LinearLayout trip_menu_botom;
    private ImageView next_button;

    private FloatingActionButton fab;
    private OpcionesCultivosListDialogFragment opcionesCultivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if(getSupportActionBar() != null ){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.title_cultivos));
        }

       DialogManager.getDialogDefault(this,getString(R.string.cargando));

    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.anim_go_in, R.anim.anim_go_out);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initViews();
        initValues();
        initListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);

    }

    public void initViews(){
        pasosView = (PasosView)findViewById(R.id.pasosView);
        trip_menu_botom = (LinearLayout)findViewById(R.id.include_menu_bottom);
        next_button = (ImageView)findViewById(R.id.next_button);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        opcionesCultivos = OpcionesCultivosListDialogFragment.newInstance();
    }

    public void initValues(){
        //TODO manage Data from Bundle


        //TODO manage seguir con el ultimo trip sin finalizar, en caso de que no,finalizar
    }

    public void initListener(){
        pasosView.setOnDragListener(null);
        pasosView.setListenerClose(this);
        pasosView.setVisibility(View.GONE);

        next_button.setOnClickListener(this);
        fab.setOnClickListener(this);

    }

   //TODO manage when map don't install
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

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
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                orihuela, 10);
        mMap.animateCamera(location);
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
                    if (geoJsonFeature !=null) {
                        if (pasosView.getVisibility() == View.VISIBLE){
                            if (geoJsonFeature.getPolygonStyle() != null) {
                                geoJsonFeature.getPolygonStyle().setFillColor(getResources().getColor(R.color.black));
                            }
                            /**
                             * Manejar else para contemplar pulsacion fuera del mapa, deseleccionar zona y ocultar menu inferior
                             * Solo cuando se esté en el PASO 1
                             */
                            pasosView.changecheck(true, 0);
                            trip_menu_botom.setVisibility(View.VISIBLE);
                        }
                    }


                }
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }



        DialogManager.ocultarDialogDefault();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close_pasos:
                pasosView.setVisibility(View.GONE);
                trip_menu_botom.setVisibility(View.GONE);
                if (getSupportActionBar() != null){
                    getSupportActionBar().show();
                }
                fab.show();
                break;
            case R.id.next_button:
                pasosView.siguiente();
                //trip_menu_botom.setVisibility(View.GONE);
                break;
            case R.id.fab:
                opcionesCultivos.show(getSupportFragmentManager(), OpcionesCultivosListDialogFragment.TAG);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO
        //getMenuInflater().inflate(R.menu.cultivos_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    public void onOpcionesCultivosClicked(int position) {
        //TODO manage items botomMenu
        if (position == 0){
            manageInspeccion();
        }
    }


    public void manageInspeccion(){
        //ocultar actionbar
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //mostrar pasos
        pasosView.setVisibility(View.VISIBLE);
        //
        managePosicionActualEnCultivo();

    }

    public void managePosicionActualEnCultivo(){
        fab.hide();
        //TODO en background leer los puntos de la region GeoJson

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

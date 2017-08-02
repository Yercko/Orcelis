package es.orcelis.orcelis.operations.maps;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.orcelis.orcelis.R;
import es.orcelis.orcelis.models.Cultivo;
import es.orcelis.orcelis.operations.cultivos.OpcionesCultivosListDialogFragment;
import es.orcelis.orcelis.utils.DialogManager;
import es.orcelis.orcelis.utils.views.PasosView;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener,OpcionesCultivosListDialogFragment.Listener {

    private GoogleMap mMap;

    private PasosView pasosView;

    private LinearLayout trip_menu_botom;
    private ImageView next_button;

    private FloatingActionButton fab;
    private OpcionesCultivosListDialogFragment opcionesCultivos;
    Toolbar tb;

    //es necesario guardar en el shared preference las siguientes variables de estado

    //manejo de poligonos
    //PolylineOptions rectOptions;
    //Polyline polygon;
    boolean markerClicked;

    Polygon poligono_modificable;
    PolygonOptions poligono_mod_Options;

    HashMap<Marker,LatLng> newRegionCultivo;

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
        //TODO manage load SharedPreference


        overridePendingTransition(R.anim.anim_go_in, R.anim.anim_go_out);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initViews();
        initValues();
        initListener();


        final BottomSheetBehavior bsb = BottomSheetBehavior.from(trip_menu_botom);

        trip_menu_botom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

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
        //TODO ordenar todo en funciones helper de configuracion de cada parte SetUpMapIfNeed
        if(mMap == null) {
            //TODO parte de configuracion visual del mapa
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
                GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.google, getApplicationContext());
                for (GeoJsonFeature capa : layer.getFeatures()) {
                    capa.getPolygonStyle().setFillColor(getResources().getColor(R.color.primary_darker_transparent));
                    capa.getPolygonStyle().setStrokeColor(getResources().getColor(R.color.accent));
                    capa.getPolygonStyle().setStrokeWidth(3.0f);
                }
                //addGeoJsonLayerToMap(layer);
                layer.addLayerToMap();

                layer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
                    @Override
                    public void onFeatureClick(GeoJsonFeature geoJsonFeature) {
                        if (geoJsonFeature != null) {
                            if (geoJsonFeature.getPolygonStyle() != null) {
                                geoJsonFeature.getPolygonStyle().setFillColor(getResources().getColor(R.color.black));

                            }
                            pasosView.changecheck(true, 0);
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
            //TODO manage lifeCicle maps

            DialogManager.ocultarDialogDefault();
        }

        //TODO NUEVO
        //comprobacion de google play services

        if (isGooglePlayServicesAvailable(this)){
            Toast.makeText(getApplicationContext(),
                    "isGooglePlayServicesAvailable SUCCESS",
                    Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "isGooglePlayServicesAvailable FAIL",
                    Toast.LENGTH_LONG).show();
        }
        //poner esto en la pantalla anterior para checkear permiso
        if ( Build.VERSION.SDK_INT >= 23) {
            int hasPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

            if (hasPermission == -1) {
                String[] PermissionsLocation = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, PermissionsLocation, 0);
            }
            else{
                long mLocTrackingInterval = 1000 * 5; // 5 sec
                float trackingDistance = 0;
                LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;

                LocationParams.Builder builder = new LocationParams.Builder()
                        .setAccuracy(trackingAccuracy)
                        .setDistance(trackingDistance)
                        .setInterval(mLocTrackingInterval);

                SmartLocation.with(this)
                        .location()
                        .continuous()
                        .config(builder.build())
                        .start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                locationListener.onLocationChanged(location);

                            }
                        });
            }
        }

        //-------------------
        /*
        rectOptions = new PolylineOptions();

        rectOptions.add(new LatLng(37.35, -122.0),
                new LatLng(37.45, -122.0),
                new LatLng(37.45, -122.2),
                new LatLng(37.35, -122.2),
                new LatLng(37.35, -122.0));
        polygon = mMap.addPolyline(rectOptions);
*/

        //mMap.setOnMapClickListener(this);
       // mMap.setOnMapLongClickListener(this);
        //mMap.setOnMarkerClickListener(this);
        markerClicked = false;





    }

    public void crear_cultivo(){
        newRegionCultivo = new HashMap<>();
        poligono_mod_Options = new PolygonOptions();

        newRegionCultivo.put(mMap.addMarker(new MarkerOptions().position(new LatLng(37.35, -122.0)).draggable(true)),new LatLng(37.35, -122.0));
        newRegionCultivo.put(mMap.addMarker(new MarkerOptions().position(new LatLng(37.45, -122.0)).draggable(true)),new LatLng(37.35, -122.0));
        newRegionCultivo.put(mMap.addMarker(new MarkerOptions().position(new LatLng(37.45, -122.2)).draggable(true)),new LatLng(37.35, -122.0));
        newRegionCultivo.put(mMap.addMarker(new MarkerOptions().position(new LatLng(37.35, -122.0)).draggable(true)),new LatLng(37.35, -122.0));
        poligono_mod_Options.add(new LatLng(37.35, -122.0),
                new LatLng(37.45, -122.0),
                new LatLng(37.45, -122.2),
                new LatLng(37.39, -122.0));
        poligono_mod_Options.strokeColor(Color.RED);
        poligono_mod_Options.fillColor(Color.WHITE);

        poligono_modificable = mMap.addPolygon(poligono_mod_Options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.35, -122.0)));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
                poligono_mod_Options.getPoints().remove(arg0.getPosition());

            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                String a  = arg0.getId();
                poligono_modificable.remove();
                poligono_mod_Options.add(arg0.getPosition());

                poligono_modificable=mMap.addPolygon(poligono_mod_Options);

            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });
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
        getMenuInflater().inflate(R.menu.cultivos_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    public void manageToolbars(){

    }


    @Override
    public void onOpcionesCultivosClicked(int position) {
        //TODO manage items botomMenu
        if (position == 0){
            manageInspeccion();
        }
        else{
            crear_cultivo();
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


private LocationListener locationListener = new LocationListener() {
    public void onLocationChanged(Location location) {
        if (location != null) {
            //almacenar ultima localizacion para usarla en cualquier momento
            /*
            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title("Localizacion actual"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            LatLng newPoint = new LatLng(location.getLatitude(), location.getLongitude());
            List<LatLng> points = polygon.getPoints();
            points.add(newPoint);
            polygon.setPoints(points);
            */
        }

    }

    public void onProviderDisabled(String provider) {

    }

    public void onProviderEnabled(String provider) {

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
};


    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

/*
@Override
public void onMapClick(LatLng latLng) {
    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    markerClicked = false;
}


    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Localizacion actual"));
        LatLng newPoint = latLng;
        List<LatLng> points = polygon.getPoints();
        points.add(newPoint);
        poligono_modificable.setPoints(points);

        markerClicked = false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(markerClicked){

            if(poligono_modificable != null){
                poligono_modificable.remove();
                poligono_modificable = null;
            }

            poligono_mod_Options.add(marker.getPosition());
            poligono_mod_Options.strokeColor(Color.RED);
            poligono_mod_Options.fillColor(Color.BLUE);
            poligono_modificable = mMap.addPolygon(poligono_mod_Options);
        }else{
            if(poligono_modificable != null){
                poligono_modificable.remove();
                poligono_modificable = null;
            }

            poligono_mod_Options = new PolygonOptions().add(marker.getPosition());
            markerClicked = true;
        }

        return true;
    }
    */
}

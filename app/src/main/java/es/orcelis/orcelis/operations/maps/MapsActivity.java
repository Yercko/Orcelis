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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import es.orcelis.orcelis.utils.MedidasManager;
import es.orcelis.orcelis.utils.views.PasosView;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener,OpcionesCultivosListDialogFragment.Listener {

    private GoogleMap mMap;

    private PasosView pasosView;

    private LinearLayout trip_menu_botom;

    private FloatingActionButton fab;
    private OpcionesCultivosListDialogFragment opcionesCultivos;
    Toolbar tb;

    public static final int PASO1 = 0;
    public static final int PASO2 = 1;
    public static final int PASO3 = 2;

    //es necesario guardar en el shared preference las siguientes variables de estado

    //manejo de poligonos
    //PolylineOptions rectOptions;
    //Polyline polygon;
    boolean markerClicked;

    Polygon poligono_modificable;
    PolygonOptions poligono_mod_Options;

    List<Marker> newRegionCultivo;

    Location localizacion_actual;

    BottomSheetBehavior button_sheet_behavior;
    //-------------------------------------

    TextView nombre_cultivo_seleccionado;
    LinearLayout btn_finalizar_pasos;
    LinearLayout btn_siguiente_paso;
    RecyclerView rv_listado_plagas;
    LinearLayout btn_add_new_plaga;

    LinearLayout btn_anterior_pasos;



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


        button_sheet_behavior = BottomSheetBehavior.from(trip_menu_botom);

        trip_menu_botom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_sheet_behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
        fab = (FloatingActionButton)findViewById(R.id.fab);
        opcionesCultivos = OpcionesCultivosListDialogFragment.newInstance();

        btn_anterior_pasos = (LinearLayout) findViewById(R.id.bnt_anterior_paso);
        nombre_cultivo_seleccionado = (TextView) findViewById(R.id.tv_cultivo_seleccionado);
        btn_finalizar_pasos = (LinearLayout)findViewById(R.id.btn_finalizar_pasos);
        btn_siguiente_paso = (LinearLayout)findViewById(R.id.btn_siguiente_paso);
        rv_listado_plagas = (RecyclerView) findViewById(R.id.recycler_listado_plagas);
        btn_add_new_plaga = (LinearLayout)findViewById(R.id.btn_add_new_plaga);


    }

    public void initValues(){
        //TODO manage Data from Bundle


        //TODO manage seguir con el ultimo trip sin finalizar, en caso de que no,finalizar
    }

    public void initListener(){
        pasosView.setOnDragListener(null);
        pasosView.setListenerClose(this);
        pasosView.setVisibility(View.GONE);

        fab.setOnClickListener(this);

        btn_anterior_pasos.setOnClickListener(this);
        btn_finalizar_pasos.setOnClickListener(this);
        btn_siguiente_paso.setOnClickListener(this);
        btn_add_new_plaga.setOnClickListener(this);

    }

   //TODO manage when map don't install
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO ordenar todo en funciones helper de configuracion de cada parte SetUpMapIfNeed
        if(mMap == null) {
            // parte de configuracion visual del mapa
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

            //TODO por defecto se muestran los cultivos disponibles
            configurar_mapa_inspeccion();


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

    public void configurar_mapa_inspeccion(){
        LatLng orihuela = new LatLng(38.0654185, -0.8743761);
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
                        pasosView.setVisibility(View.VISIBLE);
                        pasosView.changecheck(true, PASO1);
                        trip_menu_botom.setVisibility(View.VISIBLE);
                        //paso 1

                    }

                }
            });

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if (pasosView.getPasoActual() == PASO1) {
                        pasosView.changecheck(false, PASO1);
                        trip_menu_botom.setVisibility(View.GONE);
                    }

                }
            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        //TODO manage lifeCicle maps
    }

    public void crear_cultivo(){
        newRegionCultivo = new ArrayList<>();
        poligono_mod_Options = new PolygonOptions();

        newRegionCultivo.add(mMap.addMarker(new MarkerOptions().position(new LatLng(37.35, -122.0)).draggable(true)));
        newRegionCultivo.add(mMap.addMarker(new MarkerOptions().position(new LatLng(37.45, -122.0)).draggable(true)));
        newRegionCultivo.add(mMap.addMarker(new MarkerOptions().position(new LatLng(37.45, -122.2)).draggable(true)));
        newRegionCultivo.add(mMap.addMarker(new MarkerOptions().position(new LatLng(37.39, -122.0)).draggable(true)));
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
                int eliminado = 0;
                for (int i = 0; i < newRegionCultivo.size(); i++) {
                    // if (MedidasManager.distance(puntos.get(i).latitude, puntos.get(i).longitude, arg0.getPosition().latitude, arg0.getPosition().longitude) < 0.000000001) { // if distance < 0.1 miles we take locations as equal
                    if (newRegionCultivo.get(i).getId().equalsIgnoreCase(arg0.getId())) {
                        eliminado = i;
                    }
                }
                newRegionCultivo.remove(eliminado);

            }
            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                poligono_modificable.remove();
                poligono_mod_Options.getPoints().clear();
                String id = marker.getId();
                id = id.replace("m", "");
                if (Integer.parseInt(id) <= 4){
                    newRegionCultivo.add(Integer.parseInt(id), marker);
                }
                else{
                    //si el id que se obtiene es mayor al que deberia,se aniade al final
                    newRegionCultivo.add(marker);
                }
                for (int i=0;i<newRegionCultivo.size();i++){
                    poligono_mod_Options.add(newRegionCultivo.get(i).getPosition());
                }

                poligono_modificable=mMap.addPolygon(poligono_mod_Options);
            }



        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close_pasos:
                //ocultar pasos y mostrar el menu inicial
                pasosView.setVisibility(View.GONE);
                trip_menu_botom.setVisibility(View.GONE);
                if (getSupportActionBar() != null){
                    getSupportActionBar().show();
                }
                fab.show();
                break;
            case R.id.btn_siguiente_paso:
                pasosView.siguiente();
                managePasosView();
                break;
            case R.id.bnt_anterior_paso:
                pasosView.anterior();
                managePasosView();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.localizacion_actual) {
           if (localizacion_actual != null){
               CameraUpdate location =  CameraUpdateFactory.newLatLng(new LatLng(localizacion_actual.getLatitude(),localizacion_actual.getLongitude()));
               mMap.animateCamera(location);
           }
           else{
               Toast.makeText(this,"No se ha podido obtener la localización en este momento",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }




    @Override
    public void onOpcionesCultivosClicked(int position) {
        //TODO manage items botomMenu
        if (position == 0){
            mMap.clear();
            configurar_mapa_inspeccion();
            manageInspeccion();
        }
        else{
            mMap.clear();
            //TODO falta manejar el menu y el proceso de crear un cultivo
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
        fab.hide();


    }

    public void managePasosView(){
        // Setear los datos correspondientes al cultivo seleccionado

        switch (pasosView.getPasoActual()){
            case PASO1:
                btn_anterior_pasos.setVisibility(View.INVISIBLE);
                btn_siguiente_paso.setVisibility(View.VISIBLE);
                btn_finalizar_pasos.setVisibility(View.GONE);
                break;
            case PASO2:
                btn_anterior_pasos.setVisibility(View.VISIBLE);
                btn_siguiente_paso.setVisibility(View.GONE);
                btn_finalizar_pasos.setVisibility(View.VISIBLE);

                break;
            case PASO3:

                break;
            default:
                break;
        }

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
            localizacion_actual = location;
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


}

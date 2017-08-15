package es.orcelis.orcelis.operations.maps;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;

import es.orcelis.orcelis.R;
import es.orcelis.orcelis.operations.cultivos.OpcionesCultivosListDialogFragment;
import es.orcelis.orcelis.operations.cultivos.add_plaga.AddPlagaFormActivity;
import es.orcelis.orcelis.operations.cultivos.crear_cultivo.RecargarMapa;
import es.orcelis.orcelis.operations.cultivos.crear_cultivo.UIHelper;
import es.orcelis.orcelis.utils.DialogManager;
import es.orcelis.orcelis.utils.views.PasosView;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener,OpcionesCultivosListDialogFragment.Listener,RecargarMapa {

    private GoogleMap mMap;

    private PasosView pasosView;

    private LinearLayout trip_menu_botom;

    private FloatingActionButton fab;
    private OpcionesCultivosListDialogFragment opcionesCultivos;
    Toolbar tb;

    public static int ZOOM = 13;

    public static final int PASO1 = 0;
    public static final int PASO2 = 1;
    public static final int PASO3 = 2;

    Location localizacion_actual;

    BottomSheetBehavior button_sheet_behavior;

    Marker ubicacion_actual_marker;
    //-------------------------------------

    TextView nombre_cultivo_seleccionado;
    LinearLayout btn_finalizar_pasos;
    LinearLayout btn_siguiente_paso;
    RecyclerView rv_listado_plagas;
    LinearLayout btn_add_new_plaga;

    LinearLayout btn_anterior_pasos;

    TextView titulo_cultivo_seleccionado;
    TextView tv_cultivo_seleccionado;

    private static final int REQUEST_CODE_EXAMPLE = 0x9345;


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

        titulo_cultivo_seleccionado = (TextView)findViewById(R.id.title_cultivo_seleccionado);
        tv_cultivo_seleccionado = (TextView)findViewById(R.id.tv_cultivo_seleccionado);

    }

    public void initValues(){
        //TODO manage Data from Bundle
            //volver de ADD_NOTA y mostrar el menu superior


        //TODO manage seguir con el ultimo trip sin finalizar, en caso de que no,finalizar
    }

    public void initListener(){
        pasosView.setOnDragListener(null);
        pasosView.setListenerClose(this);

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
            /*
            Toast.makeText(getApplicationContext(),
                    "isGooglePlayServicesAvailable SUCCESS",
                    Toast.LENGTH_LONG).show();
                    */
        }
        else{
            /*
            Toast.makeText(getApplicationContext(),
                    "isGooglePlayServicesAvailable FAIL",
                    Toast.LENGTH_LONG).show();
                    */
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





    }

    public void configurar_mapa_inspeccion(){
        LatLng orihuela = new LatLng(38.0654185, -0.8743761);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                orihuela, ZOOM);
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
                        tv_cultivo_seleccionado.setText(geoJsonFeature.getProperty("letter"));

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



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close_pasos:
                //Resetear pasos, ocultar y mostrar el menu inicial
                pasosView.resetear();
                pasosView.setVisibility(View.GONE);
                trip_menu_botom.setVisibility(View.GONE);
                if (getSupportActionBar() != null){
                    getSupportActionBar().show();
                }
                fab.show();
                break;
            case R.id.btn_siguiente_paso:
                if (tv_cultivo_seleccionado.getText().toString().equalsIgnoreCase("")){
                    DialogManager.getTwoButtonAlertDialog(this,getString(R.string.tv_campo_obligatorio),getString(R.string.tv_obligatorio_select_cultivo),null,null,null,null);
                }
                else{
                    pasosView.siguiente();
                    managePasosView();
                }
                break;
            case R.id.bnt_anterior_paso:
                pasosView.anterior();
                managePasosView();
                break;
            case R.id.fab:
                opcionesCultivos.show(getSupportFragmentManager(), OpcionesCultivosListDialogFragment.TAG);
                break;
            case R.id.btn_add_new_plaga:
                Intent intent = new Intent(this, AddPlagaFormActivity.class);
                startActivityForResult(intent, REQUEST_CODE_EXAMPLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_CODE_EXAMPLE) {
                if (resultCode == Activity.RESULT_OK) {
                    pasosView.setVisibility(View.VISIBLE);
                }
                else {

                }
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cultivos_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.localizacion_actual) {
           if (localizacion_actual != null){
               if (ubicacion_actual_marker != null) {
                   ubicacion_actual_marker.remove();
               }
               MarkerOptions markerOptions = new MarkerOptions();
               markerOptions.position(new LatLng(localizacion_actual.getLatitude(),
                       localizacion_actual.getLongitude()));
                markerOptions.draggable(false);
               markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ubicacion_actual_36));
               ubicacion_actual_marker = mMap.addMarker(markerOptions);
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
            fab.hide();
            UIHelper crear_cultivo_helper = new UIHelper(this,mMap,this);
            crear_cultivo_helper.crear_cultivo(localizacion_actual,fab);
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
                btn_add_new_plaga.setVisibility(View.GONE);
                titulo_cultivo_seleccionado.setText(R.string.tv_eleccion_cultivo);
                tv_cultivo_seleccionado.setText("");


                break;
            case PASO2:
                btn_anterior_pasos.setVisibility(View.VISIBLE);
                btn_siguiente_paso.setVisibility(View.GONE);
                btn_finalizar_pasos.setVisibility(View.VISIBLE);
                btn_add_new_plaga.setVisibility(View.VISIBLE);
                titulo_cultivo_seleccionado.setText(R.string.tv_cultivo_seleccionado);


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

    @Override
    public void iniciar() {
        configurar_mapa_inspeccion();
    }
}

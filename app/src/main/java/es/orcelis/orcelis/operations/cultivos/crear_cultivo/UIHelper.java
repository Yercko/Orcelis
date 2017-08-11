package es.orcelis.orcelis.operations.cultivos.crear_cultivo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

import es.orcelis.orcelis.R;
import es.orcelis.orcelis.data.ContractPlagas;

import static es.orcelis.orcelis.utils.Constantes.ACCOUNT_TYPE;

/**
 * Created by karol on 06/08/2017.
 */

public class UIHelper {
    private AppCompatActivity activity_padre;

    private Polygon poligono_modificable;
    private PolygonOptions poligono_mod_Options;
    private GoogleMap mMap;
    private LinearLayout menu_crear_cultivo;
    private LinearLayout btn_cancel_crear_cultivo;
    private LinearLayout btn_confirm_crear_cultivo;
    private RecargarMapa recargarMapa;

    public static double DISTANCIA_CUADRADO = 0.01;

    private List<Marker> newRegionCultivo;
    public UIHelper(AppCompatActivity activity,GoogleMap mMap, RecargarMapa recargarMapa){
        this.activity_padre = activity;
        this.mMap = mMap;
        this.recargarMapa = recargarMapa;
    }
    public void crear_cultivo(Location localizacion_actual, final FloatingActionButton floatingActionButton){
        newRegionCultivo = new ArrayList<>();
        poligono_mod_Options = new PolygonOptions();

        newRegionCultivo.add(mMap.addMarker(new MarkerOptions().position(new LatLng(localizacion_actual.getLatitude()-DISTANCIA_CUADRADO, localizacion_actual.getLongitude()+DISTANCIA_CUADRADO)).draggable(true)));
        newRegionCultivo.add(mMap.addMarker(new MarkerOptions().position(new LatLng(localizacion_actual.getLatitude()+DISTANCIA_CUADRADO, localizacion_actual.getLongitude()+DISTANCIA_CUADRADO)).draggable(true)));
        newRegionCultivo.add(mMap.addMarker(new MarkerOptions().position(new LatLng(localizacion_actual.getLatitude()+DISTANCIA_CUADRADO, localizacion_actual.getLongitude()-DISTANCIA_CUADRADO)).draggable(true)));
        newRegionCultivo.add(mMap.addMarker(new MarkerOptions().position(new LatLng(localizacion_actual.getLatitude()-DISTANCIA_CUADRADO,localizacion_actual.getLongitude()-DISTANCIA_CUADRADO)).draggable(true)));
        poligono_mod_Options.add(
                new LatLng(localizacion_actual.getLatitude()-DISTANCIA_CUADRADO, localizacion_actual.getLongitude()+DISTANCIA_CUADRADO),
                new LatLng(localizacion_actual.getLatitude()+DISTANCIA_CUADRADO, localizacion_actual.getLongitude()+DISTANCIA_CUADRADO),
                new LatLng(localizacion_actual.getLatitude()+DISTANCIA_CUADRADO, localizacion_actual.getLongitude()-DISTANCIA_CUADRADO),
                new LatLng(localizacion_actual.getLatitude()-DISTANCIA_CUADRADO,localizacion_actual.getLongitude()-DISTANCIA_CUADRADO)
        );
        poligono_mod_Options.strokeColor(Color.WHITE);
        poligono_mod_Options.fillColor(activity_padre.getResources().getColor(R.color.color_cultivo));

        poligono_modificable = mMap.addPolygon(poligono_mod_Options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(localizacion_actual.getLatitude(), localizacion_actual.getLongitude())));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
            }
            @Override
            public void onMarkerDrag(Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                poligono_modificable.remove();
                poligono_mod_Options.getPoints().clear();
                int eliminado = 0;
                for (int i = 0; i < newRegionCultivo.size(); i++) {
                    if (newRegionCultivo.get(i).getId().equalsIgnoreCase(marker.getId())) {
                        eliminado = i;
                    }
                }
                newRegionCultivo.remove(eliminado);
                newRegionCultivo.add(eliminado, marker);
                for (int i=0;i<newRegionCultivo.size();i++){
                    poligono_mod_Options.add(newRegionCultivo.get(i).getPosition());
                }
                poligono_modificable=mMap.addPolygon(poligono_mod_Options);
            }
        });
        //inicia el proceso de crear un nuevo cultivo
        menu_crear_cultivo = (LinearLayout) activity_padre.findViewById(R.id.menu_crear_cultivo);
        btn_cancel_crear_cultivo = (LinearLayout)activity_padre.findViewById(R.id.btn_cancel_crear_cultivo);
        btn_confirm_crear_cultivo = (LinearLayout)activity_padre.findViewById(R.id.btn_confirm_crear_cultivo);
        menu_crear_cultivo.setVisibility(View.VISIBLE);
        btn_cancel_crear_cultivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionButton.show();
                menu_crear_cultivo.setVisibility(View.GONE);
                mMap.clear();
                recargarMapa.iniciar();
            }
        });
        btn_confirm_crear_cultivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TareaPruebaDatos().execute();
            }
        });

    }

    public class TareaPruebaDatos extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            ContentResolver contentResolver = activity_padre.getContentResolver();
            // Lista de operaciones
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();

            ops.add(ContentProviderOperation.newInsert(ContractPlagas.CONTENT_URI_Cultivo)
                    .withValue(ContractPlagas.Cultivo.ID, "2")
                    .withValue(ContractPlagas.Cultivo.NOMBRE,"nombre del cultivo")
                    .withValue(ContractPlagas.Cultivo.GEOJSON, "{'sdsadsa'}")
                    .build());


            try {
                contentResolver.applyBatch(ContractPlagas.AUTORIDAD, ops);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }

            Log.d("Clientes", "Clientes");
            DatabaseUtils.dumpCursor(contentResolver.query(ContractPlagas.CONTENT_URI_Usuario, null, null, null, null));

            return null;
        }
    }



}

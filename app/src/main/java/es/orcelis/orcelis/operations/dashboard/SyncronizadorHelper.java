package es.orcelis.orcelis.operations.dashboard;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import es.orcelis.orcelis.data.ContractPlagas;

/**
 * Created by ymontero on 18/08/2017.
 */

public class SyncronizadorHelper {
    AppCompatActivity activity;

    //TODO alertar cuando la sincronizacion aun no ha terminado

    public SyncronizadorHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void explotacionesShoot(){
        //comprobar si existe algun nuevo registro en local por sincronizar
        //comprobar si existe algun nuevo registro en el Servidor
        //SI existen
        //extraer las ultimas explotaciones de este usuario


        //insertar las explotaciones
        ContentResolver contentResolver = activity.getContentResolver();
        // Lista de operaciones
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(ContentProviderOperation.newInsert(ContractPlagas.CONTENT_URI_Explotacion)
                .withValue(ContractPlagas.Usuario.ID, "2")
                .withValue(ContractPlagas.Usuario.EMAIL,"email")
                .withValue(ContractPlagas.Usuario.PASSWORD,"dsadsdaa")
                .withValue(ContractPlagas.Usuario.TELEFONO,"231321")
                .build());
        try {
            contentResolver.applyBatch(ContractPlagas.AUTORIDAD, ops);
        }  catch (Exception e) {
            e.printStackTrace();
        }


        //NO existen. No hacer nada

    }

}

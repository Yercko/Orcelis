package es.orcelis.orcelis.operations.dashboard;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import es.orcelis.orcelis.data.BaseDatosPlagas;
import es.orcelis.orcelis.data.ContractPlagas;
import es.orcelis.orcelis.data.OpBaseDatosHelper;

/**
 * Created by ymontero on 18/08/2017.
 */

public class SyncronizadorHelper {
    AppCompatActivity activity;

    //TODO alertar cuando la sincronizacion aun no ha terminado

    public SyncronizadorHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void usuarioShoot(String email){
        ContentResolver contentResolver = activity.getContentResolver();

        //Columnas de la tabla a recuperar
            Cursor cur = contentResolver.query(ContractPlagas.CONTENT_URI_Usuario,
                null, //Columnas a devolver
                null,       //Condición de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados
        if (cur == null) {
            Log.v("mosd","sdd");
        }
        else if (cur.getCount() < 0 ) {
            Log.v("mosd","sdd");
        }
        else if(cur.getCount() == 0){
            // Lista de operaciones
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            ops.add(ContentProviderOperation.newInsert(ContractPlagas.CONTENT_URI_Usuario)
                    .withValue(ContractPlagas.Usuario.ID, "2")
                    .withValue(ContractPlagas.Usuario.EMAIL,"email")
                    .withValue(ContractPlagas.Usuario.PASSWORD,"dsadsdaa")
                    .withValue(ContractPlagas.Usuario.TELEFONO,"231321")
                    .withValue(ContractPlagas.Usuario._UUID,"231321")
                    .withValue(ContractPlagas.Usuario.FECHA_FIN_USO,"231321")
                    .build());
            try {
                contentResolver.applyBatch(ContractPlagas.AUTORIDAD, ops);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }
            Log.d("Clientes", "Clientes");
        }
        else {

        }



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
                .withValue(ContractPlagas.Explotacion.ID, "2")
                .withValue(ContractPlagas.Explotacion.NOMBRE,"email")
                .withValue(ContractPlagas.Explotacion.TOKEN,"dsadsdaa")
                .withValue(ContractPlagas.Explotacion.ID_USUARIO,"1")
                .build());
        try {
            contentResolver.applyBatch(ContractPlagas.AUTORIDAD, ops);
        }  catch (Exception e) {
            e.printStackTrace();
        }

        DatabaseUtils.dumpCursor(contentResolver.query(ContractPlagas.CONTENT_URI_Explotacion, null, null, null, null));

        //NO existen. No hacer nada

    }

}

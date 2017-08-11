package es.orcelis.orcelis.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.orcelis.orcelis.provider.ContractParaUsuarios;

import static es.orcelis.orcelis.data.ContractPlagas.CABECERAS_CULTIVO;
import static es.orcelis.orcelis.data.ContractPlagas.CABECERAS_TIPO_CULTIVO;

/**
 * Created by ymontero on 09/05/2017.
 */

public class PlagasProvider extends ContentProvider{
    /**
     * Instancia global del Content Resolver
     */
    private ContentResolver resolver;
    private OpBaseDatosHelper datos;

    @Override
    public boolean onCreate() {
        datos = OpBaseDatosHelper.obtenerInstancia(getContext());
        resolver = getContext().getContentResolver();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Comparar Uri
        int match = ContractPlagas.uriMatcher.match(uri);

        Cursor c;

        c = datos.getDb().query(BaseDatosPlagas.Tablas.TIPOCULTIVO, OpBaseDatosHelper.proyTipoCultivo,
                selection, selectionArgs,
                null, null, sortOrder);
        c.setNotificationUri(resolver,ContractPlagas.CONTENT_URI_Usuario);

        switch (match) {
            case ContractParaUsuarios.ALLROWS:
                // Consultando todos los registros
                c = datos.getDb().query(BaseDatosPlagas.Tablas.TIPOCULTIVO, OpBaseDatosHelper.proyTipoCultivo,
                        selection, selectionArgs,
                        null, null, sortOrder);
                c.setNotificationUri(resolver,ContractPlagas.CONTENT_URI_Usuario);
                break;
            default:
                //throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return c;


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (ContractPlagas.uriMatcher.match(uri)) {
            case CABECERAS_TIPO_CULTIVO:
                return ContractPlagas.generarMime("tipo_cultivo");
            default:
                throw new UnsupportedOperationException("Uri desconocida =>" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = datos.getDb();
        Uri _uri = null;
        int match = ContractPlagas.uriMatcher.match(uri);
        switch (match){
            case CABECERAS_CULTIVO:
                long _ID1 = db.insert(BaseDatosPlagas.Tablas.CULTIVO, "", values);
                //---if added successfully---
                if (_ID1 > 0) {
                    _uri = ContentUris.withAppendedId(ContractPlagas.CONTENT_URI_Cultivo, _ID1);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    notificarCambio(uri);
                }
                break;
            case CABECERAS_TIPO_CULTIVO:
                db.insertOrThrow(BaseDatosPlagas.Tablas.TIPOCULTIVO, null, values);
                notificarCambio(uri);
                break;
        }


        //TODO crear cabecera pedido
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    //TODO echar un ojo a notifyChange a true
    private void notificarCambio(Uri uri) {
        resolver.notifyChange(uri, null);
    }
}

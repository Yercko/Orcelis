package es.orcelis.orcelis.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static es.orcelis.orcelis.data.ContractPlagas.CABECERAS_EXPLOTACION;
import static es.orcelis.orcelis.data.ContractPlagas.CABECERAS_TIPO_CULTIVO;
import static es.orcelis.orcelis.data.ContractPlagas.CABECERAS_USUARIO;

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
        if (getContext() != null) {
            resolver = getContext().getContentResolver();
        }

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Comparar Uri
        int match = ContractPlagas.uriMatcher.match(uri);
        Cursor c = null;

        switch (match) {
            case CABECERAS_USUARIO:
                c = datos.getDb().query(BaseDatosPlagas.Tablas.USUARIO, OpBaseDatosHelper.consultarUsuario,
                        selection, selectionArgs,null, null, sortOrder);
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
            case CABECERAS_USUARIO:
                insertarDato(db, ContractPlagas.CONTENT_URI_Usuario,BaseDatosPlagas.Tablas.USUARIO , values);
                break;
            case CABECERAS_EXPLOTACION:
                long _ID2 = db.insert(BaseDatosPlagas.Tablas.EXPLOTACION, "", values);
                if (_ID2 > 0) {
                    _uri = ContentUris.withAppendedId(ContractPlagas.CONTENT_URI_Cultivo, _ID2);
                    notificarCambio(_uri);
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
    /**
     * In case of a conflict when inserting the values, another update query is sent.
     * @param db     Database to insert to.
     * @param uri    Content provider uri.
     * @param table  Table to insert to.
     * @param values The values to insert to.
     */
    private boolean insertarDato(SQLiteDatabase db, Uri uri, String table,
                                 ContentValues values) {
        boolean resultado;
        long _ID1;
        try {
            _ID1 =  db.insertOrThrow(table, null, values);
            if (_ID1 > 0) {
                Uri _uri = ContentUris.withAppendedId(uri,_ID1);
                notificarCambio(_uri);
            }
            resultado = true;
        } catch (SQLiteConstraintException e) {
            int nrRows = 0;
                   // nrRows= update(uri, values, column + "=?",
                   // new String[]{values.getAsString(column)});
            if (nrRows == 0) {
                resultado = false;
            }
            else{
                resultado = true;
            }
        }

        return resultado;
    }
}

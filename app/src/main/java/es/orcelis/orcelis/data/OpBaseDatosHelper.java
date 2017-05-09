package es.orcelis.orcelis.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import es.orcelis.orcelis.models.TipoCultivo;

/**
 * Created by ymontero on 09/05/2017.
 */

public final class OpBaseDatosHelper {
    private static BaseDatosPlagas baseDatos;

    private static OpBaseDatosHelper instancia = new OpBaseDatosHelper();

    private OpBaseDatosHelper() {
    }

    public static OpBaseDatosHelper obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDatosPlagas(contexto);
        }
        return instancia;
    }


    public Cursor obtenerTipoCultivos() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(BaseDatosPlagas.Tablas.TIPOCULTIVO);

        return builder.query(db, proyTipoCultivo, null, null, null, null, null);
    }

    private final String[] proyTipoCultivo = new String[]{
                BaseDatosPlagas.Tablas.TIPOCULTIVO + "." + ContractPlagas.TipoCultivo.ID,
                ContractPlagas.TipoCultivo.NOMBRE,
            };

    public void insertarTipoCultivos(TipoCultivo tipoCultivo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContractPlagas.TipoCultivo.ID, tipoCultivo.id);
        valores.put(ContractPlagas.TipoCultivo.NOMBRE, tipoCultivo.nombre);

        db.insertOrThrow(BaseDatosPlagas.Tablas.TIPOCULTIVO, null, valores);

    }



    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }

}

package es.orcelis.orcelis.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by yercko on 07/05/2017.
 */

public class BaseDatosPlagas extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "plagas.db";
    private static final int VERSION_ACTUAL = 1;
    private final Context contexto;

    public  interface Tablas {
        String USUARIO = "Usuario";
        String TIPOCULTIVO = "TipoCultivo";
        String EXPLOTACION = "Explotacion";
        String CULTIVO = "Cultivo";
        String PUNTOS = "Puntos";
        String FOTOS = "Fotos";
        String PUNTOSFOTOS = "PuntosFotos";
        String TRIPS = "Trips";
        String PLAGA = "Plaga";
        String IMAGENPLAGA = "ImagenPlaga";
        String UNIDAD = "Unidad";
        String PUNTOSPLAGA = "PuntosPlaga";
        String TIPOCULTIVO_PLAGA = "TipoCultivo_Plaga";
        String PAIS = "Pais";
        String REGION = "Region";
    }

    public BaseDatosPlagas(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL, %s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL )",
                Tablas.USUARIO, ContractPlagas.Usuario._ID,
                ContractPlagas.Usuario.ID, ContractPlagas.Usuario.EMAIL, ContractPlagas.Usuario.PASSWORD, ContractPlagas.Usuario.TELEFONO,
                ContractPlagas.Usuario._UUID,ContractPlagas.Usuario.FECHA_FIN_USO));
        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL,%s TEXT NOT NULL )",
                Tablas.TIPOCULTIVO, BaseColumns._ID,
                ContractPlagas.TipoCultivo.ID, ContractPlagas.TipoCultivo.NOMBRE));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tablas.USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPOCULTIVO);
          /*
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.EXPLOTACION);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.CULTIVO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PUNTOS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.FOTOS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PUNTOSFOTOS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PLAGA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.IMAGENPLAGA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.UNIDAD);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PUNTOSPLAGA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPOCULTIVO_PLAGA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PAIS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.REGION);
*/
        onCreate(db);
    }



    private void loadDummyData(SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractPlagas.ColumnasTipoCultivo.ID,"yercko1");
        contentValues.put(ContractPlagas.ColumnasTipoCultivo.NOMBRE,"yercko2");
        database.insert(Tablas.TIPOCULTIVO, null, contentValues);


    }
}

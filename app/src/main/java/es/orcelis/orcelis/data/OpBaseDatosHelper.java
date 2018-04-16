package es.orcelis.orcelis.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }


    //proyeccion login
    public static final String[] consultarUsuario = new String[]{
            ContractPlagas.Usuario.ID,
            ContractPlagas.Usuario.EMAIL,
            ContractPlagas.Usuario.TELEFONO
    };

    //proyeccion Cultivos
    public static final String[] consultarCultivos = new String[]{
            BaseDatosPlagas.Tablas.USUARIO + "." + ContractPlagas.Usuario.ID,
            ContractPlagas.Usuario.EMAIL
    };

    //proyeccion Fotos
    public static final String[] consultarFotos = new String[]{
            BaseDatosPlagas.Tablas.INSPECCION + "." + ContractPlagas.Inspeccion.ID,
            ContractPlagas.Inspeccion.MIME
    };



}

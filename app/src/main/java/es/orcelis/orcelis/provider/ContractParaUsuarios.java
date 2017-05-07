package es.orcelis.orcelis.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract Class entre el provider y las aplicaciones
 */
public class ContractParaUsuarios {
    /**
     * Autoridad del Content Provider
     */
    public final static String AUTHORITY
            = "es.orcelis.orcelis";
    /**
     * Representación de la tabla a consultar
     */
    public static final String USUARIO = "usuario";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + AUTHORITY + USUARIO;
    /**
     * Tipo MIME que retorna la consulta de { CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + USUARIO;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + USUARIO);
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * Código para URIs de multiples registros
     */
    public static final int ALLROWS = 1;
    /**
     * Código para URIS de un solo registro
     */
    public static final int SINGLE_ROW = 2;


    // Asignación de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, USUARIO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, USUARIO + "/#", SINGLE_ROW);
    }

    // Valores para la columna ESTADO
    public static final int ESTADO_OK = 0;
    public static final int ESTADO_SYNC = 1;


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public final static String EMAIL = "email";
        public final static String PASSWORD = "password";
        public final static String UUID = "uuid";
        public final static String FECHA_FIN_USO = "fecha_fin_uso";

        public static final String ESTADO = "estado";
        public static final String ID_REMOTA = "idRemota";
        public final static String PENDIENTE_INSERCION = "pendiente_insercion";

    }
}

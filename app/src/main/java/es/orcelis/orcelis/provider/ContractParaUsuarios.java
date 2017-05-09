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
    /*
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, USUARIO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, USUARIO + "/#", SINGLE_ROW);
    }
    */

    // Valores para la columna ESTADO
    public static final int ESTADO_OK = 0;
    public static final int ESTADO_SYNC = 1;

    //TODO lo siguiente esta pensado para un Contract con multiples tablas
    // Casos
    public static final int CABECERAS_TIPO_CULTIVO = 100;
    public static final int CABECERAS_TIPO_CULTIVO_ID = 101;
    /*
    public static final int CABECERAS_ID_DETALLES = 102;

    public static final int DETALLES_PEDIDOS = 200;
    public static final int DETALLES_PEDIDOS_ID = 201;

    public static final int PRODUCTOS = 300;
    public static final int PRODUCTOS_ID = 301;

    public static final int CLIENTES = 400;
    public static final int CLIENTES_ID = 401;

    public static final int FORMAS_PAGO = 500;
    public static final int FORMAS_PAGO_ID = 501;
    */

    public static final String AUTORIDAD = "es.orcelis.orcelis";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTORIDAD, "tipo_cultivo", CABECERAS_TIPO_CULTIVO);
        uriMatcher.addURI(AUTORIDAD, "tipo_cultivo/*", CABECERAS_TIPO_CULTIVO_ID);

        /*
        uriMatcher.addURI(AUTORIDAD, "detalles_pedidos", DETALLES_PEDIDOS);
        uriMatcher.addURI(AUTORIDAD, "detalles_pedidos/*", DETALLES_PEDIDOS_ID);

        uriMatcher.addURI(AUTORIDAD, "productos", PRODUCTOS);
        uriMatcher.addURI(AUTORIDAD, "productos/*", PRODUCTOS_ID);

        uriMatcher.addURI(AUTORIDAD, "clientes", CLIENTES);
        uriMatcher.addURI(AUTORIDAD, "clientes/*", CLIENTES_ID);

        uriMatcher.addURI(AUTORIDAD, "formas_pago", FORMAS_PAGO);
        uriMatcher.addURI(AUTORIDAD, "formas_pago/*", FORMAS_PAGO_ID);
        */
    }

    public static final String BASE_CONTENIDOS = "plagas.";
    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd."
            + BASE_CONTENIDOS;

    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd."
            + BASE_CONTENIDOS;
    public static String generarMime(String id) {
        if (id != null) {
            return TIPO_CONTENIDO + id;
        } else {
            return null;
        }
    }

    public static String generarMimeItem(String id) {
        if (id != null) {
            return TIPO_CONTENIDO_ITEM + id;
        } else {
            return null;
        }
    }

}

package es.orcelis.orcelis.utils;

/**
 * Created by yercko on 06/05/2017.
 */

public class Constantes {
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta característica.
     */
    private static final String PUERTO_HOST = ":8080";
    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "http://192.168.1.180";
    /**
     * URLs del Web Service
     */
    public static final String GET_URL = IP + PUERTO_HOST + "/servicio%20web/web/obtener_gastos.php";
    public static final String INSERT_URL = IP + PUERTO_HOST + "/servicio%20web/web/insertar_gasto.php";
    /**
     * Códigos del campo  ESTADO
     */
    public static final String SUCCESS = "1";
    public static final String FAILED = "2";

    /**
     * Tipo de cuenta para la sincronización
     */
    public static final String ACCOUNT_TYPE = "es.orcelis.orcelis.account";

    public static final String USUARIO = "Usuario";


}

package es.orcelis.orcelis.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.UUID;

/**
 * Created by yercko on 06/05/2017.
 */

public class ContractPlagas {
    /**
     * Autoridad del Content Provider
     */
    public final static String AUTORIDAD
            = "es.orcelis.orcelis";
    /**
     * URI de contenido principal
     */
    public static Uri CONTENT_URI_Usuario = Uri.parse("content://" + AUTORIDAD + "/"+"Usuario");
    public static Uri CONTENT_URI_TipoCultivo = Uri.parse("content://" + AUTORIDAD + "/"+"TipoCultivo");
    public static Uri CONTENT_URI_Explotacion = Uri.parse("content://" + AUTORIDAD + "/"+"Explotacion");
    public static Uri CONTENT_URI_Cultivo = Uri.parse("content://" + AUTORIDAD + "/"+"Cultivo");
    public static Uri CONTENT_URI_Puntos = Uri.parse("content://" + AUTORIDAD + "/"+"Puntos");
    public static Uri CONTENT_URI_Fotos = Uri.parse("content://" + AUTORIDAD + "/"+"Fotos");
    public static Uri CONTENT_URI_PuntosFotos = Uri.parse("content://" + AUTORIDAD + "/"+"PuntosFotos");
    public static Uri CONTENT_URI_Trips = Uri.parse("content://" + AUTORIDAD + "/"+"Trips");
    public static Uri CONTENT_URI_Plaga = Uri.parse("content://" + AUTORIDAD + "/"+"Plaga");
    public static Uri CONTENT_URI_ImagenPlaga = Uri.parse("content://" + AUTORIDAD + "/"+"ImagenPlaga");
    public static Uri CONTENT_URI_Unidad = Uri.parse("content://" + AUTORIDAD + "/"+"Unidad");
    public static Uri CONTENT_URI_PuntosPlaga = Uri.parse("content://" + AUTORIDAD + "/"+"PuntosPlaga");
    public static Uri CONTENT_URI_TipoCultivo_Plaga = Uri.parse("content://" + AUTORIDAD + "/"+"TipoCultivo_Plaga");
    public static Uri CONTENT_URI_Pais = Uri.parse("content://" + AUTORIDAD + "/"+"Pais");
    public static Uri CONTENT_URI_Region = Uri.parse("content://" + AUTORIDAD + "/"+"Region");
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;


    public static final int CABECERAS_TIPO_CULTIVO = 100;
    public static final int CABECERAS_TIPO_CULTIVO_ID = 101;
    public static final int CABECERAS_CULTIVO = 200;
    public static final int CABECERAS_CULTIVO_ID = 201;
    public static final int CABECERAS_USUARIO = 200;
    public static final int CABECERAS_USUARIO_ID = 201;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTORIDAD, BaseDatosPlagas.Tablas.TIPOCULTIVO+"/", CABECERAS_TIPO_CULTIVO);
        uriMatcher.addURI(AUTORIDAD, BaseDatosPlagas.Tablas.TIPOCULTIVO+"/*", CABECERAS_TIPO_CULTIVO_ID);
        uriMatcher.addURI(AUTORIDAD, BaseDatosPlagas.Tablas.CULTIVO, CABECERAS_CULTIVO);
        uriMatcher.addURI(AUTORIDAD, BaseDatosPlagas.Tablas.CULTIVO+"/*", CABECERAS_CULTIVO_ID);
        uriMatcher.addURI(AUTORIDAD, BaseDatosPlagas.Tablas.USUARIO, CABECERAS_USUARIO);
        uriMatcher.addURI(AUTORIDAD, BaseDatosPlagas.Tablas.USUARIO+"/*", CABECERAS_USUARIO_ID);

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

    interface ColumnasUsuario extends BaseColumns{
        String ID = "id";
        String EMAIL = "email";
        String PASSWORD = "password";
        String TELEFONO = "telefono";
        String _UUID = "uuid";
        String FECHA_FIN_USO = "fecha_fin_uso";
    }

    interface ColumnasTipoCultivo  extends BaseColumns{
        String ID = "id";
        String NOMBRE = "nombre";
    }

    interface ColumnasExplotacion extends BaseColumns{
        String ID = "id";
        String NOMBRE = "nombre";
        String TOKEN = "token";
    }

    interface ColumnasCultivo extends BaseColumns{
        String ID = "id";
        String GEOJSON = "geojson";
        String NOMBRE = "nombre";
    }

    interface ColumnasPuntos extends BaseColumns{
        String ID = "id";
        String LAT = "lat";
        String LON = "lon";
        String ID_TRIP = "id_trip";         //clave ajena
        String FECHA_HORA = "fecha_hora";
    }

    interface ColumnasFotos extends BaseColumns{
        String ID = "id";       //id por codigo
        String GRAVEDAD = "gravedad";
        String NOTA = "nota";       //atento
        String MIME = "mime";   //atento y opcional
    }

    interface ColumnasPuntosFotos extends BaseColumns{
        String ID = "id";
        String ID_FOTO = "id_foto";
        String ID_PUNTO = "id_punto";
    }

    interface ColumnasTrips  extends BaseColumns{
        String ID = "id";
        String NOMBRE = "nombre";
        String FECHA_HORA_INICIO = "fecha_hora_inicio";
        String FECHA_HORA_FIN = "fecha_hora_fin";
        //claves ajenas
        String ID_USUARIO = "id_usuario";
        String ID_TIPO_CULTIVO = "id_tipo_cultivo";
        String ID_EXPLOTACION = "id_explotation";
        String ID_CULTIVO = "id_cultivo";
    }

    interface ColumnasPlaga extends BaseColumns {
        String ID = "id";
        String NOMBRE = "nombre";
        String NOMBRE_LATIN = "nombre_latin";
        String SINTOMAS = "sintomas";
        String DESCRIPCION = "descripcion";
    }

    interface ColumnasImagenPlaga extends BaseColumns{
        String ID = "id";
        String MIME = "mime";
        //ajena
        String ID_PLAGA = "id_plaga";
    }

    interface ColumnasUnidad  extends BaseColumns{
        String ID = "id";
        String NOMBRE = "nombre";
    }

    interface ColumnasPuntosPlaga  extends BaseColumns{
        String ID = "id";
        String ID_PUNTO = "id_punto";
        String ID_PROBLEMA = "id_problema";
        String CANTIDAD = "cantidad";
        //ajena
        String ID_UNIDAD = "id_unidad";
    }

    interface ColumnasTipoCultivo_Plaga  extends BaseColumns{
        String ID = "id";
        String ID_TIPO_CULTIVO = "id_tipo_cultivo";
        String ID_PLAGA = "id_plaga";
        //clave ajena
        String ID_PAIS = "id_pais";
        String ID_REGION = "id_region";
    }

    interface ColumnasPais  extends BaseColumns{
        String ID = "id";
        String NOMBRE = "nombre";
    }

    interface ColumnasRegion extends BaseColumns{
        String ID = "id";
        String NOMBRE = "nombre";
    }


    public static class Usuario implements ColumnasUsuario {
        public static String generarIdUsuario() {
            return "US-" +  UUID.randomUUID().toString();
        }
    }

    public static class TipoCultivo implements ColumnasTipoCultivo{
        public static String generarIdTipoCultivo(){
            return "TC"+ UUID.randomUUID().toString();
        }
    }
    public static class Explotacion implements ColumnasExplotacion{
        public static String generarIdExplotacion(){
            return "EX"+ UUID.randomUUID().toString();
        }
    }
    public static class Cultivo implements ColumnasCultivo{
        public static String generarIdCultivo(){
            return "CU"+ UUID.randomUUID().toString();
        }
    }
    public static class Puntos implements ColumnasPuntos{
        public static String generarIdPuntos(){
            return "PU"+ UUID.randomUUID().toString();
        }
    }
    public static class Fotos implements ColumnasFotos{
        public static String generarIdFotos(){
            return "FO"+ UUID.randomUUID().toString();
        }
    }
    public static class PuntosFotos implements ColumnasPuntosFotos{
        public static String generarIdPuntosFotos(){
            return "PF"+ UUID.randomUUID().toString();
        }
    }
    public static class Trips implements ColumnasTrips{
        public static String generarIdTrips(){
            return "TR"+ UUID.randomUUID().toString();
        }
    }
    public static class Plaga implements ColumnasPlaga{
        public static String generarIdPlaga(){
            return "PL"+ UUID.randomUUID().toString();
        }
    }
    public static class ImagenPlaga implements ColumnasImagenPlaga{
        public static String generarIdImagenPlaga(){
            return "IP"+ UUID.randomUUID().toString();
        }
    }
    public static class Unidad implements ColumnasUnidad{
        public static String generarIdUnidad(){
            return "UN"+ UUID.randomUUID().toString();
        }
    }
    public static class PuntosPlaga implements ColumnasPuntosPlaga{
        public static String generarIdPuntosPlaga(){
            return "PP"+ UUID.randomUUID().toString();
        }
    }
    public static class TipoCultivo_Plaga implements ColumnasTipoCultivo_Plaga{
        public static String generarIdTipoCultivo_Plaga(){
            return "TCP"+ UUID.randomUUID().toString();
        }
    }
    public static class Pais implements ColumnasPais{
        public static String generarIdPais(){
            return "PA"+ UUID.randomUUID().toString();
        }
    }
    public static class Region implements ColumnasRegion{
        public static String generarIdRegion(){
            return "RE"+ UUID.randomUUID().toString();
        }
    }

}

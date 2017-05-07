package es.orcelis.orcelis.models;

/**
 * Created by yercko on 07/05/2017.
 */

public class TipoCultivo_Plaga {
    public String id;
    public String id_tipo_cultivo;
    public String id_plaga;
    public String id_pais;
    public String id_region;

    public TipoCultivo_Plaga(String id, String id_tipo_cultivo, String id_plaga, String id_pais, String id_region) {
        this.id = id;
        this.id_tipo_cultivo = id_tipo_cultivo;
        this.id_plaga = id_plaga;
        this.id_pais = id_pais;
        this.id_region = id_region;
    }
}

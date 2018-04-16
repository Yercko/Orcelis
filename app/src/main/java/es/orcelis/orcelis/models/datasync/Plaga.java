package es.orcelis.orcelis.models.datasync;

import java.util.List;

/**
 * Created by yercko on 07/05/2017.
 */

public class Plaga {
    public String id;
    public String pais;
    public String region;
    public String nombre_comun;
    public String nombre_cientifico;
    public String tipo_problema;
    public String seguimiento_riesgo;
    public String umbral;
    public String medidas_alternativas;
    public String medidas_alt_quimico;
    public List<String> fotos;


    public Plaga(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNombre_comun() {
        return nombre_comun;
    }

    public void setNombre_comun(String nombre_comun) {
        this.nombre_comun = nombre_comun;
    }

    public String getNombre_cientifico() {
        return nombre_cientifico;
    }

    public void setNombre_cientifico(String nombre_cientifico) {
        this.nombre_cientifico = nombre_cientifico;
    }

    public String getTipo_problema() {
        return tipo_problema;
    }

    public void setTipo_problema(String tipo_problema) {
        this.tipo_problema = tipo_problema;
    }

    public String getSeguimiento_riesgo() {
        return seguimiento_riesgo;
    }

    public void setSeguimiento_riesgo(String seguimiento_riesgo) {
        this.seguimiento_riesgo = seguimiento_riesgo;
    }


    public String getUmbral() {
        return umbral;
    }

    public void setUmbral(String umbral) {
        this.umbral = umbral;
    }

    public String getMedidas_alternativas() {
        return medidas_alternativas;
    }

    public void setMedidas_alternativas(String medidas_alternativas) {
        this.medidas_alternativas = medidas_alternativas;
    }

    public String getMedidas_alt_quimico() {
        return medidas_alt_quimico;
    }

    public void setMedidas_alt_quimico(String medidas_alt_quimico) {
        this.medidas_alt_quimico = medidas_alt_quimico;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}

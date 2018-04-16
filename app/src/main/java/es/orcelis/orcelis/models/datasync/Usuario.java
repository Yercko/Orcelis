package es.orcelis.orcelis.models.datasync;

import java.util.List;


/**
 * Created by ymontero on 05/05/2017.
 */

public class Usuario {
    public String id;
    public String email;
    public String password;
    public String telefono;
    public String uuid;
    public Pais pais;
    public String fecha_fin_uso;
    public List<Explotacion> explotaciones;

    public Usuario(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getFecha_fin_uso() {
        return fecha_fin_uso;
    }

    public void setFecha_fin_uso(String fecha_fin_uso) {
        this.fecha_fin_uso = fecha_fin_uso;
    }

    public List<Explotacion> getExplotaciones() {
        return explotaciones;
    }

    public void setExplotaciones(List<Explotacion> explotaciones) {
        this.explotaciones = explotaciones;
    }
}

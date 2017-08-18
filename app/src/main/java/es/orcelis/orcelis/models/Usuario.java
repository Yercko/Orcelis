package es.orcelis.orcelis.models;

/**
 * Created by ymontero on 05/05/2017.
 */

public class Usuario {
    public String id;
    public String email;
    public String password;
    public String telefono;
    public String uuid;
    public String pais;
    public String fecha_fin_uso;

    public Usuario(String id, String email, String password, String telefono, String uuid, String pais, String fecha_fin_uso) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.uuid = uuid;
        this.pais = pais;
        this.fecha_fin_uso = fecha_fin_uso;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFecha_fin_uso() {
        return fecha_fin_uso;
    }

    public void setFecha_fin_uso(String fecha_fin_uso) {
        this.fecha_fin_uso = fecha_fin_uso;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", uuid='" + uuid + '\'' +
                ", pais='" + pais + '\'' +
                ", fecha_fin_uso='" + fecha_fin_uso + '\'' +
                '}';
    }
}

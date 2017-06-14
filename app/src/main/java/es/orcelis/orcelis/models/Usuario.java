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
}

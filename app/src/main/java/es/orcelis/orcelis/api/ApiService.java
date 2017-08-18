package es.orcelis.orcelis.api;

import java.util.HashMap;
import java.util.List;

import es.orcelis.orcelis.models.Token;
import es.orcelis.orcelis.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by karol on 16/08/2017.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("/rest/login")
    Call<Token> loginUsuario(@FieldMap HashMap<String, String> body);
}

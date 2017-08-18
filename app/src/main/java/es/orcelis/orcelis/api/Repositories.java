package es.orcelis.orcelis.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;

import es.orcelis.orcelis.models.Token;
import es.orcelis.orcelis.operations.login.ILoginUI;
import es.orcelis.orcelis.utils.Constantes;
import es.orcelis.orcelis.utils.UserData;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karol on 16/08/2017.
 */

public class Repositories {
    private static Repositories instance;
    private Retrofit retrofit;
    private ApiService api;
    public Repositories(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.REMOTE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(clientBuilder.build())
                .build();
        api = retrofit.create(ApiService.class);

    }


    public void loginRequest(final Context context, final ILoginUI iLoginUI, String username, String password){
        HashMap<String, String> body = new HashMap<String,String>();
        body.put("grant_type","password");
        body.put("username",username);
        body.put("password",password);

        Call<Token> call = api.loginUsuario(body);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
                switch (response.code()) {
                    case 200:
                        Token token = response.body();
                        UserData.getInstance(context).setToken(token);
                        UserData.getInstance(context).setLogueado(true);
                        iLoginUI.OnLoginSucces(true);
                        //view.notifyDataSetChanged(data.getResults());
                        break;
                    case 401:
                        UserData.getInstance(context).setToken(null);
                        UserData.getInstance(context).setLogueado(false);
                        iLoginUI.OnLoginSucces(false);
                        Log.v("completado","Se ha acabado la sesion");
                        break;
                    default:
                        Log.v("completado","Peticion completada sin exito");
                    break;
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }



}
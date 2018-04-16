package es.orcelis.orcelis.operations.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cocoahero.android.geojson.Feature;
import com.cocoahero.android.geojson.Point;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.orcelis.orcelis.BaseActivity;
import es.orcelis.orcelis.R;
import es.orcelis.orcelis.api.Repositories;
import es.orcelis.orcelis.models.datasync.Cultivo;
import es.orcelis.orcelis.models.datasync.Explotacion;
import es.orcelis.orcelis.models.datasync.Inspeccion;
import es.orcelis.orcelis.models.datasync.Pais;
import es.orcelis.orcelis.models.datasync.Plaga;
import es.orcelis.orcelis.models.datasync.TipoCultivo;
import es.orcelis.orcelis.models.datasync.Trip;
import es.orcelis.orcelis.models.datasync.Usuario;
import es.orcelis.orcelis.operations.dashboard.MainActivity;
import es.orcelis.orcelis.utils.DialogManager;

/**
 * Created by ymontero on 04/05/2017.
 */

public class LoginActivity extends BaseActivity implements ILoginUI{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private static final int RC_SIGN_IN = 123;

    private FirebaseAuth mAuth;

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        _emailText.setText("yercko@gmail.com");
        _passwordText.setText("sadsadsa");

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Create and launch sign-in intent
        if (currentUser == null ) {
            mAuth.signInWithEmailAndPassword("prueba@orcelis.com", "bi8QFIlltbyg6mAkEgBqtk7afNk=")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else{

        }
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }



    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        DialogManager.getDialogDefault(this,getString(R.string.autenticando));

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        _loginButton.setEnabled(true);

    }


    //lo primero que debe de ir para comprobar si el usuario ya se logueo anteriormente
    public void inicializar_usuario(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public void OnLoginSucces(boolean correcto) {
        //manejo de peticion OK
        //lanzar Asynctask con insercion de los datos
        //new TareaPruebaDatos(this).execute();
        //manejo de KO
        //onLoginFailed
        DialogManager.ocultarDialogDefault();

        if (correcto) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else{
            Snackbar.make(_loginButton, getString(R.string.msg_login_error), Snackbar.LENGTH_LONG)
                    .show();
        }



    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.login_falla), Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getString(R.string.email_valido));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(getString(R.string.numero_caracteres));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}

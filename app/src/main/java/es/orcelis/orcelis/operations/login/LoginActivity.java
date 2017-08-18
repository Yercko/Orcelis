package es.orcelis.orcelis.operations.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.orcelis.orcelis.BaseActivity;
import es.orcelis.orcelis.R;
import es.orcelis.orcelis.api.Repositories;
import es.orcelis.orcelis.data.ContractPlagas;
import es.orcelis.orcelis.operations.dashboard.MainActivity;
import es.orcelis.orcelis.utils.DialogManager;

/**
 * Created by ymontero on 04/05/2017.
 */

public class LoginActivity extends BaseActivity implements ILoginUI{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

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
        //lanzar la peticion
        Repositories repos = new Repositories();
        repos.loginRequest(this,this,email,password);
        /*
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();

                    }
                }, 3000);
                */
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

    public class TareaPruebaDatos extends AsyncTask<Void, Void, Void> {
        Activity activity;
        public TareaPruebaDatos(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ContentResolver contentResolver = getContentResolver();
            // Lista de operaciones
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            ops.add(ContentProviderOperation.newInsert(ContractPlagas.CONTENT_URI_Usuario)
                    .withValue(ContractPlagas.Usuario.ID, "2")
                    .withValue(ContractPlagas.Usuario.EMAIL,"email")
                    .withValue(ContractPlagas.Usuario.PASSWORD,"dsadsdaa")
                    .withValue(ContractPlagas.Usuario.TELEFONO,"231321")
                    .build());
            try {
                contentResolver.applyBatch(ContractPlagas.AUTORIDAD, ops);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }
            Log.d("Clientes", "Clientes");
            DatabaseUtils.dumpCursor(contentResolver.query(ContractPlagas.CONTENT_URI_Usuario, null, null, null, null));
            //Columnas de la tabla a recuperar
            /*
            String[] projection = new String[] {
                    Clientes._ID,
                    Clientes.COL_NOMBRE,
                    Clientes.COL_TELEFONO,
                    Clientes.COL_EMAIL };


                ContentResolver cr = getContentResolver();

                //Hacemos la consulta
                Cursor cur = cr.query(clientesUri,
                    projection, //Columnas a devolver
                    null,       //Condición de la query
                    null,       //Argumentos variables de la query
                    null);      //Orden de los resultados
*/

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



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

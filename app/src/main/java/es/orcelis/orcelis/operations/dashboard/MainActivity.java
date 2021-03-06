package es.orcelis.orcelis.operations.dashboard;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import es.orcelis.orcelis.BaseActivity;
import es.orcelis.orcelis.R;
import es.orcelis.orcelis.data.ContractPlagas;
import es.orcelis.orcelis.data.OpBaseDatosHelper;
import es.orcelis.orcelis.operations.configuracion.ConfiguracionFragment;
import es.orcelis.orcelis.operations.explotaciones.ExplotacionFragment;
import es.orcelis.orcelis.operations.historial.HistorialItemFragment;
import es.orcelis.orcelis.operations.historial.dummy.DummyContent;
import es.orcelis.orcelis.utils.UserData;

import static es.orcelis.orcelis.utils.Constantes.ACCOUNT_TYPE;

public class MainActivity extends BaseActivity implements HistorialItemFragment.OnListFragmentInteractionListener {

    private TextView mTextMessage;
    Account mAccount;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_parcelas:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, ExplotacionFragment.newInstance(), ExplotacionFragment.TAG)
                            .commit();
                    return true;
                case R.id.navigation_informes:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, HistorialItemFragment.newInstance(1), HistorialItemFragment.TAG)
                            .commit();
                    return true;
                case R.id.navigation_configuracion:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, ConfiguracionFragment.newInstance(), ConfiguracionFragment.TAG)
                            .commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //getApplicationContext().deleteDatabase("pedidos.db");
        //datos = OpBaseDatosHelper
        //        .obtenerInstancia(getApplicationContext());
        new SyncronizadorHelper(this).usuarioShoot(UserData.getInstance(this).getUsername());



        mAccount = CreateSyncAccount(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, ExplotacionFragment.newInstance(), ExplotacionFragment.TAG)
                .commit();
    }

    OpBaseDatosHelper datos;

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }




    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }
}

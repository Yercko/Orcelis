package es.orcelis.orcelis;

import android.content.ContentResolver;
import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import es.orcelis.orcelis.data.ContractPlagas;
import es.orcelis.orcelis.data.OpBaseDatosHelper;
import es.orcelis.orcelis.data.PlagasProvider;
import es.orcelis.orcelis.models.TipoCultivo;
import es.orcelis.orcelis.provider.ContractParaUsuarios;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_parcelas:
                    mTextMessage.setText(R.string.title_parcelas);
                    return true;
                case R.id.navigation_informes:
                    mTextMessage.setText(R.string.title_parcelas);
                    return true;
                case R.id.navigation_configuracion:
                    mTextMessage.setText(R.string.title_configuracion);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //getApplicationContext().deleteDatabase("pedidos.db");
        //datos = OpBaseDatosHelper
        //        .obtenerInstancia(getApplicationContext());
        //new TareaPruebaDatos().execute();


    }


    OpBaseDatosHelper datos;

    public class TareaPruebaDatos extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            /*
            try {
                datos.getDb().beginTransaction();
                // Inserción Clientes
                datos.insertarTipoCultivos(new TipoCultivo("1", "Veronica"));
                datos.insertarTipoCultivos(new TipoCultivo("2", "Yercko"));

                datos.getDb().setTransactionSuccessful();
            } finally {
                datos.getDb().endTransaction();
            }

            // [QUERIES]
            Log.d("Tipos de cultivos","Tipos de cultivos");
            DatabaseUtils.dumpCursor(datos.obtenerTipoCultivos());

            */

            ContentResolver contentResolver = getContentResolver();
            // Lista de operaciones
            ArrayList<PlagasProvider> ops = new ArrayList<>();
            // [QUERIES]
            Log.d("Clientes", "Clientes");
            DatabaseUtils.dumpCursor(contentResolver.query(ContractParaUsuarios.CONTENT_URI, null, null, null, null));

            return null;
        }
    }
}

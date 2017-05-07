package es.orcelis.orcelis.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Bound service para gestionar la autenticación
 */
public class AuthenticationService extends Service {

    // Instancia del autenticador
    private OrcelisAuthenticator autenticador;

    @Override
    public void onCreate() {
        // Nueva instancia del autenticador
        autenticador = new OrcelisAuthenticator(this);
    }

    /*
     * Ligando el servicio al framework de Android
     */
    @Override
    public IBinder onBind(Intent intent) {
        return autenticador.getIBinder();
    }
}
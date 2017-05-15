package es.orcelis.orcelis.operations.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import es.orcelis.orcelis.R;
import es.orcelis.orcelis.operations.cultivos.TripsFragment;
import es.orcelis.orcelis.operations.explotaciones.ExplotacionFragment;
import es.orcelis.orcelis.operations.explotaciones.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements ExplotacionFragment.OnListFragmentInteractionListener {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_parcelas:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, ExplotacionFragment.newInstance(1), ExplotacionFragment.TAG)
                            .commit();
                    return true;
                case R.id.navigation_informes:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, TripsFragment.newInstance(), TripsFragment.TAG)
                            .commit();
                    return true;
                case R.id.navigation_configuracion:

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
    }

    @Override
    public void onListFragmentInteraction(DummyContent.ParentItem item) {
        Log.v("pulsado","se ha pulsado sobre el elemento tal");
    }
}

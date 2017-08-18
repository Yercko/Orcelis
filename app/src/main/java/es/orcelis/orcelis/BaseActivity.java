package es.orcelis.orcelis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import es.orcelis.orcelis.utils.UserData;

/**
 * Created by ymontero on 18/08/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserData.getInstance(this).refresh(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        UserData.getInstance(this).save(this);


    }
}

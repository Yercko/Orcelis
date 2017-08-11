package es.orcelis.orcelis.operations.splash;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import es.orcelis.orcelis.R;
import es.orcelis.orcelis.operations.login.LoginActivity;

/**
 * Created by karol on 07/08/2017.
 */

public class SplashScreenActivity extends AwesomeSplash {
    @Override
    public void initSplash(ConfigSplash configSplash) {


        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.primary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.ic_launcher); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1500); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeInDown); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title

        configSplash.setTitleSplash("Bienvenid@");
        configSplash.setAnimTitleDuration(700);
        configSplash.setAnimTitleTechnique(Techniques.BounceInUp);

        /*
        configSplash.setTitleTextColor(R.color.black);
        configSplash.setTitleTextSize(30f); //float value
        */

    }

    @Override
    public void animationsFinished() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

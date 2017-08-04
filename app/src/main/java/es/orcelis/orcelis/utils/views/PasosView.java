package es.orcelis.orcelis.utils.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import es.orcelis.orcelis.R;

/**
 * Created by ymontero on 17/05/2017.
 */

public class PasosView extends LinearLayout {
    /**
     * Estados posibles:
     * 0 : Bola completada
     * 1 : Bola actual
     * 2 : Bola sin visitar
     */
    private enum estado{COMPLETADO,ACTUAL,SIN_VISITAR};
    private LinearLayout paso2_background;
    private LinearLayout paso3_background;
    private ImageView bola1;
    private ImageView bola2;
    private ImageView bola3;
    private ImageView close_pasos_seguir;

    private int paso_actual;

    public PasosView(Context context) {
        super(context);
        init(context);
    }

    public PasosView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        paso_actual = 0;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contenedor =  inflater.inflate(R.layout.toolbar_trip_process, this, true);
        //inicializar layout como sin visitar
        paso2_background = (LinearLayout)findViewById(R.id.paso2);
        paso3_background = (LinearLayout)findViewById(R.id.paso3);
        bola1 = (ImageView)findViewById(R.id.bola1);
        bola2 = (ImageView)findViewById(R.id.bola2);
        bola3 = (ImageView)findViewById(R.id.bola3);
        //close button
        close_pasos_seguir = (ImageView)findViewById(R.id.btn_close_pasos);
    }

    public void siguiente(){
        paso_actual++;

        if (paso_actual == 1){
            //bola anterior Estado visitada
            bola1.setBackground(getResources().getDrawable(R.drawable.shape_circle));
            bola1.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_white_24dp));
            //bola actual
            bola2.setBackground(getResources().getDrawable(R.drawable.shape_border));
            //fondo como visitado
            paso2_background.setBackgroundColor(getResources().getColor(R.color.primary_darker));

        }
        else if(paso_actual == 2){
            //bola anterior Estado visitada
            bola2.setBackground(getResources().getDrawable(R.drawable.shape_circle));
            bola2.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_white_24dp));
            //bola actual
            bola3.setBackground(getResources().getDrawable(R.drawable.shape_border));
            //fondo como visitado
            paso3_background.setBackgroundColor(getResources().getColor(R.color.primary_darker));
        }
    }

    public void anterior(){
        paso_actual--;

        if (paso_actual == 0){
            //bola anterior Estado visitada
            bola1.setBackground(getResources().getDrawable(R.drawable.shape_border));
            bola1.setImageResource(android.R.color.transparent);
            //bola actual
            bola2.setBackground(getResources().getDrawable(R.drawable.shape_circle_gris));
            //fondo como visitado
            paso2_background.setBackgroundColor(getResources().getColor(R.color.base));

        }
        else if(paso_actual == 1){
            //bola anterior Estado visitada
            bola2.setBackground(getResources().getDrawable(R.drawable.shape_border));
            bola2.setImageResource(android.R.color.transparent);
            //bola actual
            bola3.setBackground(getResources().getDrawable(R.drawable.shape_circle_gris));
            //fondo como visitado
            paso3_background.setBackgroundColor(getResources().getColor(R.color.base));
        }
    }
    public void changecheck(boolean activar, int posicion){
        if (posicion == 0){
            //bola anterior Estado visitada
            bola1.setBackground(getResources().getDrawable(R.drawable.shape_circle));
            bola1.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_white_24dp));
        } else if (posicion == 1) {
            //bola anterior Estado visitada
            bola2.setBackground(getResources().getDrawable(R.drawable.shape_circle));
            bola2.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_white_24dp));
        }
    }

    public void setListenerClose(OnClickListener onClickListener){
        close_pasos_seguir.setOnClickListener(onClickListener);
    }


    public int getPasoActual(){
        return paso_actual;
    }
}

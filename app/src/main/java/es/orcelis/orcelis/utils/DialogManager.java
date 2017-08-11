package es.orcelis.orcelis.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import es.orcelis.orcelis.R;

/**
 * Created by ymontero on 23/05/2017.
 */

public class DialogManager {

    /**
     * Current alert dialog
     */
    private static AlertDialog currentAlertDialog = null;
    private static ProgressDialog progressDialog = null;
    /**
     * Current dialog
     */
    private static Dialog currentDialog = null;

    /**
     * Flag
     */
    private static boolean hideProgressDialog = false;


    /**
     * Default
     */
    public DialogManager(){
        super();
    }

    /**
     * Open progress dialog
     * @param context - app context
     */
    public static void getProgressDialog(final Context context){
        hideProgressDialog = false;

        // Mostramos la alerta en el hilo principal
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {

            @Override
            public void run() {
                if (!hideProgressDialog) {
                    //inflate view
                    final LinearLayout llLoading = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.lay_full_screen_progress_dialog, null);

                    ProgressBar progBar = (ProgressBar) llLoading.findViewById(R.id.pgWZKbar);
                    if (progBar != null) {
                        progBar.setVisibility(View.VISIBLE);
                        progBar.setIndeterminate(true);
                        progBar.getIndeterminateDrawable()
                                .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }

                    //inflate view
                    LayoutInflater inflater = LayoutInflater.from(context);
                    RelativeLayout rlDialogFullScreen = (RelativeLayout) inflater.inflate(R.layout.lay_dialog_fullscreen, null);

                    int w = context.getResources().getDisplayMetrics().widthPixels;
                    int h = context.getResources().getDisplayMetrics().heightPixels;

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w, h);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);

                    rlDialogFullScreen.addView(llLoading, params);

                    // Ocultamos algun dialogo anterior
                    DialogManager.hideCurrentDialog();

                    // Configuramos el dialogo
                    final Dialog dialog = new Dialog(context, R.style.LoadingTheme);
                    dialog.setContentView(rlDialogFullScreen);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);

                    // Mostramos la alerta
                    currentDialog = dialog;
                    currentDialog.show();
                }
            }
        });
    }

    /**
     * Oculta el dialogo actual en pantalla
     */
    public static void hideCurrentDialog() {

        try {

            hideProgressDialog = true;

            if (currentAlertDialog != null && currentAlertDialog.isShowing()) {
                currentAlertDialog.dismiss();
                currentAlertDialog.hide();
            }

            currentAlertDialog = null;


            if (currentDialog != null && currentDialog.isShowing()) {
                currentDialog.dismiss();
            }

            currentDialog = null;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            currentDialog = null;
            currentAlertDialog = null;
        }
    }


    public static void getDialogDefault(Activity activity,String mensaje){
        progressDialog = new ProgressDialog(activity,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(mensaje);
        progressDialog.show();
    }

    public static void ocultarDialogDefault(){
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog.hide();
            }
            progressDialog = null;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            progressDialog = null;
        }
    }

    /**
     * Muestra un dialogo de alerta
     * @param context - contexto de la aplicacion
     * @param title - titulo del dialogo
     * @param mensaje - mensaje del dialogo
     * @param buttonAcceptTitle - titulo del boton positivo. Si viene a null no se muestra boton
     * @param buttonCancelTittle - titulo del boton negativo. Si viene a null no se muestra boton
     * @param buttonAcceptListener - listener del boton positivo. Si viene a null no se meustra boton
     * @param buttonCancelListener - listener del boton negativo. Si viene a null no se meustra boton
     */
    public static void getTwoButtonAlertDialog(final Context context, String title, String mensaje, String buttonAcceptTitle, String buttonCancelTittle, DialogInterface.OnClickListener buttonAcceptListener, DialogInterface.OnClickListener buttonCancelListener) {
        DialogManager.hideCurrentDialog();

        // Configuramos el dialogo
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(mensaje);
        alertDialog.setCancelable(false);

        if (buttonAcceptTitle == null) {
            buttonAcceptTitle = context. getString(R.string.btn_ok);
        }

        if (buttonAcceptListener != null) {
            alertDialog.setPositiveButton(buttonAcceptTitle, buttonAcceptListener);
        } else {

            alertDialog.setPositiveButton(buttonAcceptTitle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        if (buttonCancelListener != null) {
            alertDialog.setNegativeButton(buttonCancelTittle, buttonCancelListener);
        } else {
            if (buttonCancelTittle != null) {
                alertDialog.setNegativeButton(buttonCancelTittle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        }
        // Mostramos la alerta en el hilo principal
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {

            @Override
            public void run() {
                // Mostramos la alerta
                try{
                    currentAlertDialog = alertDialog.show();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

}

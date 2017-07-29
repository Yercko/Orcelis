package es.orcelis.orcelis.utils;

/**
 * Created by yercko on 23/05/2017.
 */

public class UserData {
    /**
     *
     */
    private static UserData instance = null;

    //variables de estado de explotaciones
    private static int posicion_actual = 0;
    private static boolean pasos_view_visible = false;


    /**
     *
     */
    public UserData(){
        super();
    }

    /**
     * Static method
     * @return current class instance
     */
    public static UserData getInstance(){

        if(instance == null){
            instance = new UserData();
        }

        return instance;
    }

    public static int getPosicion_actual() {
        return posicion_actual;
    }

    public static void setPosicion_actual(int posicion_actual) {
        UserData.posicion_actual = posicion_actual;
    }

    public static boolean isPasos_view_visible() {
        return pasos_view_visible;
    }

    public static void setPasos_view_visible(boolean pasos_view_visible) {
        UserData.pasos_view_visible = pasos_view_visible;
    }
}

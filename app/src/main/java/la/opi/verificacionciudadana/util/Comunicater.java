package la.opi.verificacionciudadana.util;

import android.os.Bundle;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhordan on 10/02/15.
 */
public class Comunicater {
    static List<Marker> arrayList;

    public static List<Marker> getDatos() {
        return arrayList;
    }

    public static void setDatos(List<Marker> datos) {
        Comunicater.arrayList = datos;
    }


}

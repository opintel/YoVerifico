package la.opi.verificacionciudadana.util;

import com.google.android.gms.maps.model.Marker;

import java.util.List;

import la.opi.verificacionciudadana.models.Ocurrence;

/**
 * Created by Jhordan on 10/02/15.
 */
public class Comunicater {
    static List<Marker> arrayList;
    static List<Ocurrence> ocurrencesList;
    static String idOcurrence;

    public static String getDatoMarker() {
        return datoMarker;
    }

    public static void setDatoMarker(String datoMarker) {
        Comunicater.datoMarker = datoMarker;
    }

    static String datoMarker;

    public static List<Marker> getDatos() {
        return arrayList;
    }

    public static void setDatos(List<Marker> datos) {
        Comunicater.arrayList = datos;
    }


    public static List<Ocurrence> getOcurrencesList() {
        return ocurrencesList;
    }

    public static void setOcurrencesList(List<Ocurrence> ocurrencesList) {
        Comunicater.ocurrencesList = ocurrencesList;
    }

    public static String getIdOcurrence() {
        return idOcurrence;
    }

    public static void setIdOcurrence(String idOcurrence) {
        Comunicater.idOcurrence = idOcurrence;
    }


}

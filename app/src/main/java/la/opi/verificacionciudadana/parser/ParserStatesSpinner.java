package la.opi.verificacionciudadana.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import la.opi.verificacionciudadana.api.EndPoint;
import la.opi.verificacionciudadana.models.State;
import la.opi.verificacionciudadana.models.Town;
import la.opi.verificacionciudadana.util.VerificaCiudadConstants;

/**
 * Created by Jhordan on 25/02/15.
 */
public class ParserStatesSpinner {




    public static ArrayList<State> paserState(String response) {

        ArrayList<State> arrayListState = new ArrayList<>();

        try {

            final JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {

                State estado = new State();

                JSONObject state = jsonArray.getJSONObject(i);

                estado.setId(state.getString(EndPoint.PARSER_ID_STATE));
                estado.setName(state.getString(EndPoint.PARSER_NAME_STATE));
                arrayListState.add(estado);

                ArrayList<Town> arrayListTown = new ArrayList<>();
                JSONArray municipios = state.getJSONArray(EndPoint.PARSER_LIST_NAME_TOWN);

                for (int j = 0; j < municipios.length(); j++) {
                    Town town = new Town();
                    JSONObject towns = municipios.getJSONObject(j);
                    town.setName(towns.getString(EndPoint.PARSER_NAME_TOWN));
                    town.setId(towns.getString(EndPoint.PARSER_ID_TOWN));
                    arrayListTown.add(town);

                }
                estado.setTown(getTown(arrayListTown));
                estado.setTownArrayList(arrayListTown);


            }

        } catch (JSONException e) {
            Log.e(VerificaCiudadConstants.ERROR_PARSER, e.toString());

        }

        return arrayListState;

    }

    private static ArrayList<String> getTown(ArrayList<Town> arrayListTowns) {
        ArrayList<String> townArrayListMex = new ArrayList<>();

        for (Town town : arrayListTowns) {
            townArrayListMex.add(town.getName());
        }
        return townArrayListMex;
    }


    public static class CustomComparator implements Comparator<Town> {
        @Override
        public int compare(Town town1, Town town2) {
            return town1.getName().compareTo(town2.getName());
        }
    }



}

package la.opi.verificacionciudadana.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

                estado.setId(state.getString("id"));
                estado.setName(state.getString("name"));
                arrayListState.add(estado);

                ArrayList<Town> arrayListTown = new ArrayList<>();
                JSONArray municipios = state.getJSONArray("admin2");

                for (int j = 0; j < municipios.length(); j++) {
                    Town town = new Town();
                    JSONObject towns = municipios.getJSONObject(j);
                    town.setName(towns.getString("name"));
                    town.setId(towns.getString("id"));
                    arrayListTown.add(town);

                }
                estado.setTown(getTown(arrayListTown));

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
}

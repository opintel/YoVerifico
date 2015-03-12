package la.opi.verificacionciudadana.models;

import java.util.ArrayList;

/**
 * Created by Jhordan on 24/02/15.
 */
public class State {

    private String id;
    private String name;
    private ArrayList<String> town;
    private ArrayList<Town> townArrayList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTown() {
        return town;
    }

    public void setTown(ArrayList<String> town) {
        this.town = town;
    }

    public ArrayList<Town> getTownArrayList() {
        return townArrayList;
    }

    public void setTownArrayList(ArrayList<Town> townArrayList) {
        this.townArrayList = townArrayList;
    }






}

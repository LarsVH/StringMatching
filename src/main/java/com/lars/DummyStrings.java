package com.lars;

import java.util.ArrayList;

/**
 * Created by lars on 29.05.16.
 */
public class DummyStrings {

    private ArrayList<String> dummy = new ArrayList<>();

    public ArrayList<String> getDummy(int nr){

        switch(nr){
            case 1:
                dummy.add("Location");
                dummy.add("Place");
                dummy.add("Kitchen");
                dummy.add("PersonInRoom");
                dummy.add("PersonInLocation");
                dummy.add("PersonAtPlace");
                dummy.add("SandraInLocation");
                dummy.add("SandraInKitchen");
                dummy.add("SandraInLivingRoom");
                dummy.add("CisaMeeting");
                dummy.add("VinceSleeps");
                dummy.add("SInBedRoom");
                dummy.add("activity");
                break;
            case 2:
                break;
        }
        return dummy;
    }

}

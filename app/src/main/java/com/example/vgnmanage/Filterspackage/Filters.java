package com.example.vgnmanage.Filterspackage;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Filters {
    // nemishe beja  OBject Filter az food estefade kard CHON inja baraye search sakhte shode masalan name kamel nist
    // va delisios had aghalie va description ham besorat list search mikonim
    public String foodsname = "";
    public Integer foods_delicios_percent_most_be_more_than = 0;
    // chon ke bar in asas search mikonim ke had aghal chand bayad bashe bara hamin esmesh ine va hade aghal 0
    public Integer foods_healthy_percent_most_be_more_than = 0;
    public boolean vegan = false;
    public String description = "";
    public List<String> descriptionslist = new ArrayList<>();

    public final static Filters Lastfilter = new Filters();

    public void loging() {
        Log.e("filterslog", foodsname +
                String.valueOf(foods_delicios_percent_most_be_more_than)
                + String.valueOf(foods_healthy_percent_most_be_more_than)
                + String.valueOf(vegan)
                + description +
                change_liststring_to_onemassagestring(descriptionslist)
        );
    }

    public String change_liststring_to_onemassagestring(List<String> strings) {
        String s = "";
        for (String s2 : strings) {
            s = s + s2 + ".*";
        }
        return s;
    }

}

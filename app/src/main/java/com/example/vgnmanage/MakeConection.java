package com.example.vgnmanage;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.vgnmanage.Filterspackage.Filters;

import java.util.ArrayList;
import java.util.List;

public class MakeConection {
    public final static MakeConection defualtstate1 = new MakeConection();
    // be ma ye kelas sabet mide ke ta vaghti barnambe baze az 0 nemishe va azash mishe to koll app estefade kard
    public static MakeConection defualtstate2 = new MakeConection();
    public String somting = "ee";
    public Food food = new Food();
    public boolean justsendtonet = false;
    public Food foodfromnet = new Food();
    public List<Food> foodList_fromnet = new ArrayList<>();
    public int lastfoodid = 0;

    public boolean adapterisonline_andfromserver = false;

    public boolean online = false;

    public List<Food> foodList_frommydatabase_app = new ArrayList<>();
    public boolean hasfilterused = false;

    public Filters filters = new Filters();//masalan ye karbord in filter ine ke doroste koli filteremono midim behesh vali masalan ham bara database dakhel v ham bara databaseserver az in payamo migirim mifrestim na inke yekaro chanbar bara sharayet mothalef konim

//    public String curent_image_filename_selected = "";

    public int curent_recycle_itemposition = 0;

    MakeConection() {
    }

    MakeConection(Food food) {
        this.food = food;
    }

    void do1() {
        defualtstate1.somting = defualtstate2.somting;
//        defualtstate2 = defualtstate1;
    }

}

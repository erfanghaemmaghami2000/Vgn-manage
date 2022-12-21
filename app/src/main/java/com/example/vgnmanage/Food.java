package com.example.vgnmanage;


import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.Calendar;

public class Food {
    //    public long id = -2;
    public long id;
    public String name = String.valueOf(Calendar.getInstance().getTimeInMillis());
    //    public String name = "";
    public String description = "";
    public int healthy = 80;
    public int delicios = 70;//defaul ino dadim
    public boolean veg = false;
    public String imgurl = "";//or path

    //    public String imgpath = "";
    public boolean fromsever_internet = false;

    Food() {
    }

    Food(String name, String description, String imgurl) {
        this.name = name;
        this.description = description;
        this.imgurl = imgurl;
    }

    Food(long id, String name, String description, String imgurl, int healthy, int delicios, boolean veg) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgurl = imgurl;
        this.healthy = healthy;
        this.delicios = delicios;
        this.veg = veg;
    }

    Food(long id, String name, String description, String imgurl, int healthy, int delicios, boolean veg, boolean fromsever_internet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgurl = imgurl;
        this.healthy = healthy;
        this.delicios = delicios;
        this.veg = veg;
        this.fromsever_internet = fromsever_internet;
    }

    public ContentValues getContentValuesForDb() {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("description", description);
        cv.put("healthy", healthy);
        cv.put("delicios", delicios);
        cv.put("veg", veg ? 1 : 0);
        cv.put("imgurl", imgurl);
        return cv;
    }

    public ContentValues getContentValuesForDbwithoutid() {
        ContentValues cv = new ContentValues();
//        cv.put("id", id);
        cv.put("name", name);
        cv.put("description", description);
        cv.put("healthy", healthy);
        cv.put("delicios", delicios);
        cv.put("veg", veg ? 1 : 0);
        cv.put("imgurl", imgurl);
        return cv;
    }

    public static ContentValues createContentValues(
            long id, String name, String description, int healthy, int delicios, boolean veg, String imgurl) {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("description", description);
        cv.put("healthy", healthy);
        cv.put("delicios", delicios);
        cv.put("veg", veg ? 1 : 0);
        cv.put("imgurl", imgurl);
        return cv;
    }

    public static Food fromCursor(Cursor cursor) {
        Food food = new Food();
        food.id = cursor.getLong(cursor.getColumnIndex("id"));
        food.name = cursor.getString(cursor.getColumnIndex("name"));
        food.description = cursor.getString(cursor.getColumnIndex("description"));
        food.healthy = cursor.getInt(cursor.getColumnIndex("healthy"));
        food.delicios = cursor.getInt(cursor.getColumnIndex("delicios"));
        boolean vegf = false;
        if (1 == cursor.getInt(cursor.getColumnIndex("veg"))) {
            vegf = true;
        } else {
            vegf = false;
        }
        food.veg = vegf;
        food.imgurl = cursor.getString(cursor.getColumnIndex("imgurl"));

        return food;
        // chon ke selected az update estefade mishe baraye gereftane food khode food bedone selected ro migirim
        // vali selected to data base add mishe v hastesh v mishe estefadash kard
    }

    public void foodslog() {
        Log.e("foodslog", id + "   " + name + "      " + description +
                "      " + healthy + "        " + delicios + "     " + veg + "     " + imgurl);
    }

}

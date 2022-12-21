package com.example.vgnmanage.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.vgnmanage.Food;
import com.example.vgnmanage.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyfoodDb extends SQLiteOpenHelper {
    // age kelasi ya taghiri ba esme data base rokh bede bayad version dbkole classa update she
    // bara daryaft database amade yek data base minevisim badesh mirim az view toolwindows devicefileexpelorer data data esme proje data base sho copy va dar assets gharar midim
    private Context context;
    private static final int DBVERSION = 3;
    // agar ba esme sabet data base table jadid bekhad ezafe she hatman version bayad taghir kone
    //    private static final String DBNAME = "db_food";// age esme tablo avaz kardim eror dad behtare version data base ro ham avaz konim badesh  dad mishe esm db ham avaz kard
    private static final String DBNAME = "db_food";
    //    public static final String TABLE_FOOD = "tb_food";
    //        in dakhelesh data base assets hastesh
    public static final String TABLE_FOOD = "tb_myfood";

    public static final String[] all_columns = {"id", "name", "description", "healthy", "delicios"
            , "veg", "imgurl"};

    private static final String CMD_CREATE_CITY_TB = "CREATE TABLE IF NOT EXISTS '" + TABLE_FOOD + "' (" +
            "'id' INTEGER PRIMARY KEY NOT NULL, " +
            "'name' TEXT, " +
            "'description' TEXT, " +
            "'healthy' INTEGER, " +
            "'delicios' INTEGER, " +
            "'veg' INTEGER," +
            "'imgurl' TEXT " +
            ")";

    public MyfoodDb(Context context) {
        super(context, DBNAME, null, DBVERSION);
        this.context = context;
//        if (getCities(null,null).isEmpty()){
//            initContents();
//        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CMD_CREATE_CITY_TB);
        Log.e("dbhelper", "Table created.");
        Toast.makeText(context, "dbhelper", Toast.LENGTH_SHORT).show();
//        initContents();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
//        initContents();
    }

    public void initContents() {
        //     chon to teread be sorat movazi ejra mishe v  barname tool nemikeshe
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    1111001 Build_a_Balanced_Meal  Combine_protein_and_fiber_during_dinner_as_it_will_help_you_feel_full_for_longer 60 60 nothing
//                    1111002 Protein_Packed_Diet  Its_true_nothing_contains_protein_quite_like_meat_does_But_if_youre_vegetarian_loadup_on_proteinrich_ingredients 60 60 nothing
//                    1111003 Turn_Up_That_Umami_Kick  Include_asparagus_tomatoes_seaweed_soy_tofu_corn_and_onions_in_your_diet_as_they_are_good_substitute_for_umami  60 60 nothing
                    InputStream stream = context.getResources().openRawResource(R.raw.defualtfood);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    int datasize = 6;// tedad data haye morede nazar az ro input khonde va be data base rikhte
                    String line = "";
                    SQLiteDatabase db = getWritableDatabase();
                    while ((line = reader.readLine()) != null) {//ta vaghti ke reader har khato mikhone va on khat mohtava dare
                        String[] data = line.split("\t");//\t yani har faselei
                        if (data.length < datasize + 1) {
                            continue;
                            //* continue dar shart be manaye ine ke bere khate bad v split \t yani harja fasele v rab bod joda kon
                        }
                        long insertId = db.insert(TABLE_FOOD, null,
                                Food.createContentValues(
                                        Long.valueOf(data[0]), // behtare id ham nadad age az ghabl id haye khasi dar nazar gerefte nashode bashe  albate bayad tabe bedon id ro ham nevesht
                                        data[1],
                                        data[2],
                                        Integer.parseInt(data[3]),
                                        Integer.parseInt(data[4]),
                                        false,// pishfarz hamaro gheir veg midim bara hamin to txt filemon nabod
                                        data[6]
                                )

                        );
                        Log.i("dbhelper", "city inserted : " + insertId);

                    }
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    // tabdil mohtava file txt be database hazer

    public void updateFoodSelected(long id, Food food) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv = food.getContentValuesForDb();
        db.update(TABLE_FOOD, cv, "id = " + id, null);// bar asas id jahaye digeii ke taghir karde ro taghir midim
        List<Food> foodlist = getfoods("id = " + id, null);
        for (Food foods : foodlist) {// az onjaii ke bara har id yek object vojod dare yedone mide kolan
            String foodid = String.valueOf(foods.id);
            String name = food.name;
            Log.i("id food name :", name + foodid);
        }
        db.close();
    }

    public void updateFoodSelected(long id, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv = contentValues;
//        cv.put("selected", selected ? 1 : 0);
        // shayadam ghablan selected nadade bodan
        db.update(TABLE_FOOD, cv, "id = " + id, null);// bar asas id jahaye digeii ke taghir karde ro taghir midim
        List<Food> foodlist = getfoods("id = " + id, null);
        for (Food food : foodlist) {// az onjaii ke bara har id yek object vojod dare yedone mide kolan
            String foodid = String.valueOf(food.id);
            String name = food.name;
            Log.i("id food name :", name + foodid);
        }
        // succed in dorost bod yani id ro migire v content value ro dastkari mikone
        // chon ke update khodesh midone cv jadid cv onchizai ke taghir karde ro update mikone
        // (name table , content value jadid , peyda kardan bar asas id,)
        db.close();

//        db.execSQL("UPDATE DB_TABLE SET YOUR_COLUMN='newValue' WHERE id=6 ");
        //injori ham mishe goft faghat dastoro albate to kode khode inja kole value jadid bejoz id ro nemidim balkae soton ya masalan meghdar esm ro taghir midim
//        db.execSQL("UPDATE DB_TABLE SET name ='pitza' WHERE id=6 ");albate in maesale daghesho nemidonam

    }
    // id shahr ro migire v prpperti hasho update mikone ke ma baraye vegan tru ya false esh ro estefade mikonim

    public void insertFoodToDb(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        Long insertId = db.insert(TABLE_FOOD, null, food.getContentValuesForDb());
//        Long insertId = db.insert(TABLE_FOOD, null, food.getContentValuesForDbwithoutid());

        Toast.makeText(context, "inserted", Toast.LENGTH_SHORT).show();
        Log.i("dbhelper", "city inserted with id : " + insertId);
        db.close();
    }

    public void insertFoodToDbwithoutid(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Long insertId = db.insert(TABLE_FOOD, null, food.getContentValuesForDb());
        Long insertId = db.insert(TABLE_FOOD, null, food.getContentValuesForDbwithoutid());

        Toast.makeText(context, "inserted", Toast.LENGTH_SHORT).show();
        Log.i("dbhelper", "city inserted with id : " + insertId);
        db.close();
    }
    //behtare in estefade beshe ta ba id  chon khodesh id ro be tartib mide va badesh dakhel listemon ezafe mishe va harvaghtam beakhaim ba serach ya az dakhel list id ro migirim

    public void deletFoodToDb(String where) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(TABLE_FOOD, where, null);
        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
        Log.e("dbhelper", "city deleted with id : " + delete);
        db.close();
    }

    public List<Food> getfoods(String selection, String[] selectionArgs) {
        List<Food> foodlist = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOOD, all_columns, selection, selectionArgs, null, null, null/*"delicios, name"*/);
        // cursor dar vaghe jadvale entekhabie va metode gereftan database
        Log.d("dbhelper", "cursor returned " + cursor.getCount() + " records.");
        if (cursor.moveToFirst()) {
            do {
                foodlist.add(Food.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodlist;
    }

    public List<Food> getfoods(String selection, String[] selectionArgs, int limit) {
        List<Food> foodlist = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOOD, all_columns, selection, selectionArgs, null
                , null, null/*"delicios, name"*/, String.valueOf(limit));
        // cursor dar vaghe jadvale entekhabie va metode gereftan database
        Log.d("dbhelper", "cursor returned " + cursor.getCount() + " records.");
        if (cursor.moveToFirst()) {
            do {
                foodlist.add(Food.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodlist;
    }

    public List<Food> getfoods(String str) {
        List<Food> foodlist = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(str, null);
// fekr konam injoriam beshe
        Log.d("dbhelper", "cursor returned " + cursor.getCount() + " records.");
        if (cursor.moveToFirst()) {
            do {
                foodlist.add(Food.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodlist;
    }

    public List<Food> searchFoodByName(String name, String limit) {
        List<Food> foods = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_FOOD, all_columns,
                "name LIKE '" + name + "%'",
                //agar % estefade bashe yani mesl on inja yani edame name harchi dige ham mojod bod neshon bede
                //ya masalan fast food ro injori mishe search kard %st%foo%  ya mohamad reza moha%
                null, null, null, "name"
                //baraye olaviat namayesh aval masalan name ro neshon mide
                , limit
                //limit ham meghdare liste
        );

        Log.d("dbhelper", "cursor returned " + cursor.getCount() + " records.");
        if (cursor.moveToFirst()) {
            do {
                foods.add(Food.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        db.close();
        return foods;
    }
    //search mikone bar asas esm v tedad va digar khastehamon

    public List<Food> searchFood(String order, String limit) {
        List<Food> foods = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_FOOD, all_columns,
//                "name LIKE '" + cityname + "%'",
                order,
                null, null, null, null, limit);

        Log.d("dbhelper", "cursor returned " + cursor.getCount() + " records.");
        if (cursor.moveToFirst()) {
            do {
                foods.add(Food.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        db.close();
        return foods;
    }

    public List<Food> getfoodsbyorder(String order) {
        SQLiteDatabase db = getReadableDatabase();
        List<Food> foods = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                order, null);
//        Log.i(FLOWER_DB_HELPER_LOGTAG, "Returned " + cursor.getCount() + " rows.");
        if (cursor.moveToFirst()) {
            do {
                // process for each row
                Food food = Food.fromCursor(cursor);
                foods.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (db.isOpen()) db.close();
        return foods;
    }

//mishe yekar dige ham bara data base goshi kard bejoz file ya xml ya string ya ..
// inke ye metode add bezarim va begim age in objcet ha ba in moshakhasat to db vojod nadasht addesh kon
}



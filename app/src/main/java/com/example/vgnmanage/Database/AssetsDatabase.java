package com.example.vgnmanage.Database;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetsDatabase {
    // age kelasi ya taghiri ba esme data base rokh bede bayad version dbkole classa update she
    // bara daryaft database amade yek data base minevisim badesh mirim az view toolwindows
    // devicefileexpelorer data data esme proje data base sho copy va dar assets gharar midim
// ebteda ke file data basemono sakhtim mirim az too bala to campioter yeja save as mikonim
// bade inke kopish kardim miaim poshe assets misazim va mirizim tosh badesh tarjihan
// esme file default db va file data base yeki bashe vali version fargh kone
// bade inke kopi kardim mirim kole data base ya app ro az divice manitor pak mikonim
// bad kelass app minevisim va extend application mikonim v to oncreate chekdb ro anjam midim
// bayad to manifast name =".app" bashe va vaghti data base pak v dar assets kopi shod ejrash
// (chek db)mikonim chon bayad database ehtemalan zod tar dorost se ta db inja
// albate age bekhaim yeki bashan mitonim db ghbli ro ye table dige sakhte bashim va
// versiono taghir bedim ya data base ro taghir bedim
// yani shayad ba kopi kardan to assts va taghir table esme db lazem be anjam yeseri kara nashe

    private Context context;
    //    private String db_food_default = "db_food_default";
//    private String goal_database = "db_food";
    private String DATABASENAME = "db_food";
    // data base ghadimi

    public AssetsDatabase(Context context) {
        this(context, "db_food");
        // in yani age in constroctoro seda zad ba in maghadir to boro constroctor paini ro seda bezan
    }

    public AssetsDatabase(Context context, String dbName) {
        this.context = context;
//        this.db_food_default = dbName;
        this.DATABASENAME = dbName;
    }

    public void checkDb() {// bara rikhtan data base default bayad dar databasehelper
        // initial ro az raw bekhonim
//        try {
//            File db1 = new File("");+
//            File db2 = File.createTempFile("", "");+
//            File db3=context.getCacheDir();
//            File db5=context.getDatabasePath("");+
//            File db6=context.getExternalCacheDir();
//            File db7=context.getExternalFilesDir("");
//            File db8=context.getFilesDir();+
//            File db9=context.getFileStreamPath(""); +
//            File db10=context.getObbDir();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        File dbfile = context.getDatabasePath(goal_database);
        File dbfile = context.getDatabasePath(DATABASENAME);
        // inja mige to filaye data base be esm db age darim bede v age
        // vojod dashte bego age nadasht copy kono besaz out put barash
//        File file=new File("pathname YA masalan E://game")ravesh halat adi esm ya
//        adres file ro behesh midim dar bala fek konam
        // inja fek konam miporse data basi aya be in esm ghablan vojod dashte ya na
        if (!dbfile.exists()) {
            try {
                copyDatabase(dbfile);
                Log.e("AssetsDatabaseHelper", "database copied.");
                // chon yebar in ejra mishe dige log nemide
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database.", e);
            }
        }
    }
    // chek kardan vojod dashtan file

    public void copyDatabase(File dbfile) throws IOException {
//        InputStream inputStream = context.getAssets().open(db_food_default);
        InputStream inputStream = context.getAssets().open(DATABASENAME);

        // age vojod nadasht az assets db name o peyda kon
        // chon az jens file bod va bara khondan bod inputstream
        dbfile.getParentFile().mkdirs();
        //      dbfile.getParentFile().mkdir();
        //      makdirs ha kareshon ine ke age ghablan directory sakhte nashode bashe besazan
        //      directory mesl folder listi az file hast to java
        OutputStream outputStream = new FileOutputStream(dbfile);
// inja darvaghe file data base be esm db_name ro bevojod miarim
//         OutputStream outputStream = new FileOutputStream(context.getDatabasePath(dbName));
        int len = 0;
        byte[] buffer = new byte[1024];

        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        //    yani har 1024 byte  buffere
        //    va az to input har buffer o beriz to len
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
// database ro az assets migire mirize to data base feli

}

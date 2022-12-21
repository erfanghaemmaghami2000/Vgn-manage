package com.example.vgnmanage;

import androidx.appcompat.app.AppCompatActivity;
//shift ctrl r
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AddFood extends AppCompatActivity {
    Food curentfood;// ham ba in mishe ham bedone in
    boolean just_send_to_net;
    EditText editname, editdescription, edithelthy, editdelicios;
    String name, description;
    int deliciospercent = -1;
    int helthypercent = -1;
    String imageurlorpath;
    ImageView imagefood;
    boolean booleanvegan;
    Switch swichtvegan;
    Button buttenfinish, buttenselectimage;
    Bitmap imagebitmap;

    String strdelicios;
    String strhelthy;

    boolean hasimagchosed = false;

    public static final int GALLERY = 22;
    private static final String IMAGE_DIRECTORY = "/VgnManage/addfood/imagefood";

    //    private static final String IMAGE_DIRECTORY = "/VgnManage";
// bara inke axo beshe avord bitmapesho (chon momkene hajmi eror bede) bayad in 2 ta ro be dakhele manifast application esazfe konim
//    android:hardwareAccelerated="false" , android:largeHeap="true" ke 2 omi mohem tare ya fek konam az paini ham mishe
//                    Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
//                    Glide.with(mContext).load(imgID).asBitmap().override(1080, 600).into(mImageView);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        just_send_to_net = MakeConection.defualtstate1.justsendtonet;
//        just_send_to_net = getIntent().getBooleanExtra("sendtonet", false);
        if (just_send_to_net) {
            Toast.makeText(this, "we just send it to net", Toast.LENGTH_SHORT).show();
        }
        init();
        Utils.requestMultiplePermissions(this);
    }

    private void init() {
        editname = findViewById(R.id.editname);
        editdescription = findViewById(R.id.editdes);
        editdelicios = findViewById(R.id.editdelicios);
        edithelthy = findViewById(R.id.edithelthy);
        imagefood = findViewById(R.id.imageView);
        swichtvegan = findViewById(R.id.switchveg);
        swichtvegan.setChecked(false);

        buttenfinish = findViewById(R.id.buttonfinish);
        buttenselectimage = findViewById(R.id.buttonchoseimage);

        buttenfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chekform();
            }
        });

        buttenselectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editname.getText().toString();
                // bara inke mikhaim esm ro be sabt file ya ax bedim bayad moghe har kilik name esm dashte bashe bara hamin name ro to clikaye dige ham migirimim
                //                if (name == null || name == "" || name.isEmpty()) {
                if (editname.getText().toString() == null || editname.getText().toString().isEmpty()) {
                    editname.setError("pleas first enter the name ");
                    return;
                }
                //                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //kode makhsos raftan yezarb be galeri ke yek
//                startActivityForResult(galleryIntent, GALLERY);

//                Intent galleryIntent = new Intent(Intent., );


//*                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                getIntent.setType("image/*");
//
//                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                pickIntent.setType("image/*");
//
//                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
//
//                startActivityForResult(chooserIntent, GALLERY);

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//kode makhsos raftan yezarb be galeri
                startActivityForResult(galleryIntent, GALLERY);
            }
        });

        swichtvegan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                booleanvegan = isChecked;
                Toast.makeText(AddFood.this, "vegan", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void chekform() {
        boolean haserore1 = true;
        boolean haserore2 = true;
        boolean haserore3 = true;
        boolean haserore4 = true;
        boolean haserore = true;

        name = editname.getText().toString();
        description = editdescription.getText().toString();
        strdelicios = String.valueOf(editdelicios.getText().toString());
        strhelthy = String.valueOf(edithelthy.getText().toString());

        if (name.isEmpty() || name == null) {
            editname.setError("pleas enter the name ");
        } else {
            haserore1 = false;
        }
        if (description.isEmpty() || description == null) {
            editdescription.setError("pleas enter the description ");
        } else {
            haserore2 = false;
        }
        if (strdelicios.isEmpty() || strdelicios == null) {
            editdelicios.setError("pleas enter the percent of delicios ");
        } else {
            deliciospercent = Integer.parseInt(strdelicios);
            if (deliciospercent > 100 || deliciospercent < 0) {
                editdelicios.setError("pleas enter betwen 100 and 0");
            } else {
                haserore3 = false;
            }
        }
        if (strhelthy.isEmpty() || strhelthy == null) {
            edithelthy.setError("pleas enter the percent of delicios ");
        } else {
            helthypercent = Integer.parseInt(strhelthy);
            if (helthypercent > 100 || helthypercent < 0) {
                edithelthy.setError("pleas enter betwen 100 and 0");
            } else {
                haserore4 = false;
            }
        }

//            haserore1 = haserore1 & seteror(editname, name, "name");
//            haserore2 = haserore2 & seteror(editdescription, description, "description");
//            haserore3 = haserore3 & seteror(editdelicios, String.valueOf(deliciospercent), "deliciospercent");
//            haserore4 = haserore4 & seteror(edithelthy, String.valueOf(helthypercent), "helthypercent");
        haserore = haserore1 | haserore2 | haserore3 | haserore4;

//        if (!haserore) {
//            name = editname.getText().toString();
//            description = editdescription.getText().toString();
//            deliciospercent = Integer.parseInt(editdelicios.getText().toString());// chon ke  age edittext number khali bashe v begirimesh va be adad tabdil konim eror mide
//            helthypercent = Integer.parseInt(edithelthy.getText().toString());
//            if (deliciospercent > 100 || deliciospercent < 0) {
//                editdelicios.setError("pleas chose your percent betwen 100 and 0");
//            }
//            if (helthypercent > 100 || helthypercent < 0) {
//                edithelthy.setError("pleas chose your percent betwen 100 and 0");
//            }
//        }


        AlertDialog.Builder alertBuilder;
        if (!haserore && hasimagchosed) {
//        if (hasimagchosed) {
            //        if (!haserore) {
            curentfood = new Food();
            curentfood.name = name;
            curentfood.description = description;
            curentfood.delicios = deliciospercent;
            curentfood.healthy = helthypercent;
            curentfood.veg = booleanvegan;
            curentfood.imgurl = imageurlorpath;

            if (just_send_to_net) {
                sendtonet(curentfood);
            } else {
                alertBuilder = new AlertDialog.Builder(this);

                alertBuilder.setTitle("SERVER!");
                alertBuilder.setMessage("would you like recomend this to us?");

                alertBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendtonet(curentfood);
                        finish();
                    }
                });
                alertBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        send_foodparament_tomainactivity();
                        finish();
                    }
                });

                alertBuilder.setNeutralButton("both", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddFood.this, "seccesfull", Toast.LENGTH_SHORT).show();
                        sendtonet(curentfood);
                        send_foodparament_tomainactivity();
                        finish();
                    }
                });
//            alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//
//                }
//            });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        } else if (!hasimagchosed) {
            Toast.makeText(this, "please select the image", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "please say parametre", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendtonet(Food food) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConectionWithNet.Url1)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();//kode makhsos

        File file = new File(food.imgurl);
        if (!file.exists()) {
            Log.e("log", "file dosentexist");
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part req_fileToUpload = MultipartBody.Part.createFormData("filefoodimagename", file.getName()// key ,
                , requestBody);
        //filefoodimagename kilid ya esme filemon ke server az in tarigh shenasaii kone va file.getName() name file
        RequestBody req_foodname = RequestBody.create(MediaType.parse("text/plain"), food.name);
        RequestBody req_fooddes = RequestBody.create(MediaType.parse("text/plain"), food.description);
        RequestBody req_foodhealthy = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(food.healthy));
        RequestBody req_fooddelicios = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(food.delicios));
        int intveg = (food.veg) ? 1 : 0;
        RequestBody req_foodvegan = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(intveg));

        ConectionWithNet getResponse = retrofit.create(ConectionWithNet.class);
        Call<String> call = getResponse.uploadditails(req_fileToUpload, req_foodname, req_fooddes, req_foodhealthy, req_fooddelicios, req_foodvegan);// yek no interface ke inja behesh parametr hasho midim v be inja midim khode retofit tarifesh mikone

        Log.d("assss", "asss");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("mullllll", response.body().toString());
//                try {
////                    JSONObject jsonObject = new JSONObject(response.body());
//                    JSONObject jsonObject = new JSONObject(response.body().toString());
//                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    jsonObject.toString().replace("\\\\", "");
//                    if (jsonObject.getString("status").equals("true")) {
//                        file.delete();
//                        JSONArray dataArray = jsonObject.getJSONArray("data");
//                        String url = "";
//                        for (int i = 0; i < dataArray.length(); i++) {
//                            JSONObject dataobj = dataArray.getJSONObject(i);
//                            url = dataobj.optString("pathToFile");
//                        }//khondan ya download az mahale gharar gereftan file to server
//                        Picasso.get().load(url).into(imageView);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("gttt", call.toString() + t.toString());
            }
        });
//<?php
//// aval bayad az data base ya php admine site ye data base sakht badesham table
//        if($_SERVER['REQUEST_METHOD']=='POST'){
//            include_once("config.php");
//
//            $originalImgName= $_FILES['filefoodimagename']['name'];
//            $foodimage= $_FILES['filefoodimagename']['tmp_name'];
//
//            $foodname=$_POST['foodname'];
//            $fooddescription=$_POST['fooddescription'];
//            $foodhealthy=$_POST['foodhealthy'];
//            $fooddelicios=$_POST['fooddelicios'];
//            $foodvegan=$_POST['foodvegan'];
//            $foodhealthypercnt=(int)$foodhealthy;
//            $fooddeliciospercnt=(int)$fooddelicios;
//            $foodveganint=(int)$foodvegan;
//
//            $folder="veganimage/";
//
//            $url = "http://efikhatar.ir/".$folder.$originalImgName; //update path as per your directory structure
//
//            if(move_uploaded_file($foodimage,$folder.$originalImgName)){
//                $query = "INSERT INTO veganmanage (name,description,healthy,delicios,vegan,imageurl) VALUES
//                ('$foodname','$fooddescriptiont','$foodhealthypercnt','$fooddeliciospercnt','$foodveganint','$url')";
//                if(mysqli_query($con,$query)){
//                    $query= "SELECT * FROM veganmanage WHERE imageurl='$url'";
//                    $result= mysqli_query($con, $query);
//                    $emparray = array();
//                    if(mysqli_num_rows($result) > 0){
//                        while ($row = mysqli_fetch_assoc($result)) {
//                            $emparray[] = $row;
//                        }
//                        echo json_encode(array("status" => "true","message" => "Successfully file added!" , "data" => $emparray) );
//                    }
//                    else{
//                        echo json_encode(array("status" => "false","message" => "Failed1!") );
//                    }
//                }else{
//                    echo json_encode(array( "status" => "false","message" => "Failed2!") );
//                }
//            }
//            else{
//                echo json_encode(array( "status" => "false","message" => "Failed3!") );
//            }
//        }
//?>
    }

    private void send_foodparament_tomainactivity() {
        Toast.makeText(this, "seccusfull", Toast.LENGTH_SHORT).show();
        MakeConection.defualtstate1.somting = "aaa";
        MakeConection.defualtstate2.somting = "aaaa";
//            finishActivity();
        if (name != null && description != null && deliciospercent != -1 && helthypercent != -1
                && imageurlorpath != null) {
            MakeConection.defualtstate2.food.name = name;
            MakeConection.defualtstate2.food.description = description;
            MakeConection.defualtstate2.food.delicios = deliciospercent;
            MakeConection.defualtstate2.food.healthy = helthypercent;
            MakeConection.defualtstate2.food.veg = booleanvegan;
//            MakeConection.defualtstate2.food.imgpath = imageurlorpath;
            MakeConection.defualtstate2.food.imgurl = imageurlorpath;

            Utils utils = new Utils();
            utils.savefoodtodatabase(MakeConection.defualtstate2.food, true, this);
            // mishod yek objekt food joda ham dad vali bara inke ye karbord make conection dige malom beshe
            //intori ham mitonim mostahim az make conection dastresi dashte bashim ham ferestadan mostaghim

        } else {
            Toast.makeText(this, "soryyyyyyyy some wrong", Toast.LENGTH_SHORT).show();
        }
    }
// darvaghe food ro be database mide va to main activity az data base migiratesh

    private boolean seteror(EditText editText, String s, String ss) {
        s = editText.getText().toString();
        if (s.isEmpty() || s == null) {
            editText.setError("pleas enter the " + ss);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            //this = activity
            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                Log.e("loggg", "1");
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);//tabdil uri be bitmap
//                    Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
//                    Glide.with(mContext).load(imgID).asBitmap().override(1080, 600).into(mImageView);
                    Log.e("loggg", "2");
                    if (bitmap != null) {
                        Log.e("loggg", "3");
                    }
                    imagefood.setImageBitmap(bitmap);
                    if (name != null) {
                        String path = saveimagebitmap_tofile_andgive_path(bitmap, name);// masir filemono midim
                        imageurlorpath = path;
                        imagebitmap = bitmap;
                        if (!path.isEmpty() && path != null && path != "") {
                            hasimagchosed = true;
                        } else {
                            Log.e("path", "imagehasntselected");
                        }
                    } else {
                        Log.e("name", "erooor");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddFood.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
// gereftan ax az gallery tabdil be bitmap va sepas tabdile be file

    public String saveimagebitmap_tofile_andgive_path(Bitmap myBitmap, String nameof_file) {
        if (nameof_file == null || nameof_file.isEmpty()) {
            nameof_file = "noname";
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();// kode makhsos tabdil be str
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);// bitmap ya ax ro andaze va noe file v byte haii ke bayad tosh rikhte beshe ro dobare be bitmapemon midim
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);// bitmap ya ax ro andaze va noe file v byte haii ke bayad tosh rikhte beshe ro dobare be bitmapemon midim
//        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT); // bara vaghti mikhastim file nasazim
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);// yek file dar mohit khareji be esm posheye delkhahemon migirim
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {// age on file vojod nadasht misazimesh
            wallpaperDirectory.mkdirs();
//wallpaperDirectory.createNewFile();
        }
        try {
            File f = new File(wallpaperDirectory, nameof_file + "_" + Calendar.getInstance()// yek nemone az calender ya taghvime ke tsh tarikh v .. on rozo dare
                    .getTimeInMillis() + ".jpg");// ke ma inja zaman hal ro bara asas mili sanie mide // 1600032334344.jpg
            // yek file jadid be esm file ghablimon v pasvand ya chile ya / jadidemon migirim
            f.createNewFile();// ono misazim
            FileOutputStream fo = new FileOutputStream(f);// baraye filemon yek output baz mikonim va behesh byt haro midim (ke darvaghe be filemon ham mide )
            fo.write(bytes.toByteArray());// kode makhsos rikhtan file
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},//arayeye  masirhaye filehamon
                    new String[]{"image/jpeg"},//noe formati ke mikhaim
                    null);
            fo.close();// yek output baz mikonim va tosh masir filemon ya khode file ke mikhaimo midim v mibandim
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();// masir file ro midim ke besorat string
        } catch (IOException e1) {
            e1.printStackTrace();
            Log.e("TAG", "File not Saved::--->");
        }
        return "";
    }
//tabdil bitmap be bite va bite be file

}
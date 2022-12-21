package com.example.vgnmanage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vgnmanage.Filterspackage.Filters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SendFiltersAndGetFoods {

    public static class Paramtaskmodel {
        // baraye vaghti mikhaim to parametr chizaye delkhaho bedim
        public int integer;
        public String string;
    }

    boolean hasgetfinished = false;

//    public static abstract class Task_Sendorder_andgetfoods_fromnet extends AsyncTask</*ProgressBar progresbar ,*/Paramtaskmodel, List<Food>, String> {
//
//        Task_Sendorder_andgetfoods_fromnet(/*ProgressBar progressBar*/) {
//        }
//
//        @Override
//        protected void onPreExecute() {
////            if (asyncTaskList.isEmpty()) {
//            // chon ke avalesh age ,meghdar avlie ya hamon empty nadim behesh v null bashe eror mide
//// chon ke empty nist v nulle mire to else v hichi meghdar nemigirie bara hamin mire akharesh metod akhar ke remov kone bug mide
////                asyncTaskList.add(this);
////            } else {
////
////            }
//
//        }
//
//        @Override
//        protected String doInBackground(Paramtaskmodel... paramtaskmodels) {
////                Thread.sleep(5000);
//            String s = paramtaskmodels[0].string;
//            int i = paramtaskmodels[0].integer;
//
//            Filters filter = MakeConection.defualtstate1.filters;
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(ConectionWithNet.Url1)
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .build();//kode makhsos
//
//            RequestBody req_foodname = RequestBody.create(MediaType.parse("text/plain"), filter.foodsname);
//            RequestBody req_fooddes = RequestBody.create(MediaType.parse("text/plain"), filter.changelisttostr(filter.descriptions));
//            // bara dadan in ham mishod kelaso abstraco jori nevesht ke araye ro bede
//            // ya inke kolesho to ye massage descriptions bedi onja joda koni ya massage data basesho inja bedim
//            RequestBody req_foodhealthy = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(filter.foods_healthy_percent_most_be_more_than));
//            RequestBody req_fooddelicios = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(filter.foods_delicios_percent_most_be_more_than));
//            int intveg = (filter.vegan) ? 1 : 0;
//            RequestBody req_foodvegan = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(intveg));
//
//            ConectionWithNet getResponse = retrofit.create(ConectionWithNet.class);
//            Call<String> call = getResponse.uploadfiltersandgetfoods(req_foodname, req_fooddes, req_foodhealthy, req_fooddelicios, req_foodvegan);// yek no interface ke inja behesh parametr hasho midim v be inja midim khode retofit tarifesh mikone
//
//            Log.d("assss", "asss");
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    Log.e("mullllll", response.body().toString());
//                    Food food = new Food();
//                    List<Food> foods = new ArrayList<>();
//
//                    try {
//                        //      JSONObject jsonObject = new JSONObject(response.body());
//                        JSONObject jsonObject = new JSONObject(response.body().toString());
//
//                        jsonObject.toString().replace("\\\\", "");
//
//                        if (jsonObject.getString("status").equals("true")) {
//                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                            String url = "";
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObjectinstance = jsonArray.getJSONObject(i);
////                            food.imgurl = jsonObjectinstance.optString("imageurl");
////                            food.imgurl = jsonObjectinstance.getString("imageurl");  fatgi nadaran opt default dare vali get na null mide va opt empty mide age nabashe fek konam
//                                food.imgurl = jsonObjectinstance.optString("imageurl", "");
//                                food.name = jsonObjectinstance.optString("name", "");
//                                int veganint = jsonObjectinstance.optInt("vegan", 0);
//                                if (veganint == 1) {
//                                    food.veg = true;
//                                } else if (veganint == 0) {
//                                    food.veg = false;
//                                }
//                                food.healthy = jsonObjectinstance.optInt("healthy", 0);
//                                food.delicios = jsonObjectinstance.optInt("delicios", 0);
//                                food.description = jsonObjectinstance.optString("description", "");
//                                foods.add(food);
//                            }//khondan ya download az mahale gharar gereftan file to server
////                        Picasso.get().load(url).into(imageView);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    publishProgress(foods);////*  // bara onprogress in hatman bayad seda zade she
//                    MakeConection.defualtstate1.foodList_fromnet = foods;
//                }
//
//                @Override
//                public void onFailure(Call call, Throwable t) {
//                    Log.d("gttt", call.toString());
//                }
//            });
//
//            // chon inja threadie ke be ui dastresi nadare karaye intori inja anjam mishe
////            v karaye mostaghim dar onprogres bara hamin inja sleep mikonim chon mostaghim nist v fekr konam ye thread digas
//            return "FF";
//        }
//
//        abstract void refresh_adapter(List<Food> foods);
//
//        @Override
//        protected void onProgressUpdate(List<Food>... values) {
//            // dastresi be ui mostaghim
//            Log.e("sleep", "true");
////            refresh_adapteradd(values[0].get());
//            refresh_adapter(values[0]);
//
////            mProgressBar.setProgress(Integer.parseInt(values[0]));
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
////            asyncTaskList.remove(this);
////            if (asyncTaskList.isEmpty()) {
////
////            }
////            else  (){
////
////            }
//        }
//    }

    // ya be ravesh in painia mishe
//    @Override
//    public void onClick(View view) {
//        Toast.makeText(MythreadandhandlerandAysintask.this, "buten clicked", Toast.LENGTH_SHORT).show();
//        MyTask myTask = new MyTask();
//        myTask.execute("kkk");
//        MyTask myTask2 = new MyTask();
//        myTask2.execute("kkk");
//        MyTask2 myTask3 = new MyTask2();
//        myTask3.execute("kk");
//        Log.i("myTask3", myTask3.getStatus().name() + myTask3.getStatus().toString());
//               // MyTask myTask4 = new MyTask();
//               // myTask4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    //// in jori be sorat movazi anjam mide ke fek konam ehtemalan ob backgraund bishtar fargh mikone
    //// ye bar navar aval  badesh aval badesh dovom ejra mishe
////                myTask4.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR); halate difalt ine ke motevalie v lazem nist bedimesh vali mishe ham dad
//    }
//    public class MyTask extends AsyncTask</*ProgressBar progresbar ,*/String, String, String> {
//        Random random;
//        Integer integer;
//
//        MyTask(/*ProgressBar progressBar*/) {
//            random = new Random();
//            integer = random.nextInt();
//        }
//
//        @Override
//        protected void onPreExecute() {
////            if (asyncTaskList.isEmpty()) {
//            // chon ke avalesh age ,eghdar avlie ya hamon empty nadim behesh v null bashe eror mide
//// chon ke empty nist v nulle mire to else v hichi meghdar nemigirie bara hamin mire akharesh metod akhar ke remov kone bug mide
////                asyncTaskList.add(this);
////            } else {
////
////            }
//
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            for (int i = 0; i < 30; i++) {
//                try {
//                    Thread.sleep(500);
//                    Log.e("a", " :true");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                publishProgress(String.valueOf(i));
//            }
//            // chon inja threadie ke be ui dastresi nadare karaye intori inja anjam mishe
////            v karaye mostaghim dar onprogres bara hamin inja sleep mikonim chon mostaghim nist v fekr konam ye thread digas
//            return "FF";
//        }
//
//        @Override
//
//        protected void onProgressUpdate(String... values) {
//            // dastresi be ui mostaghim
//
////            textView.setText("66666");
////            mProgressBar.setProgress(10);
//            mProgressBar.setProgress(Integer.parseInt(values[0]));
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
////            asyncTaskList.remove(this);
////            if (asyncTaskList.isEmpty()) {
////
////            }
////            else  (){
////
////            }
//        }
//    }
//
//    public class MyTask2 extends AsyncTask</*ProgressBar progresbar ,*/String, String, String> {
//        MyTask2(/*ProgressBar progressBar*/) {
//
//        }
//
//        @Override
//        protected void onPreExecute() {
////            if (asyncTaskList.isEmpty()) {
////                asyncTaskList.add(this);
////            } else {
////
////            }
//        }
//
//        @Override
//        /*synchronized*/ protected String doInBackground(String... strings) {
//
//            for (int i = 0; i < 30; i++) {
//                try {
//                    Thread.sleep(500);
//                    Log.e("b", " :true");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                publishProgress(String.valueOf(i));
//            }
//            // chon inja threadie ke be ui dastresi nadare karaye intori inja anjam mishe
////            v karaye mostaghim dar onprogres bara hamin inja sleep mikonim chon mostaghim nist v fekr konam ye thread digas
//            return "FF";
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            // dastresi be ui mostaghim
//
////            textView.setText("66666");
////            mProgressBar.setProgress(10);
//            mProgressBar2.setProgress(Integer.parseInt(values[0]));
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
////            asyncTaskList.remove(this);
////            if (asyncTaskList.isEmpty()) {
////
////            } else {
////
////            }
//        }
//    }
}
//<?php
//// aval bayad az data base ya php admine site ye data base sakht badesham table
//        if($_SERVER['REQUEST_METHOD']=='POST'){
//        include_once("config.php");
//
//        $foodname="";
//        $fooddescription="";
//        $foodhealthypercnt=0;
//        $fooddeliciospercnt=0;
//        $foodveganint=0;
//
//        $foodname=$_POST['filterfoodsname'];
//        $fooddescription=$_POST['filterdescriptions'];
//        $foodhealthy=$_POST['filterfoodshealthy'];
//        $fooddelicios=$_POST['filterfooddelicios'];
//        $foodvegan=$_POST['filterfoodvegan'];
//        $foodhealthypercnt=(int)$foodhealthy;
//        $fooddeliciospercnt=(int)$fooddelicios;
//        $foodveganint=(int)$foodvegan;
//
//        $desarray = array();
//        function changestrtostrs($str) {
//        $desarrayf = array();
//        $desarrayf=explode(".*", $str);
//
////$pizza  = "piece1 piece2 piece3 piece4 piece5 piece6";
////$pieces = explode(" ", $pizza);
////echo $pieces[1]; // piece2
//
//        return  $desarrayf;
//        }
//
//
//        $desarray=changestrtostrs($fooddescription);
//
//        function getdeschangetodb($des)    {
//        $dbstring="";
//
//        //  for(int i=0;i<count($des);i++)
//        //  {
//        //              $dbstring = $dbstring . " AND fooddescription LIKE '%"
//        //                 . $des[i] . "%'";
//        //      }
//        foreach($des as $index)
//        {
//        //  $dbstring = $dbstring . " AND fooddescription LIKE '%"
//        //    . $des[i] . "%'";
//        $dbstring = $dbstring . " AND description LIKE '%"
//        .$index."%'";
//        }
//
//        return  $dbstring;
//        }
//        $desmassage="";
//        $desmassage=getdeschangetodb($desarray);
//
//        function databool($b) {
//        $massagedataboole = "";
//        if ($b==1) {// faghat vegan
//        $massagedataboole = " AND vegan =1 ";
//        }
//        return $massagedataboole;
//        }
//
//        $url = ""; //update path as per your directory structure
//        $tablename="veganmanage";
//
//        $query = "SELECT * FROM veganmanage
//        WHERE name LIKE '%".$foodname."%'
//        AND healthy>".(string)$foodhealthypercnt."
//        AND delicios>".(string)$fooddeliciospercnt
//        //   ."AND vegan=".(string)$foodveganint
//        .databool($foodveganint)
//        .$desmassage;
//        // 	." AND description LIKE '%".$desmassage."%'";
//
//        // // 	LIMIT 6";
//        // 	$query = "SELECT * FROM veganmanage";
//
//
//        // if(mysqli_query($con, $query)){
//        $result= mysqli_query($con, $query);
//
//        $emparray = array();
//        if(mysqli_num_rows($result) > 0){
//        while ($row = mysqli_fetch_assoc($result)) {
//        $emparray[] = $row;
//        }
//        echo json_encode(array("status" => "true","message" => "Successfully file geted!" , "data" => $emparray , "aa"=>$desmassage) );
//        }
//        else{
//        echo json_encode(array("status" => "false","message" => "Failed1!") );
//        }
//        // }
//        // else{
//        //     echo json_encode(array( "status" => "false","message" => "Failed2!") );
//        // }
//        }
//        else{
//        echo json_encode(array( "status" => "false","message" => "Failed3!") );
//        }
//
//
//        ?>

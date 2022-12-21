package com.example.vgnmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.vgnmanage.Database.FoodDbHelper;
import com.example.vgnmanage.Database.MyfoodDb;
import com.example.vgnmanage.Filterspackage.Filters;
import com.example.vgnmanage.Filterspackage.FiltersActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;


//https://blog.faradars.org/top-android-studio-shortcuts/
//ctrl+shift+alt+l=moratabsazi   ctrl+z=bargasht be aghab  ctrl+shift+f=search  shift+delet=pak kardan yek khat    ctrl+/=commendkardan yek khat ya chand khat   ctrl+w abikardan ya entekhab nazdik tarin kod va gostareshesh ctrl+p neshon mide bara ye metod che parametraii mishe varedkard    altenter neshon dadan eror va rahe hal bara yek eror
//bara estefade az icon ha az mipmap kilik cahp new va assets misazim icon ro ya axo bad khodesh miad be drawble va toye resurce ha coleresho ezafe mikone va bara farakhone behtare mipmapo seda konim ke tarkibe 2 tasho biare

//    https://android-studio.ir/generating-signed-apk/  bara gereftan apk  mahal gharar giri to file apk app releas gharar dare
// bara erorei ke to relase dashtam neveshte bod az file kotline vali ba filter omadam avalesh dorost nashod bade chand daghighe okey shod albate yekar dige ham kardam nemidonam azine ya azon on karam inbodke toye file setting bild gradle chekbox generat *.ml ro bardashtam

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Food> foods;
    FoodAdapter foodAdapter;
    Switch switchbtn;

    FoodDbHelper foodDbHelper;
    MyfoodDb myfoodDb;

    Button buttonaddfood;

    boolean sendtonetforintentaddfood = false;

    public Context context = this;
    // bara daryaft database amade yek data base minevisim badesh mirim az view toolwindows devicefileexpelorer data data esme proje data base sho copy va dar assets gharar midim
    Button buttonfilter;

    ImageButton imageButtonadd_anotherfoods_totheonlinelistadapter;

    ImageView imageViewcurentfoodselected;
    ImageView imageView_dicline_mainactivity;

    GridLayoutManager gridLayoutManager;

    // chose food
    // basicly u chose from inter net
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonaddfood = findViewById(R.id.buttonaddfood);
        switchbtn = findViewById(R.id.switchbtn);

        buttonaddfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddFood.class);
                intent.putExtra("sendtonet", sendtonetforintentaddfood);
                startActivity(intent);
//                getIntent().getBundleExtra()

            }
        });


        foods = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleview);
        foodAdapter = new FoodAdapter(foods, this) {
            // mishe bara kole kelas extend ya impliment ham kard
            @Override
            public void mclick(Food food) {
                Log.i("abstract", "true");
                foodsclick(food);
            }
        };
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(foodAdapter);
        foodDbHelper = new FoodDbHelper(this);
        myfoodDb = new MyfoodDb(this);

//        foodDbHelper.insertFoodToDbwithoutid(food1);
//        foodDbHelper.deletFoodToDb(" id =" + -1);

//        myfoodDb.insertFoodToDbwithoutid(food1);

        Log.e("helooo", "sadaaaaaaaaa");

        switchbtn.setChecked(true);// baghiash to on resum

//        requestMultiplePermissions();
        Utils.requestMultiplePermissions(this);

        buttonfilter = findViewById(R.id.buttonfilters);
        buttonfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FiltersActivity.class));
            }
        });

        imageButtonadd_anotherfoods_totheonlinelistadapter = findViewById(R.id.imagebuttonaddanotherfoodstotheonlineadapter);
        //inja behesh mana midim vali be adapteremon goftim vaghti online bod neshon bede
        imageButtonadd_anotherfoods_totheonlinelistadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("add", "1");
                Task_Sendorder_andgetfoods_fromnet task_sendorderandgetfoodsfromnet = new Task_Sendorder_andgetfoods_fromnet(true);
                task_sendorderandgetfoodsfromnet.execute("");

            }
        });
    }

    @Override
    protected void onPause() {
//        Handler updateHandler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                loadWeatherData();
//                updateHandler.postDelayed(this, 10 * 60 * 1000);
//            }
//        };yani har 10 daghighe load kone data haro
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (switchbtn.isChecked() && !MakeConection.defualtstate1.hasfilterused) {
            refresh_adapter_from_mydatabase_without_filter();
        } else if (switchbtn.isChecked() && MakeConection.defualtstate1.hasfilterused) {
            refresh_adapter(MakeConection.defualtstate1.foodList_frommydatabase_app);
        } else if (!switchbtn.isChecked()) {
            refreshfoodfromnet();
        }
        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // taze mishod az caching ham estefade kard
                // ya ye list movaghat midadim ke harchi az server migereft ro mirikht tosh va moghe switch bemone va ono biare bala
                if (isChecked) {
                    MakeConection.defualtstate1.lastfoodid = 0;// vaghti avaz mishe dobare bayad az aval gereft
                    imageButtonadd_anotherfoods_totheonlinelistadapter.setVisibility(View.GONE);
                    MakeConection.defualtstate1.adapterisonline_andfromserver = false;
                    if (!MakeConection.defualtstate1.hasfilterused) {
                        refresh_adapter_from_mydatabase_without_filter();
                    } else if (MakeConection.defualtstate1.hasfilterused) {
                        refresh_adapter(MakeConection.defualtstate1.foodList_frommydatabase_app);
                    }
                } else if (!isChecked) {
                    MakeConection.defualtstate1.adapterisonline_andfromserver = true;
                    refreshfoodfromnet();
                    //                    SendFiltersAndGetFoods.sendfileandget(MakeConection.defualtstate1.filters, context);
                    //                        SendFiltersAndGetFoods sendFiltersAndGetFoods =
//                                new SendFiltersAndGetFoods(MakeConection.defualtstate1.filters);

                }
            }
        });


        Toast.makeText(this, MakeConection.defualtstate1.somting, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, MakeConection.defualtstate2.somting, Toast.LENGTH_SHORT).show();
        Log.e("xxx",
                MakeConection.defualtstate2.food.name +
                        MakeConection.defualtstate2.food.description +
                        String.valueOf(MakeConection.defualtstate2.food.delicios) +
                        String.valueOf(MakeConection.defualtstate2.food.healthy) +
                        String.valueOf(MakeConection.defualtstate2.food.veg) +
                        String.valueOf(MakeConection.defualtstate2.food.imgurl)
//                        String.valueOf(MakeConection.defualtstate2.food.imgpath)
        );

        boolean portrait = (findViewById(R.id.myfrag) != null);
        if (portrait) {
            MyFragment myFragment = MyFragment.newInstance("");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.myfrag, myFragment)
                    .commit();

            FrameLayout frameLayout = findViewById(R.id.myfrag);
//            Toast.makeText(context, "portrait", Toast.LENGTH_SHORT).show();
            frameLayout.setVisibility(View.GONE);// ba in mishe ham view v ham fragmento az beyn bord
        } else {
            Toast.makeText(context, "landscape", Toast.LENGTH_SHORT).show();

        }

    }

    private void refreshfoodfromnet() {
        Task_Sendorder_andgetfoods_fromnet task_sendorderandgetfoodsfromnet = new Task_Sendorder_andgetfoods_fromnet(false);
        task_sendorderandgetfoodsfromnet.execute("");
        imageButtonadd_anotherfoods_totheonlinelistadapter.setVisibility(View.VISIBLE);
//        refresh_adapter_addfoodstoadapter dar akhar baz ino seda mizane
    }

    private void refresh_adapter_from_mydatabase_without_filter() {
        foods.clear();
        foods.addAll(myfoodDb.getfoods(null, null));// dorost kar kard
        foodAdapter = new FoodAdapter(foods, this) {
            // mishe bara kole kelas extend ya impliment ham kard
            @Override
            public void mclick(Food food) {

                Log.i("abstract", "true");
                foodsclick(food);
            }
        };
        Log.e("from mydata base", String.valueOf(MakeConection.defualtstate1.hasfilterused));

        foodAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(foodAdapter);

    }

    private void refresh_adapter(List<Food> foodList) {

        // vaghti ofline food ro daryaft mikonim tamame kar ha dar activity filter anjam mishe va list ghaza ro be ma mide
        // vali bara online ma faghat filter haro migirim mifrestim be server va onja baresi mikone va list food haro mide be ma
        foods.clear();
        foods.addAll(foodList);
        Log.e("from filter", String.valueOf(MakeConection.defualtstate1.hasfilterused));
        foodAdapter = new FoodAdapter(foods, this) {
            // mishe bara kole kelas extend ya impliment ham kard
            @Override
            public void mclick(Food food) {
                Log.i("abstract", "true");
                foodsclick(food);
            }
        };
//        foods.clear();

        foodAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(foodAdapter);
    }

//    private void refresh_adapteradd(Food food) {
////        foods.clear();
////        foods.addAll(foodList);
//        foods.add(food);////*
//        Log.e("from filter", String.valueOf(MakeConection.defualtstate1.hasfilterused));
//        foodAdapter = new FoodAdapter(foods, this) {
//            // mishe bara kole kelas extend ya impliment ham kard
//            @Override
//            public void mclick(Food food) {
//                Log.i("abstract", "true");
//                foodsclick(food);
//            }
//        };
////        foods.clear();
//
//        foodAdapter.notifyDataSetChanged();
//        recyclerView.setAdapter(foodAdapter);
//    }

    private void refresh_adapter_addfoodstoadapter(List<Food> foodslist, boolean clearlist) {
        if (clearlist) {
            foodAdapter.foods.clear();
        }
        Log.e("before", foods.toString());
        Log.e("list", foodslist.toString());
        foods.addAll(foodslist);
        Log.e("after", foods.toString());
        Log.e("from filter", String.valueOf(MakeConection.defualtstate1.hasfilterused));
//        foodAdapter = new FoodAdapter(foods, this) {
//            // mishe bara kole kelas extend ya impliment ham kard
//            @Override
//            public void mclick(Food food) {
//                Log.i("abstract", "true");
//                foodsclick(food);
//            }
//        };
//        inam mishe vali refresh mishe

        foodAdapter.foods.addAll(foodslist);
//  dadan ye foods jadid
        foodAdapter.notifyDataSetChanged();
//        recyclerView.setAdapter(foodAdapter);
        recyclerView.smoothScrollToPosition(MakeConection.defualtstate1.curent_recycle_itemposition);
//MakeConection.defualtstate1.curentrecycleitemposition = position akharin view load shode ke az onbine adapter migirim
//recyclerView.smoothScrollToPosition(); dastore inke bere be koja ya az koja ya kodom position shoro kone
    }
// baraye add shodan edame foods ha az server bedone inke bere az aval list neshon bede

    private void refresh_adapter_addfoodswithpagination(List<Food> foodslist) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
//                if (!isLoading() /*&&!isLastPage()*/) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {// in kar darvaghe
                    // ine ke miad baresi mikone be akharin iteme listemon residim ya na va in
                    // ke farghesh ba last position ine ke in painn tarin view ro mide ke yekam daghigh tare ke ba onam mishe
                    // va albate onja behesh barash button ham tarif kardim ke vaghti kilik shod biad inkararo bokone
                    // ke intoriam mishe inja khod be khod vaghti be tah list mirese inkaro mikone
                    // inja ham data haro migire badesh mide be adapter va dakhel liste adapter gharar mide
                    // va dige lazem be refresh nist chon dar list hazer adapter gharar migire
                    foodAdapter.foods.addAll(foodslist);
                    foodAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    // baraye bedone button click kardan va ezafe shodan khod be khod

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu subMenu;
        MenuItem menuItem;

        menu.add("ur recomend food").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                intent.putExtra("sendtonet", true);
                // inam mishod
                Intent intentm = new Intent(MainActivity.this, AddFood.class);
                MakeConection.defualtstate1.justsendtonet = true;
                startActivity(intentm);
                return false;
            }
        });
        subMenu = menu.addSubMenu("update or email ");
        MenuItem menuitem1 = subMenu.add("update").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(context, "check play store", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        MenuItem menuItem2 = subMenu.add("email")/*.setIcon(R.drawable.veganfood)*/.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setTitle("for help or the coder email:");
                alertBuilder.setMessage("ghaemmmaggamierfan@gmail.com");
                alertBuilder.create().show();

                return false;
            }
        });
        menu.add("myfood")
//                .setIcon(R.drawable.ic_launcher_background)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    private void basefoods() {
        InputStream input = getResources().openRawResource(R.raw.foods);
        List<Food> foods = new ArrayList<>();
//        foods =new FoodXmlParser(input).parseXml();

        Log.i("JdomParser", "Returned " + foods.size() + " movies.");
    }

    private void foodsclick(Food food) {
//        new Handler().postAtTime(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(context, "wooooow", Toast.LENGTH_SHORT).show();
//            }
//        }, 1000);
        if (MakeConection.defualtstate1.adapterisonline_andfromserver) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("food").setItems(new String[]{
                    "rate this food(healthy or delicios)", "ditails and photo",
                    "report this food", "add this food to myfoods", "just photo"
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {

                    } else if (which == 1) {
                        FrameLayout frameLayout = findViewById(R.id.frameimagefood);
                        frameLayout.setVisibility(View.VISIBLE);
                        String url = food.imgurl;
                        Ditails_Image_foodFragment ditailsImagefoodFragment;
//                    ditailsImagefoodFragment = Ditails_Image_foodFragment.newInstance(url);
                        ditailsImagefoodFragment = new Ditails_Image_foodFragment(frameLayout, context, food);
                        // inja baraye ravesh dadan data framelayout ro midim vali to paiin to replace
                        ditailsImagefoodFragment.text = " name: " + food.name
                                + "\n healthypercent: " + food.healthy
                                + "\n deliciospercent: " + food.delicios
                                + "\n vegan: " + food.veg
                                + "\n description: " + food.description
                        ;
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameimagefood, ditailsImagefoodFragment)
                                //dar vaghe miaim jaye framelayout ro ba framlayoutfragment avaz mikonim
                                .commit();
                        dialog.dismiss();
                    } else if (which == 2) {

                    } else if (which == 3) {
//                        imageView.invalidate();
//                        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//                        Bitmap bitmap = drawable.getBitmap();

                        //intori az internet baraye ax bitmapesho migirim va be file tabdilesh mikonim
                        Picasso.get()
                                .load(food.imgurl)
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        food.imgurl = Utils.saveimage_bitmaptofilepath(bitmap, food.name,
                                                "/VgnManage/addfood/imagefood", context);
//                                        myfoodDb.insertFood_ToDb_without_giveid(food);
                                        myfoodDb.insertFoodToDbwithoutid(food);

                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    }
                                });
                    }
                    if (which == 4) {
                        imageViewcurentfoodselected = findViewById(R.id.imageviewcurentfoodselected);
                        myanimzoom(imageViewcurentfoodselected, food);
                    }
                }
            });
            alertBuilder.create().show();

        } else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("food").setItems(new String[]{
                    "delet food", "ditails and photo", "just photo"
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        File file = new File(food.imgurl);
                        if (file.exists()) {
                            file.delete();
                        }
                        // myfoodDb.deletFoodToDb(" name =" + food.name);// ba name ya chizaye dige ham mishe vali momkene chon tekrari bashan eror bedan
                        myfoodDb.deletFoodToDb(" id =" + food.id);// ba name ya chizaye dige ham mishe vali momkene chon tekrari bashan eror bedan
                        // ontori bayad begim food haii ba in esmo bede ba idishono bad age 1 ki bodan pak kon
                        dialog.dismiss();
                        refresh_adapter_from_mydatabase_without_filter();
                        Toast.makeText(context, "deleeeet reallyyy?", Toast.LENGTH_SHORT).show();
                    } else if (which == 1) {
                        FrameLayout frameLayout = findViewById(R.id.frameimagefood);
                        frameLayout.setVisibility(View.VISIBLE);
                        String url = food.imgurl;
                        File file = new File(url);
                        Ditails_Image_foodFragment ditailsImagefoodFragment;
//                    ditailsImagefoodFragment = Ditails_Image_foodFragment.newInstance(url);
                        ditailsImagefoodFragment = new Ditails_Image_foodFragment(frameLayout, context, food);
                        // albate baraye jabeja kardan framlayout ba layoutfrragment(ke onam framelayoute) akhare code inja mikonim va na to khode fragment vali bara hazfesh az dakhel fragment framelayoutro mibandim
                        ditailsImagefoodFragment.text = " name: " + food.name
                                + "\n healthypercent: " + food.healthy
                                + "\n deliciospercent: " + food.delicios
                                + "\n vegan: " + food.veg
                                + "\n description: " + food.description
                        ;
//                        ditailsImagefoodFragment.imagefood.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                myanimzoom(ditailsImagefoodFragment.imagefood);
//                            }
//                        });
                        if (file.exists()) {
                            ditailsImagefoodFragment.imageurl = url;
                        } else {
                            Toast.makeText(context, "sory this food hasnt any picture", Toast.LENGTH_SHORT).show();
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameimagefood, ditailsImagefoodFragment)
                                //dar vaghe miaim jaye framelayout ro ba framlayoutfragment avaz mikonim
                                .commit();
                        dialog.dismiss();
//                        frameLayout.setVisibility(View.VISIBLE); farghi nadare inja ham mishe gozasht ham bala

                    } else if (which == 2) {
                        imageViewcurentfoodselected = findViewById(R.id.imageviewcurentfoodselected);
                        myanimzoom(imageViewcurentfoodselected, food);
                    }
                }
            });
            alertBuilder.create().show();
        }

        //        alertBuilder.setIcon(food.imgurl)
//        alertBuilder.setMessage("would you like recomend this to us?");

//        alertBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        alertBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                sendtomainactivity();
//            }
//        });
//
//        alertBuilder.setNeutralButton("both", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(AddFood.this, "seccesfull", Toast.LENGTH_SHORT).show();
//                sendtonet();
//                sendtomainactivity();
//            }
//        });

        Toast.makeText(context, String.valueOf(food.id), Toast.LENGTH_SHORT).show();
    }

//    public void hey(View view) {
//        Toast.makeText(context, "heyyyyyyylgcsdhfc", Toast.LENGTH_SHORT).show();
//    }

    public class Task_Sendorder_andgetfoods_fromnet extends AsyncTask</*ProgressBar progresbar ,*/String, List<Food>, String> {

        ProgressDialog progressDialog = new ProgressDialog(context);
        Task_Sendorder_andgetfoods_fromnet thistask = this;// darvaghe migim in task ro beriz to moteghaier thistask na inke berizim to this this sabete az ghabl bode
        boolean addnewfoods_to_last_serverfoodslist = false;

        Task_Sendorder_andgetfoods_fromnet(/*ProgressBar progressBar*/boolean addnewfoods) {
            this.addnewfoods_to_last_serverfoodslist = addnewfoods;
        }

        @Override
        protected void onPreExecute() {
//            if (asyncTaskList.isEmpty()) {
            // chon ke avalesh age ,meghdar avlie ya hamon empty nadim behesh v null bashe eror mide
// chon ke empty nist v nulle mire to else v hichi meghdar nemigirie bara hamin mire akharesh metod akhar ke remov kone bug mide
//                asyncTaskList.add(this);
//            } else {
//
//            }

            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... string) {
//                Thread.sleep(5000);

            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    return;
                }
            });

            Filters filter = MakeConection.defualtstate1.filters;//akharin filteri ke gereftim
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConectionWithNet.Url1)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();//kode makhsos bara ertebat ba net (ferestadan parametra be net)

            RequestBody req_foodname = RequestBody.create(MediaType.parse("text/plain"), filter.foodsname);
            RequestBody req_fooddes = RequestBody.create(MediaType.parse("text/plain"), filter.change_liststring_to_onemassagestring(filter.descriptionslist));
            // bara dadan in ham mishod kelaso abstraco jori nevesht ke araye ro bede
            // ya inke kolesho to ye massage descriptions bedi onja joda koni ya massage data basesho inja bedim
            RequestBody req_foodhealthy = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(filter.foods_healthy_percent_most_be_more_than));
            RequestBody req_fooddelicios = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(filter.foods_delicios_percent_most_be_more_than));
            int intveg = (filter.vegan) ? 1 : 0;
            RequestBody req_foodvegan = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(intveg));
            int lastfoodid = MakeConection.defualtstate1.lastfoodid;
            RequestBody req_lastfoodid = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lastfoodid));

            ConectionWithNet getResponse = retrofit.create(ConectionWithNet.class);
            Call<String> call = getResponse.uploadfiltersandgetfoods(req_foodname, req_fooddes, req_foodhealthy,
                    req_fooddelicios, req_foodvegan, req_lastfoodid);// yek no interface ke inja behesh parametr hasho midim v be inja midim khode retofit tarifesh mikone


            Log.d("assss", "asss");
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("mullllll", response.body().toString());
                    Food food = new Food();
                    List<Food> foods = new ArrayList<>();
                    final List<Food> curentlistfood = new ArrayList<>();
                    try {
                        //      JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        jsonObject.toString().replace("\\\\", "");
                        if (jsonObject.getString("status").equals("false") &&
                                jsonObject.getString("message").equals("Failed1!")
                            // yani khalie
                        ) {
                            Toast.makeText(MainActivity.this, "sorry food finished", Toast.LENGTH_SHORT).show();
                            imageButtonadd_anotherfoods_totheonlinelistadapter.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            MakeConection.defualtstate1.foodList_fromnet = curentlistfood;
                            publishProgress(foods);
                            return;
                            // in harekat baes mishe baghie tabe ejra nashe va inke dar akhar null return kone
                        } else if (jsonObject.getString("status").equals("false")) {
                            Toast.makeText(MainActivity.this, "sorry or try again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            return;
                        } else if (jsonObject.getString("status").equals("true")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            progressDialog.dismiss();

                            String url = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectinstance = jsonArray.getJSONObject(i);

                                Log.e("foood", String.valueOf(jsonObjectinstance));
//                            food.imgurl = jsonObjectinstance.optString("imageurl");
//                            food.imgurl = jsonObjectinstance.getString("imageurl");  fargi nadaran bejoz inke opt default dare vali get na null mide va opt empty mide age nabashe fek konam
                                food.imgurl = jsonObjectinstance.optString("imageurl", "");
                                food.name = jsonObjectinstance.optString("name", "");
                                Log.e("foood", String.valueOf(jsonObjectinstance.opt("imageurl")));
                                int veganint = jsonObjectinstance.optInt("vegan", 0);
                                if (veganint == 1) {
                                    food.veg = true;
                                } else if (veganint == 0) {
                                    food.veg = false;
                                }
                                food.healthy = jsonObjectinstance.optInt("healthy", 0);
                                food.delicios = jsonObjectinstance.optInt("delicios", 0);
                                food.description = jsonObjectinstance.optString("description", "");
                                food.id = jsonObjectinstance.optLong("id", 0);
                                Log.e("food", String.valueOf(food.name));
                                foods.add(i, food);//bara in i ya index dadim ke bar asas tedad listi ke gereftim hamahang konim
                                Log.e("food", String.valueOf(foods.get(0).name));
//                                Log.e("curentlistfood", String.valueOf(curentlistfood.get(0).name));
                                final Food curentfood = new Food(food.id, food.name, food.description, food.imgurl
                                        , food.healthy, food.delicios, food.veg, true);
                                curentlistfood.add(curentfood);
                            }//khondan ya download az mahale gharar gereftan file to server
//                        Picasso.get().load(url).into(imageView);
                            Log.e("foodsdsff", String.valueOf(foods.get(0).name)
                                    + "   "
                                    + curentlistfood.get(0).name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MakeConection.defualtstate1.foodList_fromnet = curentlistfood;
                    ////* list mamoli ham mizaram ta bebini ke hamash avaz mishe
//  list ro harkari mikardam araye akhare listo be tamam arayeha midad bara hamin ye final list v ye object final ke yebar tarif mishe behesh dadam
                    publishProgress(foods);////*  // bara onprogress in hatman bayad seda zade she
// albate rahaye dige ham bod masalan done done be publich food ro bedim v be adapter add konim ya karaye dige
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.d("gttt", call.toString() + t.toString());
//                    Toast.makeText(MainActivity.this, "sorry pleas turn on the internet or try again", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
//                    androidx.appcompat.app.AlertDialog alertDialog=
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setTitle("erorr").setMessage("sorry pleas turn on the internet or try again")
                            .setPositiveButton("try again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Task_Sendorder_andgetfoods_fromnet task_sendorderandgetfoodsfromnet = new Task_Sendorder_andgetfoods_fromnet(addnewfoods_to_last_serverfoodslist);
                                    task_sendorderandgetfoodsfromnet.execute("");
                                    imageButtonadd_anotherfoods_totheonlinelistadapter.setVisibility(View.VISIBLE);
                                    thistask.cancel(true);
                                    // fek konam vaghti kilic gozine ya kharej az view beshe khod be khod dismiss mishe
                                }
                            });
                    alertBuilder.setNegativeButton("i will check the internet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            thistask.cancel(true);
                        }
                    });
                    alertBuilder.create().show();
                    //                    alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//                    })
                }
            });

            // chon inja threadie ke be ui dastresi nadare karaye intori inja anjam mishe
//            v karaye mostaghim dar onprogres bara hamin inja sleep mikonim chon mostaghim nist v fekr konam ye thread digas
            return "FF";
        }

        @Override
        protected void onProgressUpdate(List<Food>... values) {
            // dastresi be ui mostaghim
            //in thread asli masalan inja nemishe ba view ha kar kard
            try {
                Log.e("getfood", String.valueOf(values[0].get(0).name)
//                    + String.valueOf(MakeConection.defualtstate1.foodList_fromnet.get(1).name)
//                    + String.valueOf(MakeConection.defualtstate1.foodList_fromnet.get(0).name)
                );
//            refresh_adapteradd(values[0].get());
            } catch (Exception e) {

            }
            if (MakeConection.defualtstate1.foodList_fromnet.isEmpty()) {
                Log.e("internetlist", "empty");
            } else {
                MakeConection.defualtstate1.lastfoodid = (int) MakeConection.defualtstate1.foodList_fromnet
                        .get(MakeConection.defualtstate1.foodList_fromnet.size() - 1).id;
//                MakeConection.defualtstate1.lastfoodid = (int) values[0].get(values[0].size() - 1).id; inam ehtemalan beshe
                //akharin id object foodi ke dakhel liste netemon gereftim
            }

            if (addnewfoods_to_last_serverfoodslist) {
                Log.e("add", "2");
                refresh_adapter_addfoodstoadapter(MakeConection.defualtstate1.foodList_fromnet, false);
            } else {
                Log.e("add", "3");
                refresh_adapter(MakeConection.defualtstate1.foodList_fromnet);
            }
//            mProgressBar.setProgress(Integer.parseInt(values[0]));
        }
        //in thread asli masalan inja nemishe ba view ha kar kard

        @Override
        protected void onPostExecute(String s) {
//            asyncTaskList.remove(this);
//            if (asyncTaskList.isEmpty()) {
//
//            }
//            else  (){
//
//            }
        }
    }

    private void myanimzoom(ImageView imageView, Food food) {
        imageView_dicline_mainactivity = findViewById(R.id.imageViewdiclineshowphoto);
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
//        imageView.startAnimation(anim);
        imageView_dicline_mainactivity.setVisibility(View.VISIBLE);
        imageView_dicline_mainactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_dicline_mainactivity.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                return;
            }
        });
        if (food.fromsever_internet) {
            Picasso.get().load(food.imgurl).into(imageView);
        } else {
            File file = new File(food.imgurl);
            if (file.exists()) {
                imageView.setImageURI(Uri.parse(food.imgurl));
            } else {
                imageView.setImageResource(R.drawable.veganfood);
            }
        }
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setVisibility(View.VISIBLE);
        imageView.setTranslationY(-5000);// meghdar haye avalie
        imageView.setTranslationX(-1000);
//        imageView.setAlpha();
        imageView.animate().alpha(1f)
//                .translationYBy()jaygahash dar safhe
                .setDuration(1500).translationYBy(5000).translationXBy(1000);
//            .rotationY(360); daraje charkhesh be mehvar y
//                .scaleX(2).scaleY(2)// meghdar bozorgshodan
//                .translationYBy(2000);//2000dp be samt pain biad
    }

}
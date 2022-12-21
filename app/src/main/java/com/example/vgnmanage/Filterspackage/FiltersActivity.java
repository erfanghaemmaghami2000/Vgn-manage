package com.example.vgnmanage.Filterspackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.vgnmanage.Database.MyfoodDb;
import com.example.vgnmanage.Food;
import com.example.vgnmanage.MakeConection;
import com.example.vgnmanage.R;

import java.util.ArrayList;
import java.util.List;

public class FiltersActivity extends AppCompatActivity {
    ViewPager viewPager;
    Switch switchmorefilters;
    Button buttenfinishfilters;
    FragmentPager fragmentPager;
    List<Fragment> fragments = new ArrayList<>();

    Filter1fragment filter1fragment;
    Filter2fragment filter2fragment;
    Filters filters = new Filters();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        viewPager = findViewById(R.id.viewpagerfilters);
        switchmorefilters = findViewById(R.id.switchshowmorfilters);
        buttenfinishfilters = findViewById(R.id.buttonfinishfilters);

//        filter1fragment = Filter1fragment.newInstance();
//        filter1fragment = new Filter1fragment() {
//            @Override
//            void seterorforfrag1andgivethefragbody(String foodsname, Integer foods_delicios_percent_most_be_more_than, Integer foods_healthy_percent_most_be_more_than, boolean vegan) {
//                filters.foodsname = foodsname;
//                filters.foods_delicios_percent_most_be_more_than = foods_delicios_percent_most_be_more_than;
//                filters.foods_healthy_percent_most_be_more_than = foods_healthy_percent_most_be_more_than;
//                filters.vegan = vegan;
//                Log.e("filte", filters.foodsname +
//                        String.valueOf(filters.foods_delicios_percent_most_be_more_than)
//                        + String.valueOf(filters.foods_healthy_percent_most_be_more_than)
//                        + filters.vegan);
//    }
//};
//        filter1fragment = new Filter1fragment() {};
//        filter1fragment = new Filter1fragment() {
//            @Override
//            void aVoid() {
//
//            }
//        };
        filter1fragment = new Filter1fragment();
//        filter2fragment = Filter2fragment.newInstance();
        filter2fragment = new Filter2fragment(getApplicationContext());

        buttenfinishfilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filters = filter1fragment.getdata();
                if (filters.foods_delicios_percent_most_be_more_than < 0 || filters.foods_delicios_percent_most_be_more_than > 100) {
                    filter1fragment.editTextdelicios.setError("pleas enter more than -1 and less than 101");
                }
                if (filters.foods_healthy_percent_most_be_more_than < 0 || filters.foods_healthy_percent_most_be_more_than > 100) {
                    filter1fragment.editTexthealthy.setError("pleas enter betwen 0 and 100");
                }
                filters.descriptionslist = filter2fragment.getdata().descriptionslist;
                // mishod bejoz jam az yek kelas filter koli ya kelas sevom estefade kard ya ... vali inja ma filter2 ro ezafe kardim be 1
                //filters.foodsname=filter1fragment.getdata().foodsname;filters.descriptionslist=filter2fragment.getdata().descriptionslist;
                // ya hata bedone get data az activity ya kelas mostaghim migereftim

                filters.loging();

                //search bara data base dakhel app
                List<Food> foods = new ArrayList<>();
                MyfoodDb myfoodDb = new MyfoodDb(getApplicationContext());
                foods = myfoodDb.searchFood(
                        "name LIKE '%" + filters.foodsname + "%'"
                                + devid_list_to_database_AND_msg(filters.descriptionslist)
//                                " AND " + "description LIKE '%"
//                                + filters.changelisttostr(filters.details) + "%'"
//  ADADO KHODESH BE STRING TABDIL MIKONE chone string daryaft mikone pas lazem be tabdilesh nist
                                + " AND healthy>" + String.valueOf(filters.foods_healthy_percent_most_be_more_than)
                                + " AND delicios>" + filters.foods_delicios_percent_most_be_more_than
//                                + " AND veg ="
                                + databool(filters.vegan)

//                                + " OR " +
                        , "10");// ham khodesho neshon mide ham shabihasho neshon mide

//                SELECT name FROM Products WHERE name LIKE %$search[1]% AND name LIKE %$search[2]% LIMIT 6


                MakeConection.defualtstate1.hasfilterused = true;
                MakeConection.defualtstate1.filters = filters;
                MakeConection.defualtstate1.foodList_frommydatabase_app = foods;
                MakeConection.defualtstate1.lastfoodid = 0;//fekr konam baadan to main avaz mishe
                finish();
            }
        });

        fragments.add(filter1fragment);
        fragments.add(filter2fragment);

        fragmentPager = new FragmentPager(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragmentPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    switchmorefilters.setChecked(false);
                } else if (position == 1) {
                    switchmorefilters.setChecked(true);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        switchmorefilters.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    if (viewPager.getCurrentItem() != 0) {
                        viewPager.setCurrentItem(0);
                    }
//                    viewPager.setCurrentItem(0);
                }
                if (isChecked) {
                    if (viewPager.getCurrentItem() != 1) {
                        viewPager.setCurrentItem(1);
                    }
//                    viewPager.setCurrentItem(1);
                }
            }
        });
        // in yekam bug dare
    }

    String devid_list_to_database_AND_msg(List<String> strings) {
        Log.e("log", String.valueOf(strings));
        String massage = "";
        if (!strings.isEmpty() && strings != null) {
            for (String s : strings) {
                massage = massage + " AND description LIKE '%"
                        + s + "%'";
            }
        }
        Log.e("log", massage);

        return massage;
    }

    String databool(Boolean b) {
        String massage = "";
        if (b) {
            // faghat agar vegan bod search kone dar gheir in sorat hame ro neshon bede
            massage = " AND veg =1";
        }
//        else if (!b) {
//            massage = " AND veg =1";
//            massage = massage + " OR veg =0";
        // ya age 3 halat dasht khastim 2 halatesho dashte bashim fek
        // konam bayad 3 halatesho az db begirim badesh inja entekhab konim
        // age filteraye dige nabashan ba or ham mishe nevesht
//        }

        return massage;
    }

    String devid_list_to_database_OR_msg(List<String> strings) {
        String massage = " ";
        for (String s : strings) {
            massage = massage + " OR " + "description LIKE '%"
                    + s + "%'";
        }

        return massage;
    }
}

//class GetflowerFromMyDb {
//    private Context context;
//
//    GetflowerFromMyDb(Context context, Filter filters) {
//        this.context = context;
//        List<Food> foods = new ArrayList<>();
//        MyfoodDb myfoodDb = new MyfoodDb(context);
//        foods = myfoodDb.searchFood(
//                "name LIKE '%" + filters.foodsname + "%'"
//                        + devid_list_to_database_ANDmsg(filters.descriptions)
////                                " AND " + "description LIKE '%"
////                                + filters.changelisttostr(filters.details) + "%'"
//
//                        + " AND healthy>" + filters.foods_healthy_percent_most_be_more_than
//                        + " AND delicios>" + filters.foods_delicios_percent_most_be_more_than
//
////                                + " AND veg ="
//                        + databool(filters.vegan)
//
////                                + " OR " +
//                , "10");// ham neshon mide ham shabihasho neshon mide
//
////                SELECT name FROM Products WHERE name LIKE %$search[1]% AND name LIKE %$search[2]% LIMIT 6
//
//    }
//
//}
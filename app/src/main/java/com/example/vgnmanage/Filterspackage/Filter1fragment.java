package com.example.vgnmanage.Filterspackage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.vgnmanage.MakeConection;
import com.example.vgnmanage.R;

public /*abstract*/ class Filter1fragment extends Fragment {
    // fragment fek konam nemitone abstract beshe
    public Filter1fragment(/*int somthing*/) {
    }

//    public static Filter1fragment newInstance() {
//        Filter1fragment fragment = new Filter1fragment() {
//            void aVoid() {
//            }
//        };
//        return fragment;
//    }

//    public static Filter2fragment newInstance(String param1, String param2) {
//        Filter2fragment fragment = new Filter2fragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView textView;
    EditText editTextname;
    EditText editTextdelicios;
    EditText editTexthealthy;
    Switch switchvegan;
    public String foodsname = "";
    public int foods_delicios_percent = 0;
    public int foods_healthy_percent = 0;
    public boolean vegan = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter1fragment, container, false);
        editTextname = view.findViewById(R.id.editTextfoodnameincludefilter);
        editTextdelicios = view.findViewById(R.id.editTextfoodsdeliciosfilter);
        editTexthealthy = view.findViewById(R.id.editTextfoodshealthyfilter);
        switchvegan = view.findViewById(R.id.switchfoodveganfilter);
        switchvegan.setChecked(false);
//        // beja absracto ina ham mishod data haro be kelas vaset dad v chizhaye morede niazam ba seda zadan kelas gereft
//        seterorforfrag1andgivethefragbody(foodsname, foods_delicios_percent_most_be_more_than, foods_healthy_percent_most_be_more_than, vegan);
        switchvegan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vegan = isChecked;
            }
        });
        if (MakeConection.defualtstate1.hasfilterused) {
            if (!MakeConection.defualtstate1.filters.foodsname.isEmpty() &&
                    MakeConection.defualtstate1.filters.foodsname != null) {
                Log.e("er", MakeConection.defualtstate1.filters.foodsname);
                foodsname = MakeConection.defualtstate1.filters.foodsname;
                editTextname.setText(foodsname);
            }
            if (MakeConection.defualtstate1.filters.foods_delicios_percent_most_be_more_than != 0
                    && MakeConection.defualtstate1.filters.foods_delicios_percent_most_be_more_than != null) {
                foods_delicios_percent = MakeConection.defualtstate1.filters.foods_delicios_percent_most_be_more_than;
                editTextdelicios.setText(String.valueOf(foods_delicios_percent));
            }
            if (MakeConection.defualtstate1.filters.foods_healthy_percent_most_be_more_than != 0
                    && MakeConection.defualtstate1.filters.foods_healthy_percent_most_be_more_than != null) {
                foods_healthy_percent = MakeConection.defualtstate1.filters.foods_healthy_percent_most_be_more_than;
                editTexthealthy.setText(foods_healthy_percent);
            }
            vegan = MakeConection.defualtstate1.filters.vegan;
            switchvegan.setChecked(vegan);
        }

        return view;
    }

    Filters getdata() {
//        if (editTextname != null && editTextname.getText().toString() != null
//                && !editTextname.getText().toString().isEmpty()) {
        foodsname = editTextname.getText().toString();
//        }
//        if (editTextdelicios != null && editTextdelicios.getText().toString() != null
//                && !editTextdelicios.getText().toString().isEmpty()) {
//        foods_delicios_percent_most_be_more_than = Integer.valueOf(editTextdelicios.getText().toString());
        //        }
        String s = editTextdelicios.getText().toString();
        if (s == "" || editTextdelicios.getText().toString().isEmpty()) {
            foods_delicios_percent = 0;
        } else {
            foods_delicios_percent = Integer.parseInt(s);
        }
//        if (editTexthealthy != null && editTexthealthy.getText().toString() != null
//                && !editTexthealthy.getText().toString().isEmpty()) {
//        foods_healthy_percent_most_be_more_than = Integer.valueOf(editTexthealthy.getText().toString());
//        }
        String s2 = editTexthealthy.getText().toString();
        if (s2 == "" || editTexthealthy.getText().toString().isEmpty()) {
            foods_healthy_percent = 0;
        } else {
            foods_healthy_percent = Integer.parseInt(s2);
        }
        vegan = switchvegan.isChecked();
        Filters filters = new Filters();
        filters.foodsname = foodsname;
        filters.foods_delicios_percent_most_be_more_than = foods_delicios_percent;
        filters.foods_healthy_percent_most_be_more_than = foods_healthy_percent;
        filters.vegan = vegan;

        return filters;
    }
//    abstract void seterorforfrag1andgivethefragbody(String foodsname, Integer foods_delicios_percent_most_be_more_than, Integer foods_healthy_percent_most_be_more_than, boolean vegan);


}
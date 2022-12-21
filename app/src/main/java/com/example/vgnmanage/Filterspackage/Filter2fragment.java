package com.example.vgnmanage.Filterspackage;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vgnmanage.MakeConection;
import com.example.vgnmanage.R;

import java.util.ArrayList;
import java.util.List;

public class Filter2fragment extends Fragment {

    //    List<DetFood> detFoods = new ArrayList<>();
    public List<String> detFoods = new ArrayList<>();

    Context context;
    int iddet = -1;

    public Filter2fragment(Context context) {
        this.context = context;

    }

//    public static Filter2fragment newInstance() {
//        Filter2fragment fragment = new Filter2fragment();
//
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
        if (getArguments() != null) {
        }
    }

    TextView textView;
    EditText editText;
    Button buttonadd;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter2fragment, container, false);
        textView = view.findViewById(R.id.textviewfrag2);
        editText = view.findViewById(R.id.editTextfrag2);
        buttonadd = view.findViewById(R.id.buttonfrag2);
        listView = view.findViewById(R.id.listditailsfrag2);

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString() != null && editText != null &&
                        !editText.getText().toString().isEmpty()) {
                    String det = "";
                    det = editText.getText().toString();
                    detFoods.add(det);
                    refreshadapter();
                } else {
                    Toast.makeText(context, "pleas enter some details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setClickable(true);
//        listView.getSelectedView()
//        listView.getRootView().setOnClickListener();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//                alertBuilder.setTitle("delet?").setItems(new String[]{
//                        "yes", "no"
//                }, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == 0) {
                detFoods.remove(position);
                refreshadapter();
//                        }
//                    }
//                });
//                alertBuilder.create().show();
            }
        });

        //        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(context, "hhhhhhhhhheh", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        if (MakeConection.defualtstate1.hasfilterused) {
            if (!MakeConection.defualtstate1.filters.descriptionslist.isEmpty()
                    && MakeConection.defualtstate1.filters.descriptionslist != null) {
                for (String s : MakeConection.defualtstate1.filters.descriptionslist) {
                    detFoods.add(s);
                }
                refreshadapter();
//                editTexthealthy.setText(MakeConection.defualtstate1.filters.foods_healthy_percent_most_be_more_than);
            }
        }
        refreshadapter();
        return view;
    }

    private void refreshadapter() {
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter(context,
                        android.R.layout.simple_list_item_1,
                        detFoods);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

    }

    Filters getdata() {
        Filters filters = new Filters();
        filters.descriptionslist = detFoods;
        return filters;
    }

//    class DetFood {
//        DetFood(String name, int id) {
//            this.name = name;
//            this.id = id;
//        }
//
//        public String name = "";
//        public int id = -1;
//
//        @Override
//        public String toString() {
//            return "DetFood{" +
//                    "name='" + name + '\'' +
//                    '}';
//        }
//    }
}
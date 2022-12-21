package com.example.vgnmanage;

//import android.app.Dialog;
//import android.app.DialogFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
//import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MyFragment extends Fragment {
// fragmenta hame chi mishe hata recycle view va ghabeliat dismiss ham dare

    TextView textViewhello;
    final static String TextHY = "text";

    //    String text; in ravesham mishe  ya hata make conection
    public MyFragment() {

    }

    public static MyFragment newInstance(String mytext) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        args.putString(TextHY, "HEYYYYYY");
        //        fragment.text = text;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            textViewhello.setText(getArguments().getString(TextHY)); inja ham mishe
            //        tv.setText(text);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        textViewhello = view.findViewById(R.id.textviewhello);
        textViewhello.setText(getArguments().getString(TextHY));
        //        tv.setText(text);
//        dismiss();
//        faghat bara dialog frag mishe
//        container.removeAllViews();
//        container.removeView(view);
        // balaiia nemishan vali in  mishe
//        FrameLayout frameLayout = findViewById(R.id.myfrag);
//        frameLayout.setVisibility(View.GONE);// ba in mishe ham view v ham fragmento az beyn bord

        return view;

    }

}
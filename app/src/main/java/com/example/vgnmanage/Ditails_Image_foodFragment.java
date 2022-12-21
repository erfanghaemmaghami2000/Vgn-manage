package com.example.vgnmanage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Ditails_Image_foodFragment extends Fragment {
    // fragment ya yek activity sayar
    private static final String ARG_PARAM1 = "imageurlorpath";

    TextView textViewdet;
    public String imageurl = "";// publicesh kardim ke mostaghim bedimesh ba searchesh malom mishe ke koja dadim to main
    ImageView imagefood;
    ImageView imgcancel;
    FrameLayout frameLayout;// be raveshe abstract ham mishod ke ba chiz dige anjam dadam
    public String text = "";
    Context context;
    Food food;

    public Ditails_Image_foodFragment(FrameLayout frameLayout, Context context, Food food) {
        this.frameLayout = frameLayout;
        this.context = context;
        this.food = food;
    }

//    public static Ditails_Image_foodFragment newInstance(String param1) {
//        Ditails_Image_foodFragment fragment = new Ditails_Image_foodFragment(null);////*
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageurl = getArguments().getString(ARG_PARAM1);
            Log.e("chossdfhsdhfgbskjcvs", "rid" + imageurl);
            // ba farakhani newinstance parametr ro mide
            // vali chon goftim age null nabashe ejra nemishe ya age beshe hichi nemide badesh khodemon be image url meghdar midim **
        } else {
            Log.e("chossdfhsdhfgbskjcvs", "narid" + imageurl);
        }
    }
//hichi

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ditails_image_food, container, false);
        textViewdet = view.findViewById(R.id.textViewditals);
        textViewdet.setText(text);
//        textViewdet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//                alertBuilder.setTitle("more ditails");
//                alertBuilder.setMessage(text);
//                AlertDialog alert = alertBuilder.create();
//                alert.show();
//            }
//        });
        imagefood = view.findViewById(R.id.imageViewfragment);
        if (food.fromsever_internet) {
            Log.e("path", imageurl);
            Log.e("path", "");
            Log.e("path", "hhh");
//            Log.e("path", null);

            Picasso.get().
                    load(food.imgurl)
                    .into(imagefood);
        } else {
            if (imageurl == "") {
                imagefood.setImageResource(R.drawable.veganfood);

            } else {
                imagefood.setImageURI(Uri.parse(imageurl));
            }
        }

        imgcancel = view.findViewById(R.id.imagecancel);
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.GONE);  //khod be khod kole fragment baste mishe kamel va ba baz kardan dobare fragment etelaati ke be fragment ghabli dadim nemiad mage inke yeja mesl make conection savesh konim
            }
        });
        imagefood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myanimzoom(imagefood);
                Log.e("aaaaa", "aa");
            }
        });
        return view;
    }
//gereftan viewholder va gereftan view ha va anjam amaliat roshon va baghie karha

    private void myanimzoom(ImageView imageView) {
//        Animation anim = AnimationUtils.loadAnimation(context, R.anim.myanim);
//        imageView.startAnimation(anim);
        imageView.animate().alpha(0.8f).setDuration(1000)
//            .rotationY(360); daraje charkhesh
                .scaleX(2).scaleY(2)// meghdar bozorgi
                .translationYBy(100);// jabejaii
    }


}
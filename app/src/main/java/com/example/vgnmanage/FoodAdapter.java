package com.example.vgnmanage;

//import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public abstract class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    public List<Food> foods;
    Context context;

    //    Activity activity;
//    @Override
//    public final boolean hasStableIds() {
//        return true;
//    }

    public FoodAdapter(List<Food> foods, Context context) {
        this.context = context;
        this.foods = foods;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_item, parent, false);
        return new ViewHolder(itemView);
    }
// tabdil va inflate kardan yek layout be viewholder

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MakeConection.defualtstate1.curent_recycle_itemposition = position;
        Food food = foods.get(position);
        holder.tv_name.setText(food.name);
        holder.tv_des.setText(food.description);
        Log.e("curentitem", String.valueOf(position));

        String url = food.imgurl;
//        if (!url.isEmpty() && url != null && url != "") {
        food.foodslog();
        File file = new File(url);
        if (file.exists()) {
            holder.img_food.setImageURI(Uri.parse(food.imgurl));//load kardan az file
//            holder.img_food.setImageResource();
//            holder.img_food.setImageDrawable();
//            holder.img_food.setImageBitmap();
            //            holder.img_food.setImageURI(Uri.fromFile(file));
        } else if (food.fromsever_internet) {
            Picasso.get().load(url).into(holder.img_food);// load kardan az net
        } else {
            holder.img_food.setImageResource(R.drawable.veganfood);//load kardan az icon pishfars ke to drawble zakhire shode
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mclick(curentfood);
                mclick(food);
//                MakeConection.defualtstate1.curent_image_filename_selected = holder.img_food;// mishod be interfacemonam dad vali in behtare ya inke momke ontori faghat to ghestam layout khodesh neshon bede
//                Toast.makeText(context, "hoooosh", Toast.LENGTH_SHORT).show();
            }
        });
    }
//az viewholder be view haye layoutemon miresim va onaro tanzim mikonim va baghie kar ha mamolan inja behtare beshe

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_des;
        ImageView img_food;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.foodname);
            tv_des = itemView.findViewById(R.id.fooddes);
            img_food = itemView.findViewById(R.id.imagefood);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, " u had                   cliiik", Toast.LENGTH_SHORT).show();
//                    // inam mishe bala ham mishe age bala nabod in mishod
//                }
//            });
        }
    }
//tanzim view holder va view haye layoutemon

//    public abstract void mclick(Food food, ImageView imageView);

    public abstract void mclick(Food food);
    // rahaye dige ham hast masalan metodi ke to celassi ke gharare baray mlick tarif beshe ro inja tarif konim va baraye return ya parametr haye morede niaz ro az class dige be inja biarim va baad metdodo tarif konim
//ya mishe metod ro to on celass tarif kard bad ba nemone giri az kelass inja farakhanish kard
}


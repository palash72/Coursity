package com.example.course.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.course.Interface.MyCallBackFromCategoryToCourseList;
import com.example.course.Model.CategoryItemModel;
import com.example.course.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    ArrayList<CategoryItemModel> categoryItemModels;
    MyCallBackFromCategoryToCourseList callback;
    public CategoryAdapter(MyCallBackFromCategoryToCourseList callback, ArrayList<CategoryItemModel> models){
        this.categoryItemModels = models;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.category_item_demo, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        holder.tv_title.setText(categoryItemModels.get(position).getsTitle());
        Glide.with(holder.itemView.getContext()).load(categoryItemModels.get(position).getsImgUrl()).into(holder.iv_img);
        int pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("data6", categoryItemModels.get(pos).getsTitle());
                if (callback != null){
                    callback.mShowFiltered(categoryItemModels.get(pos).getsTitle());
                }
//                Toast.makeText(holder.itemView.getContext(),  "ttt", Toast.LENGTH_SHORT).show();
            }
        });
//        if (position == getItemCount()-1  )
    }

    @Override
    public int getItemCount() {
        return categoryItemModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_cat_img_id);
            tv_title = itemView.findViewById(R.id.tv_cat_txt_id);

        }
    }
}

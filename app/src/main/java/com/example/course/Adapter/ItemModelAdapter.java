package com.example.course.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.course.Activity.CourseDetailse;
import com.example.course.Interface.MyCallBackVideoDataBundle;
import com.example.course.Interface.VideoItemClickedCallBack;
import com.example.course.Model.ItemModel;
import com.example.course.R;

import java.util.ArrayList;

public class ItemModelAdapter extends RecyclerView.Adapter<ItemModelAdapter.MyViewHolder>{

    Context context;
    VideoItemClickedCallBack callBack;
    ArrayList<ItemModel> models;
    int selectedPosition = -1;
    MyCallBackVideoDataBundle videoDataBundle;

    public ItemModelAdapter(Context context, ArrayList<ItemModel> models, CourseDetailse courseDetailse, MyCallBackVideoDataBundle dataBundle) {
        this.context = context;
        this.models = models;
        this.callBack = courseDetailse;
    }

    @NonNull
    @Override
    public ItemModelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.course_list_item_demo, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }
    
    @Override
    public void onBindViewHolder(@NonNull ItemModelAdapter.MyViewHolder holder, int position) {
        holder.tv_pos.setText(String.valueOf(position+1)+" . ");
        holder.tv_title.setText(models.get(position).getS_title());
        Glide.with(holder.itemView).load(models.get(position).getS_video_img_url()).into(holder.iv_img);
        int pos = position;
        holder.itemView.setBackgroundColor(selectedPosition == pos ? Color.parseColor("#D4F3F3") : Color.WHITE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String video_url = models.get(pos).getS_url();
                callBack.myOnClicked(video_url);
                selectedPosition = pos;
            }
        });


    }
    void itemBgColorSelect(int pos, ItemModelAdapter.MyViewHolder hol){
//        hol.cl_item.setBackgroundColor(hol.itemView.getContext().getColor(R.color.selected));

    }
    @Override
    public int getItemCount() {
        return models.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        LinearLayout cl_item;
        TextView tv_pos, tv_title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.crs_dtls_lst_img_id);
            tv_pos = itemView.findViewById(R.id.crs_dtls_lst_pos_id);
            tv_title = itemView.findViewById(R.id.crs_dtls_lst_title_id);
            cl_item = itemView.findViewById(R.id.crs_dtls_lst_item_id);

        }
    }
}
















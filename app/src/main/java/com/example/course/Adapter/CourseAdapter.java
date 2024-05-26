package com.example.course.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.course.Interface.MyListener;
import com.example.course.Interface.VideoItemClickedCallBack;
import com.example.course.R;
import com.example.course.Model.ViewHolderItemModel;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    ArrayList<ViewHolderItemModel> models;
    MyListener myListener;

    public CourseAdapter(ArrayList<ViewHolderItemModel> data, MyListener listener){
        this.models = data;
        this.myListener = listener;

    }

    @NonNull
    @Override
    public CourseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_course, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(models.get(position).getStr_title());
        holder.tvOwner.setText(models.get(position).getStr_owner());
        holder.tvPrice.setText(models.get(position).getStr_price());
        holder.tvRating.setText(models.get(position).getStr_rating());

        int course_bg = holder.ivCourse.getResources().getIdentifier(models.get(position).getIv_course_bg(), "drawable", holder.ivCourse.getContext().getPackageName());
        int owner_pic = holder.ivCourse.getResources().getIdentifier(models.get(position).getIv_owner(), "drawable", holder.ivCourse.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(course_bg).into(holder.ivCourse);
        Glide.with(holder.itemView.getContext()).load(owner_pic).into(holder.ivOwner);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myListener != null){
                    myListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvOwner, tvPrice, tvRating;
        ImageView ivCourse, ivOwner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.titleTxt);
            tvOwner = itemView.findViewById(R.id.ownerTxt);
            tvPrice = itemView.findViewById(R.id.priceTxt);
            tvRating = itemView.findViewById(R.id.starTxt);
            ivCourse = itemView.findViewById(R.id.pic);
            ivOwner = itemView.findViewById(R.id.ownerPic);


        }
    }
}

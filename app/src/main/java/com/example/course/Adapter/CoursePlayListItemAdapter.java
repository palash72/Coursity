package com.example.course.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.course.Activity.CourseDetailse;
import com.example.course.Model.CoursePlayListItemModel;
import com.example.course.R;

import java.util.ArrayList;

public class CoursePlayListItemAdapter extends RecyclerView.Adapter<CoursePlayListItemAdapter.MyViewHolder>{
    ArrayList<CoursePlayListItemModel> model;
    Context context;

    public CoursePlayListItemAdapter(Context context, ArrayList<CoursePlayListItemModel> model){
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public CoursePlayListItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_playlist_item_demo, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoursePlayListItemAdapter.MyViewHolder holder, int position) {
        holder.tv_playlist_type.setText(model.get(position).getStr_type());
        holder.tv_playlist_title.setText(model.get(position).getStr_title());
        Glide.with(holder.itemView).load(model.get(position).getStr_img_url()).into(holder.iv_playlist_img);
        int pos = position;
        holder.cv_playlist_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,  (pos+1)+" no item is clicked", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context,  " "+model.get(pos).getStr_img_url(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, CourseDetailse.class);
//                b.putString("url", model.get(pos).getCourse_path()[0]);
                //TODO: ekhan theke playlist er video array list pathate hobe
                intent.putExtra("course_path", model.get(pos).getCourse_path());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_playlist_img;
        TextView tv_playlist_type, tv_playlist_title;
        CardView cv_playlist_item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_playlist_img = itemView.findViewById(R.id.iv_course_playlist_item_img_id);
            tv_playlist_type = itemView.findViewById(R.id.tv_course_playlist_item_type_id);
            tv_playlist_title = itemView.findViewById(R.id.tv_course_playlist_item_title_id);
            cv_playlist_item = itemView.findViewById(R.id.cv_course_playlist_item_cardView_id);

        }
    }
}

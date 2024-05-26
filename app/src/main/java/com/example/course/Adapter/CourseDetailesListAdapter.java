package com.example.course.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.course.R;
import com.example.course.Model.CourseListItemModel;

import java.util.ArrayList;

public class CourseDetailesListAdapter extends RecyclerView.Adapter<CourseDetailesListAdapter.MyViewHolder> {
    ArrayList<CourseListItemModel> listItems;

    public CourseDetailesListAdapter(ArrayList<CourseListItemModel> data){
        this.listItems = data;
    }


    @NonNull
    @Override
    public CourseDetailesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item_demo, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseDetailesListAdapter.MyViewHolder holder, int position) {
        holder.tv_pos.setText(listItems.get(position).getPos());
        holder.tv_title.setText(listItems.get(position).getTitle());
        String thumbId = getThumbIdFromURL(listItems.get(position).getUrl());
        String thumbUrl = "https://img.youtube.com/vi/"+thumbId+"/hqdefault.jpg";
        Glide.with(holder.itemView.getContext()).load(thumbUrl).into(holder.iv_thumb);

//        Glide.with(context)
//                .load("https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg") // Use maxresdefault for highest quality
//                .apply(new RequestOptions().override(width, height)) // Optional: resize the image
//                .into(yourImageView);


    }

    public String getThumbIdFromURL(String iframe){
        int startIndex = iframe.indexOf("/embed/") + 7; // Add 7 to skip "/embed/"
        int endIndex = iframe.indexOf("?si=");// Find the ending index before "?si="

        String extractedId;
        if (startIndex >= 0 && endIndex > startIndex) {
            extractedId = iframe.substring(startIndex, endIndex);
        } else {
            extractedId = "";
        }
        return extractedId;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_thumb;
        TextView tv_pos, tv_title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_thumb = itemView.findViewById(R.id.crs_dtls_lst_img_id);
            tv_pos = itemView.findViewById(R.id.crs_dtls_lst_pos_id);
            tv_title = itemView.findViewById(R.id.crs_dtls_lst_title_id);

        }
    }
}

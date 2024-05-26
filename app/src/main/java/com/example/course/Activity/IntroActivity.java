package com.example.course.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.course.Adapter.CategoryAdapter;
import com.example.course.Adapter.CoursePlayListItemAdapter;
import com.example.course.Control.MyFirebase;
import com.example.course.Interface.MyCallBackCategory;
import com.example.course.Interface.MyCallBackFromCategoryToCourseList;
import com.example.course.Interface.MyCallBackPlaylist;
import com.example.course.Interface.MyListener;
import com.example.course.Model.CategoryItemModel;
import com.example.course.Model.CoursePlayListItemModel;
import com.example.course.R;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity implements MyListener, MyCallBackCategory, MyCallBackPlaylist, MyCallBackFromCategoryToCourseList {

    RecyclerView rv_courseList, rv_category;
    ConstraintLayout cl_banner;
    ArrayList<CategoryItemModel> categoryItemModels = new ArrayList<>();
    private ViewPager2 bannerSlider;
    private Handler handler;
    private Runnable slideRunnable;
    private int currentPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);
        rv_courseList = (RecyclerView) findViewById(R.id.rv_course_list_id);
        rv_category = (RecyclerView)findViewById(R.id.rv_cat_id);
    }



    @Override
    protected void onStart() {
        MyFirebase myFirebase = new MyFirebase(this);
        myFirebase.mFirebaseCategoryGet(this);

        super.onStart();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(IntroActivity.this, CourseDetailse.class);
        startActivity(intent);
    }

    @Override
    public void onCategoryDataRetrieved(ArrayList<CategoryItemModel> data) {
        this.categoryItemModels = data;
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryItemModels);
        rv_category.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_category.setAdapter(categoryAdapter);

        MyFirebase myFirebase = new MyFirebase(this);
        myFirebase.mFirebasePlayListDataGet(this, this.categoryItemModels);
    }

    @Override
    public void onPlaylistDataRetrieved(ArrayList<CoursePlayListItemModel> data) {

        CoursePlayListItemAdapter adapter = new CoursePlayListItemAdapter(this, data);//TODO: send the all data in a clicked playlist
        rv_courseList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_courseList.setAdapter(adapter);

    }

    @Override
    public void mShowFiltered(String filter_type) {

        MyFirebase myFirebase = new MyFirebase(this);
        myFirebase.mFirebasePlayListDataGet(this, this.categoryItemModels, filter_type);

    }
}











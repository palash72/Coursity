package com.example.course.Interface;

import com.example.course.Model.CategoryItemModel;
import com.example.course.Model.MyFirebaseItemModel;

import java.util.ArrayList;

public interface MyCallBackFirebaseData {
    void onMyFirebaseDataRetrieved(ArrayList<MyFirebaseItemModel> data);
}

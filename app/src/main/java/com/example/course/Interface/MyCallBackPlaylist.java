package com.example.course.Interface;


import com.example.course.Model.CoursePlayListItemModel;

import java.util.ArrayList;
import java.util.List;

public interface MyCallBackPlaylist {

    void onPlaylistDataRetrieved(ArrayList<CoursePlayListItemModel> data );
}

package com.example.course.Control;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.course.Activity.CourseDetailse;
import com.example.course.Interface.MyCallBackCategory;
import com.example.course.Interface.MyCallBackPlayListItemLoaded;
import com.example.course.Interface.MyCallBackPlaylist;
import com.example.course.Interface.MyGetTtitleBackByUrl;
import com.example.course.Model.CategoryItemModel;
import com.example.course.Model.CoursePlayListItemModel;
import com.example.course.Model.ItemModel;
import com.example.course.Model.MyFirebaseItemModel;
import com.example.course.View.VideoMeta;
import com.example.course.View.YouTubeExtractor;
import com.example.course.View.YtFile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyFirebase{
    Context context;
    ArrayList<String> allCategoryTitle;
    ArrayList<String> allCategoryImgUrl;
    ArrayList<ItemModel> allItemModel;
    ArrayList<String> CourseName ;
    HashMap<Integer, String[]> mSinglePlayList = new HashMap<>();
    ArrayList<MyFirebaseItemModel> myFirebaseItemModels = new ArrayList<>();
    String url;
    String course_yt_url = "1111";
    DatabaseReference databaseReference, categoryRef, playlistRef, singleCourseRef;
    public MyFirebase(Context context){
        this.context = context;
        allCategoryTitle = new ArrayList<String>();
        allCategoryImgUrl = new ArrayList<String>();
        CourseName = new ArrayList<>();
        allItemModel = new ArrayList<>();

    }
    public String mGetPlaylistThumbImgUrl(String yt_url){
        int startIndex = yt_url.indexOf("watch?v=") + 8; // Add 7 to skip "/embed/"
        int endIndex = yt_url.indexOf("&list=");//TODO: only '&' or '&list=' Find the ending index before "?si="

        String extractedId;
        if (startIndex >= 0 && endIndex > startIndex) {
            extractedId = yt_url.substring(startIndex, endIndex);
        } else {
            extractedId = "";
        }

        String thumbUrl = "https://img.youtube.com/vi/"+extractedId+"/hqdefault.jpg";

        return thumbUrl;
    }
    public DatabaseReference myCustomDatabaseRefForCategory(String filtered_type){
        categoryRef = FirebaseDatabase.getInstance().getReference("Course").child(filtered_type);
        return categoryRef;
    };
    public DatabaseReference myCustomDatabaseRefForCategory(){
        categoryRef = FirebaseDatabase.getInstance().getReference("Course").child("type");
        return categoryRef;
    };
    public void mFirebasePlayListDataGet(MyCallBackPlaylist callBackPlaylist, ArrayList<CategoryItemModel> tempCategory, String filter_type){
        ArrayList<CoursePlayListItemModel> tempPlaylistAll = new ArrayList<>();
        ArrayList<CoursePlayListItemModel> tempPlaylist = new ArrayList<>();

        categoryRef = FirebaseDatabase.getInstance().getReference("Course").child("type").child(filter_type);

        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot11) {
                for (DataSnapshot d: snapshot11.getChildren()){
                        if (mCourseOrNot(d)){
                            String[] cp = { filter_type,d.getKey()};
                            String yt_url = mGetPlayListImage(d);
                            String img_url = mGetPlaylistThumbImgUrl(yt_url);
                            CoursePlayListItemModel model = new CoursePlayListItemModel(img_url,filter_type,mGetPlaylistTitle(d), cp);

                            tempPlaylist.add(model);
                        }
                }
                    tempPlaylistAll.addAll(tempPlaylist);
                    if (callBackPlaylist != null){
                        callBackPlaylist.onPlaylistDataRetrieved(tempPlaylistAll);//TODO: send the all data in a clicked playlist
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void mFirebasePlayListDataGet(MyCallBackPlaylist callBackPlaylist, ArrayList<CategoryItemModel> tempCategory){
        String current_img_url = null;
        ArrayList<CoursePlayListItemModel> tempPlaylistAll = new ArrayList<>();

        for (CategoryItemModel item: tempCategory){
            ArrayList<CoursePlayListItemModel> tempPlaylist = new ArrayList<>();

            categoryRef = FirebaseDatabase.getInstance().getReference("Course").child("type");

            playlistRef = categoryRef.getRef().child(item.getsTitle());//Not-TODO: what we done here, after 'type' (in firebase) serially all course name in that 'type' all appears, like title of course 1, course 2, course 3 serially
//            Log.d("data4", String.valueOf(playlistRef));
            playlistRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot snap: snapshot.getChildren()){
                            if (mCourseOrNot(snap)){

//                                String coursePath = String.valueOf(categoryRef.child(item.getsTitle()).child(snap.getKey()));
                                String coursePath = String.valueOf(categoryRef.child(item.getsTitle()).child(snap.getKey()));
                                String[] cp = {item.getsTitle(), snap.getKey()};

//                                Log.d("data4", String.valueOf(cp[0]));
//                                Log.d("data4", String.valueOf(coursePath));

//                                coursePath = coursePath.replace(" ", "%20");//TODO: this line add '%20' in space, because in url (in firebase) space not worked
//                                Log.d("data4", String.valueOf(categoryRef));
                                String yt_url = mGetPlayListImage(snap);
                                String img_url = mGetPlaylistThumbImgUrl(yt_url);
                                CoursePlayListItemModel model = new CoursePlayListItemModel(img_url,item.getsTitle(),mGetPlaylistTitle(snap), cp);

                                tempPlaylist.add(model);
                            }


//                            mGetPlaylistTitle(snap);
//                            Log.d("data3", item.getsTitle()+" : "+snap.getKey());
                        }
                        tempPlaylistAll.addAll(tempPlaylist);
                        if (callBackPlaylist != null){
                            callBackPlaylist.onPlaylistDataRetrieved(tempPlaylistAll);//TODO: send the all data in a clicked playlist
                        }
                    }
                }




                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }
    private String mGetPlayListImage(DataSnapshot snap) {

        for (DataSnapshot nnn: snap.getChildren()){
            course_yt_url = String.valueOf(nnn.child("url").getValue());
            return course_yt_url;
        }
        return null;


    }

    private boolean mCourseOrNot(DataSnapshot snap) {
        boolean b = false;
        String course_name = snap.getKey();
        if (!course_name.equals("category_img")){
            b = true;
        }
        return b;
    }

    private String mGetPlaylistTitle(DataSnapshot snap) {
        String course_name = String.valueOf(snap.child("1").child("title").getValue());
        return course_name;
    }

    public void mFirebaseCategoryGet(MyCallBackCategory callBack){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Loading data...");
        builder.setCancelable(false); // Prevent user from dismissing the dialog
        AlertDialog dialog = builder.create();
//        dialog.show(); //TODO: Handle if no internet its alwayse loading, if it then load dummy or make it empty with message no internet


        ArrayList<CategoryItemModel> tempCategory = new ArrayList<CategoryItemModel>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Course");
        categoryRef = databaseReference.getRef().child("type");
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    allCategoryImgUrl.clear();
                    allCategoryTitle.clear();
                    tempCategory.clear();


                    for (DataSnapshot snap : snapshot.getChildren()){
//                        CategoryItemModel item = snap.getValue(CategoryItemModel.class);
                        String category_title = snap.getKey();
//                        category_title = category_title.substring(0, 1).toUpperCase() + category_title.substring(1);
                        CategoryItemModel item = new CategoryItemModel(category_title, (String) snap.child("category_img").getValue());
                        tempCategory.add(item);
                        /*TODO: the key in allCategoryTitle
                           it will help to get all 'types' of course, like android c java c++ etc, this title will help to bring that type's all course under it
                        */

//                        categoryItemsData.add(item);
//                        Log.d("data2", String.valueOf(temp.size()));


//                        allCategoryTitle.add(snap.getKey());
//                        allCategoryImgUrl.add( (String) snap.child("category_img").getValue());

                    }
//                    mFirebasePlayListDataGet(tempCategory);
                    if (callBack != null){
                        callBack.onCategoryDataRetrieved(tempCategory);
                    }
                }
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void mGetFirebaseSingleCourseData(MyCallBackPlayListItemLoaded callBack ,String[] crsPath) {
        singleCourseRef = FirebaseDatabase.getInstance().getReference("Course").child("type");
        for (int i = 0; i < crsPath.length; i++) {
            singleCourseRef = singleCourseRef.child(crsPath[i]);
        }
        int l = crsPath.length;

        singleCourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot st) {
                if (st.exists()){
                    for (DataSnapshot dtst: st.getChildren()){

                        String video_img_url = mGetPlaylistThumbImgUrl(dtst.child("url").getValue(String.class));
                        ItemModel m = new ItemModel(dtst.child("title").getValue(String.class),dtst.child("url").getValue(String.class), video_img_url);
                        allItemModel.add(m);
                    }
                    if (callBack != null){
                        callBack.onCategoryDataRetrieved(allItemModel);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


}

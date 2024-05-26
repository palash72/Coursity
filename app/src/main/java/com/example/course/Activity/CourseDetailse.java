package com.example.course.Activity;

import static androidx.appcompat.widget.ListPopupWindow.MATCH_PARENT;
import static androidx.appcompat.widget.ListPopupWindow.WRAP_CONTENT;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.course.Adapter.ItemModelAdapter;
import com.example.course.Control.MyFirebase;
import com.example.course.Interface.MyCallBackPlayListItemLoaded;
import com.example.course.Interface.MyCallBackVideoDataBundle;
import com.example.course.Interface.MyGetTtitleBackByUrl;
import com.example.course.Interface.VideoItemClickedCallBack;
import com.example.course.Model.ItemModel;
import com.example.course.R;
import com.example.course.View.VideoMeta;
import com.example.course.View.YouTubeExtractor;
import com.example.course.View.YtFile;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CourseDetailse extends AppCompatActivity implements MyCallBackPlayListItemLoaded, VideoItemClickedCallBack, MyCallBackVideoDataBundle {

    private static final int ITAG_FOR_AUDIO = 140;

    PlayerView playerView;
    RecyclerView rv_dtls_list;
    WebView wv_videoPlayer;
//    String COURSE_PATH = "";
    String[] COURSE_PATH;
    DatabaseReference databaseReference;
    ImageView fullscreenButton;
    MyFirebase myFirebase;
    //ConstraintLayout fl;
    String videoUrl;
    MediaItem mediaItem;
    ExoPlayer exoPlayer;
    TextView tv_running_title;
    String CURRENT_VIDEO_URL;
    Bundle video_item_bundle;
    Bundle outState = new Bundle();
    ProgressBar progressBar;
    private List<YtFragmentedVideo> formatsToShowList;
    private long currentPlayerPosition = 0;
    @UnstableApi @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detailse);

        Intent intent = getIntent();
        COURSE_PATH = intent.getStringArrayExtra("course_path");

        tv_running_title = (TextView) findViewById(R.id.crs_dtls_running_video_txt_id);
        progressBar = (ProgressBar) findViewById(R.id.prog_id);

        rv_dtls_list = (RecyclerView) findViewById(R.id.rv_course_list_item_id);
        //fl = (ConstraintLayout) findViewById(R.id.fl_media_id);
        playerView = (PlayerView) findViewById(R.id.player_view_id);

        exoPlayer = new ExoPlayer.Builder(CourseDetailse.this).build();
        playerView.setPlayer(exoPlayer);


        video_item_bundle = new Bundle();
        String rec_data = video_item_bundle.getString("url");
        if (rec_data != null){
            Toast.makeText(this, "Ureca...", Toast.LENGTH_SHORT).show();
        }


        progressBar.setVisibility(View.VISIBLE);
        myFirebase = new MyFirebase(CourseDetailse.this);
        myFirebase.mGetFirebaseSingleCourseData(this,COURSE_PATH);


        fullscreenButton = findViewById(R.id.fullscreen_id);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                currentPlayerPosition = exoPlayer.getCurrentPosition();
                if (isPortraitMode()) {
                    outState.putString("url", CURRENT_VIDEO_URL);
//                    storeValue("rec", CURRENT_VIDEO_URL);

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    hideSystemUI();
                    setPlayerViewLayoutParams(MATCH_PARENT, MATCH_PARENT);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

                    if (currentPlayerPosition > 0) {
                        exoPlayer.seekTo(currentPlayerPosition);
                        exoPlayer.setPlayWhenReady(true);
                    }

                } else {
                    if (CURRENT_VIDEO_URL != null){
                        savedInstanceState.putString("url", CURRENT_VIDEO_URL);
//                        storeValue("rec", CURRENT_VIDEO_URL);
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    showSystemUI();
                    setPlayerViewLayoutParams(MATCH_PARENT, WRAP_CONTENT);
//                    fl.getLayoutParams().height = WRAP_CONTENT;

                    if (currentPlayerPosition > 0) {
                        exoPlayer.seekTo(currentPlayerPosition);
                        exoPlayer.setPlayWhenReady(true);
                    }

                }
            }
        });


        if (savedInstanceState != null) {
            int playerState = savedInstanceState.getInt("playerState", Player.STATE_IDLE);
            currentPlayerPosition = savedInstanceState.getLong("currentPlayerPosition", 0);
            if (playerState == Player.STATE_READY || playerState == Player.STATE_ENDED) {
                exoPlayer.seekTo(playerState);
                if (currentPlayerPosition > 0) {
                    exoPlayer.seekTo(currentPlayerPosition);
                }
            }
            CURRENT_VIDEO_URL = savedInstanceState.getString("url");
            if (CURRENT_VIDEO_URL != null){
                getYoutubeDownloadUrl(CURRENT_VIDEO_URL);

            }
        }


        if (isPortraitMode()){
            setPlayerViewLayoutParams(MATCH_PARENT, 500);

        }

        playerView.setControllerVisibilityListener(new PlayerView.ControllerVisibilityListener() {
            @Override
            public void onVisibilityChanged(int visibility) {
                //TODO: i have checked, 'visibility' has 2 value. 0: controller shows, 8:controller hide
                if (visibility == 0){
                    showFullScreenIcon();
                } else{
                    hidFullScreenIcon();
                }
            }
        });


    }
    @Override
    public void mOnGetVideoData(Bundle video_data_bundle) {
        String u = video_data_bundle.getString("url");
        Log.d("data7", String.valueOf(u));
        if (u!=null){
            CURRENT_VIDEO_URL = u;
            onSaveInstanceState(video_data_bundle);
        }

    }

    @Override
    public void onCategoryDataRetrieved(ArrayList<ItemModel> data) {
        ItemModelAdapter adapter = new ItemModelAdapter(this,data, this, this);
        rv_dtls_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_dtls_list.setAdapter(adapter);

        Bundle b = new Bundle();

        String s = b.getString("url");
        if (s != null){
            getYoutubeDownloadUrl(s);
            exoPlayer.seekTo(currentPlayerPosition);
//            Log.d("data7", String.valueOf(currentPlayerPosition));
        }else {
            getYoutubeDownloadUrl(data.get(0).getS_url());
        }

    }

    @Override
    protected void onPause() {
        Bundle bac_bundle = new Bundle();
        bac_bundle.putString("url", CURRENT_VIDEO_URL);

        super.onPause();
    }

    private void getYoutubeTitleByUrl(String youtubeLink, MyGetTtitleBackByUrl callBackTitle) {

        if (youtubeLink != null){

            new YouTubeExtractor(this) {

                @Override
                public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                    progressBar.setVisibility(View.GONE);

                }
            }.extract(youtubeLink);



        }else {
//            Toast.makeText(this, "Youtube Link Missed" , Toast.LENGTH_SHORT).show();
        }
    }
    private void getYoutubeDownloadUrl(String youtubeLink) {
        if (youtubeLink != null){

            new YouTubeExtractor(this) {

                @Override
                public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                    progressBar.setVisibility(View.GONE);
                    if (ytFiles == null) {return;}
                    formatsToShowList = new ArrayList<>();
                    for (int i = 0, itag; i < ytFiles.size(); i++) {
                        itag = ytFiles.keyAt(i);
                        YtFile ytFile = ytFiles.get(itag);

                        if (ytFile.getFormat().getExt().equals("webm")) {
                            continue;
                        }
                        if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
                            addFormatToList(ytFile, ytFiles);
                        }
                    }
                    Collections.sort(formatsToShowList, new Comparator<YtFragmentedVideo>() {
                        @Override
                        public int compare(YtFragmentedVideo lhs, YtFragmentedVideo rhs) {
                            return lhs.height - rhs.height;
                        }
                    });
                    for (YtFragmentedVideo files : formatsToShowList) {
                        //TODO: Ekhan thekei 'files' er moddho theke video url call core uri banate hobe...
                        if (files.videoFile != null){
                            mMakeReadyByURL(files, vMeta.getTitle());
                        }else{
//                        Log.d("data5",String.valueOf("null hai"));
                        }
                    }
                }
            }.extract(youtubeLink);

        }else {
//            Toast.makeText(this, "Youtube Link Missed" , Toast.LENGTH_SHORT).show();
        }

    }


    private void mMakeReadyByURL(YtFragmentedVideo files, String title) {
        CURRENT_VIDEO_URL = files.videoFile.getUrl();//for save instance

        tv_running_title.setText(title);
        Uri uri = Uri.parse(CURRENT_VIDEO_URL);
        MediaItem item = MediaItem.fromUri(uri);
        playerView.setUseController(true);
        exoPlayer.setMediaItem(item);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.play();
        if (currentPlayerPosition > 0){
            exoPlayer.seekTo(currentPlayerPosition);
        }
//        storeValue("rec", CURRENT_VIDEO_URL);
    }


    @Override
    public void myOnClicked(String url) {
        getYoutubeDownloadUrl(url);
        video_item_bundle = new Bundle();
        this.video_item_bundle.putString("url", url);
        onSaveInstanceState(video_item_bundle);

//        storeValue("rec", url);
    }


    private void addFormatToList(YtFile ytFile, SparseArray<YtFile> ytFiles) {
        int height = ytFile.getFormat().getHeight();
        if (height != -1) {
            for (YtFragmentedVideo frVideo : formatsToShowList) {
                if (frVideo.height == height && (frVideo.videoFile == null || frVideo.videoFile.getFormat().getFps() == ytFile.getFormat().getFps())) {
                    return;
                }
            }
        }
        YtFragmentedVideo frVideo = new YtFragmentedVideo();
        frVideo.height = height;
        if (ytFile.getFormat().isDashContainer()) {
            if (height > 0) {
                frVideo.videoFile = ytFile;
                frVideo.audioFile = ytFiles.get(ITAG_FOR_AUDIO);
            } else {
                frVideo.audioFile = ytFile;
            }
        } else {
            frVideo.videoFile = ytFile;
        }
        formatsToShowList.add(frVideo);
    }




    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("data5","changed");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setPlayerViewLayoutParams(MATCH_PARENT, MATCH_PARENT);
            if (CURRENT_VIDEO_URL != null){
                getYoutubeDownloadUrl(CURRENT_VIDEO_URL);
                Log.d("data5", "eije");
            }
        } else {
            setPlayerViewLayoutParams(MATCH_PARENT, 500);
            if (CURRENT_VIDEO_URL != null){
                getYoutubeDownloadUrl(CURRENT_VIDEO_URL);
                Log.d("data5", "naije");
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (exoPlayer != null) {
            exoPlayer.getCurrentPosition();
            int playerState = exoPlayer.getPlaybackState();
            outState.putInt("playerState", playerState);
            if (playerState == Player.STATE_READY || playerState == Player.STATE_ENDED) {
                outState.putLong("currentPlayerPosition", exoPlayer.getCurrentPosition());
            }
        }
        long cp =  exoPlayer.getCurrentPosition();


        if (outState != null){
            outState.putString("url",CURRENT_VIDEO_URL);
            Log.d("data8","Ka hua");
        }else {
            outState = new Bundle();
            outState.putString("url",CURRENT_VIDEO_URL);
            Log.d("data8","2 Ka hua");
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        hidFullScreenIcon();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }
    void showFullScreenIcon(){
        fullscreenButton.setVisibility(View.VISIBLE);

    }
    void hidFullScreenIcon(){
        fullscreenButton.setVisibility(View.GONE);
    }
    private boolean isPortraitMode() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void hideSystemUI() {
        showFullScreenIcon();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showSystemUI() {
        hidFullScreenIcon();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    private void setPlayerViewLayoutParams(int width, int height) {
        playerView.getLayoutParams().width = width;
        playerView.getLayoutParams().height = height;
        playerView.requestLayout();
    }



    private static class YtFragmentedVideo {
        int height;
        YtFile audioFile;
        YtFile videoFile;
    }

}
























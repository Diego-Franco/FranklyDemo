package com.defch.fnkydemo.activities;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.defch.fnkydemo.ApplicationC;
import com.defch.fnkydemo.R;
import com.defch.fnkydemo.model.Song;
import com.defch.fnkydemo.util.LogUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by DiegoFranco on 4/29/15.
 */
public class BaseActivity extends ActionBarActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    public static final String TITLE = "title";
    public static final String ARTIST = "artist";
    public static final String ALBUM = "album";

    private boolean isLandscape;
    private boolean isTablet;

    public ApplicationC app;

    public ArrayList<Song> songList;

    @Override
    public void setContentView(int layoutRedID) {
        super.setContentView(layoutRedID);
        LogUtil.v(TAG, "setContentView");
        ButterKnife.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = ApplicationC.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        isTablet = getResources().getBoolean(R.bool.is_tablet);
        songList = app.getSongsList();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(int color) {
        if (app.sdkVersion >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    public boolean isLandscape() {
        return isLandscape;
    }

    public boolean isTablet() {
        return isTablet;
    }

    public void saveSongOnDB(Song song) {
        app.sqlHelper.insertPost(song);
    }

    public void removeSongOnDB(int id) {
        app.sqlHelper.deletePost(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(app.instance == null) {
            app.getInstance(getApplicationContext());
        }
    }

}

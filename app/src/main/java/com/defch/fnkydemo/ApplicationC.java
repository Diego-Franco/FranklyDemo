package com.defch.fnkydemo;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.defch.fnkydemo.model.Song;
import com.defch.fnkydemo.storage.SqlHelper;

import java.util.ArrayList;

/**
 * Created by DiegoFranco on 4/29/15.
 */
public class ApplicationC extends Application {

    private static final String TAG = ApplicationC.class.getSimpleName();

    private ArrayList<Song> songsList = new ArrayList<>();

    public static ApplicationC instance;

    public SqlHelper sqlHelper;

    public int sdkVersion = Build.VERSION.SDK_INT;

    public static ApplicationC getInstance() {
        return instance;
    }

    public static ApplicationC getInstance(Context context) {
        return context != null ? (ApplicationC) context.getApplicationContext() : instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sqlHelper = new SqlHelper(getApplicationContext());
        new LoadSongsTask().execute();
    }

    public ArrayList<Song> getSongsList() {
        return songsList;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    private class LoadSongsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            songsList = sqlHelper.getSongs();
            return null;
        }
    }

}

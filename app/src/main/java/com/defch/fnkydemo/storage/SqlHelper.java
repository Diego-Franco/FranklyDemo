package com.defch.fnkydemo.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.defch.fnkydemo.model.Song;
import com.defch.fnkydemo.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by DiegoFranco on 4/17/15.
 */
public class SqlHelper extends SQLiteOpenHelper {
    private static final String TAG = SqlHelper.class.getSimpleName();

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "demof.db";

    private static SQLiteDatabase mReadableDatabase;

    private static SQLiteDatabase mWritableDatabase;

    private long idPost;

    public SqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SqliteParams.SongsT.CREATE_TABLE_SQL);
        LogUtil.v(TAG, "post table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        onCreate(db);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        if (mReadableDatabase == null || !mReadableDatabase.isOpen()) {
            mReadableDatabase = super.getReadableDatabase();
        }

        return mReadableDatabase;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        if (mWritableDatabase == null || !mWritableDatabase.isOpen()) {
            mWritableDatabase = super.getWritableDatabase();
        }

        return mWritableDatabase;
    }

    /**
     * Inserts the user to the database
     *
     * @param song
     */
    public void insertPost(@NonNull Song song) {
        LogUtil.v(TAG, "Inserting song " + song.toString());
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SqliteParams.SongsT.COLUMN_TITLE, song.getTitle());
        values.put(SqliteParams.SongsT.COLUMN_ARTIST, song.getArtist());
        values.put(SqliteParams.SongsT.COLUMN_ALBUM, song.getAlbum());
        this.idPost = db.insertWithOnConflict(SqliteParams.SongsT.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    /**
     * Updates the user's information
     *
     * @param song
     */
    public void updatePost(@NonNull Song song) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqliteParams.SongsT._ID, song.getId());
        values.put(SqliteParams.SongsT.COLUMN_TITLE, song.getTitle());
        values.put(SqliteParams.SongsT.COLUMN_ARTIST, song.getArtist());
        values.put(SqliteParams.SongsT.COLUMN_ALBUM, song.getAlbum());
        db.update(SqliteParams.SongsT.TABLE_NAME, values, null, null);
        db.close();
    }
    

    /**
     * Returns a list of all the cached topics
     *
     * @return ArrayList<Song>
     */
    public ArrayList<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SqliteParams.SongsT.GET_POST_SQL, null);

        while (cursor.moveToNext()) {
            songs.add(new Song(cursor));
        }

        cursor.close();
        return songs;
    }

    /**
     * Deletes a topic from the databse given its id
     *
     * @param id
     */
    public void deletePost(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format(SqliteParams.SongsT.DELETE_POST_SQL, id));
        db.close();
    }

    public long getIdPost() {
        return idPost;
    }

    @Override
    public synchronized void close() {
        if (mReadableDatabase != null) {
            mReadableDatabase.close();
            mReadableDatabase = null;
        }

        if (mWritableDatabase != null) {
            mWritableDatabase.close();
            mWritableDatabase = null;
        }

        super.close();
    }
}

package com.defch.fnkydemo.storage;

import android.provider.BaseColumns;

public class SqliteParams {
    public static class SongsT implements BaseColumns {
        public static final String TABLE_NAME = "songs";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_ARTIST = "artist";

        public static final String COLUMN_ALBUM = "album";


        public static final String CREATE_TABLE_SQL =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + _ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_ARTIST + " TEXT NOT NULL, " +
                COLUMN_ALBUM + " TEXT NOT NULL);";

        public static int COLUMN_INDEX_ID = 0;

        public static int COLUMN_INDEX_TITLE = 1;

        public static int COLUMN_INDEX_ARTIST = 2;

        public static int COLUMN_INDEX_ALBUM = 3;


        public static final String GET_POST_SQL = "SELECT * FROM " + TABLE_NAME
                + " ORDER BY " + _ID + " COLLATE NOCASE ASC";
        
        public static final String DELETE_POST_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " +
                _ID + " =%d";
    }

}

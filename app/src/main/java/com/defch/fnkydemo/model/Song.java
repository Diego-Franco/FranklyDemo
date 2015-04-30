package com.defch.fnkydemo.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.defch.fnkydemo.storage.SqliteParams;

/**
 * Created by DiegoFranco on 4/29/15.
 */
public class Song implements Parcelable{

    int id;
    String title;
    String artist;
    String album;

    public Song() {}

    public Song(Cursor cursor) {
        setId(cursor.getInt(SqliteParams.SongsT.COLUMN_INDEX_ID));
        setTitle(cursor.getString(SqliteParams.SongsT.COLUMN_INDEX_TITLE));
        setArtist(cursor.getString(SqliteParams.SongsT.COLUMN_INDEX_ARTIST));
        setAlbum(cursor.getString(SqliteParams.SongsT.COLUMN_INDEX_ALBUM));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    protected Song(Parcel in) {
        id = in.readInt();
        title = in.readString();
        artist = in.readString();
        album = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(album);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}

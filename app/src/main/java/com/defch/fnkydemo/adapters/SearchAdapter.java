package com.defch.fnkydemo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.defch.fnkydemo.R;
import com.defch.fnkydemo.model.Song;

import java.util.ArrayList;

/**
 * Created by DiegoFranco on 4/29/15.
 */
public class SearchAdapter extends android.support.v4.widget.CursorAdapter {

    private ArrayList<Song> sObjects;
    private TextView textView;

    public SearchAdapter(Context context, Cursor c, ArrayList<Song> objects) {
        super(context, c, false);
        this.sObjects = objects;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_search, parent, false);
        textView = (TextView) view.findViewById(R.id.item_searchable);
        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Song song = sObjects.get(cursor.getPosition());
        textView.setText(song.getTitle());
    }
}

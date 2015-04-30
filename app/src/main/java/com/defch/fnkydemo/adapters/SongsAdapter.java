package com.defch.fnkydemo.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.defch.fnkydemo.R;
import com.defch.fnkydemo.activities.BaseActivity;
import com.defch.fnkydemo.model.Song;
import com.defch.fnkydemo.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by DiegoFranco on 4/29/15.
 */
public class SongsAdapter extends ArrayAdapter<Song> implements Filterable {

    private static final String TAG = SongsAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Song> songs;
    private ArrayList<Song> oringSongs;

    public SongsAdapter(Context context, ArrayList<Song> objects) {
        super(context, 0, objects);
        this.context = context;
        this.songs = objects;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        Song song = songs.get(position);
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
            holder = new ViewHolder();
            holder.layout = view.findViewById(R.id.lyt_adapter);
            holder.txtTitle = (TextView)view.findViewById(R.id.txt_title);
            holder.txtArtist = (TextView)view.findViewById(R.id.txt_artist);
            holder.txtAlbum = (TextView)view.findViewById(R.id.txt_album);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.txtTitle.setText(song.getTitle());
        holder.txtArtist.setText(song.getArtist());
        holder.txtAlbum.setText(song.getAlbum());

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.title_dialog_delete)
                        .setMessage(R.string.delete_warning)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                songs.remove(position);
                                notifyDataSetChanged();
                                ((BaseActivity)context).removeSongOnDB(which);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Song> results = new ArrayList<>();
                if (oringSongs == null)
                    oringSongs = songs;
                if (constraint != null) {
                    if (oringSongs != null && oringSongs.size() > 0) {
                        for (final Song s : oringSongs) {
                           LogUtil.v(TAG, constraint.toString());
                            if (constraint.toString().equalsIgnoreCase(s.getTitle())
                                    || constraint.toString().equalsIgnoreCase(s.getArtist())
                                    || constraint.toString().equalsIgnoreCase(s.getAlbum())) {
                                results.add(s);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.values != null) {
                    ArrayList<Song> tmpSongs = (ArrayList<Song>) results.values;

                    if(tmpSongs.size() > 0) {
                        songs = tmpSongs;
                    } else {
                        songs = oringSongs;
                    }
                }
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Song getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        View layout;
        TextView txtTitle;
        TextView txtArtist;
        TextView txtAlbum;
    }
}

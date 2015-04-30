package com.defch.fnkydemo.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.defch.fnkydemo.R;
import com.defch.fnkydemo.adapters.SongsAdapter;
import com.defch.fnkydemo.model.Song;

import butterknife.InjectView;


public class MainActivity extends BaseActivity {

    @InjectView(R.id.mtoolbar)
    Toolbar mToolbar;

    @InjectView(R.id.list_songs)
    ListView listSongs;

    @InjectView(R.id.input_title)
    EditText inputTitle;

    @InjectView(R.id.input_artist)
    EditText inputArtist;

    @InjectView(R.id.input_album)
    EditText inputAlbum;

    private SearchView search;

    private SongsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initToolbar();
        showSongList();
    }

    private void initToolbar() {
        setStatusBarColor(getResources().getColor(R.color.primary_dark));
        mToolbar.setBackgroundColor(getResources().getColor(R.color.primary));
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(songList.size() > 0) {
            search = (SearchView)  menu.findItem(R.id.action_search).getActionView();
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setSubmitButtonEnabled(true);
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


                @Override
                public boolean onQueryTextSubmit(String s) { return false; }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (TextUtils.isEmpty(s)) {
                        listSongs.clearTextFilter();
                    } else {
                        listSongs.setFilterText(s);
                    }
                    return false;
                }

            });

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                verifyAndSaveSong();
                break;
            case R.id.action_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void verifyAndSaveSong() {
        if(titleIsNotEmpty() && artistIsNotEmpty() && albumIsNotEmpty()) {
            Song song = new Song();
            song.setTitle(inputTitle.getText().toString());
            song.setArtist(inputArtist.getText().toString());
            song.setAlbum(inputAlbum.getText().toString());
            saveSongOnDB(song);
            songList.add(song);
            showSongList();
            inputTitle.setText("");
            inputArtist.setText("");
            inputAlbum.setText("");
        } else {
            Toast.makeText(this, R.string.save_warning, Toast.LENGTH_SHORT).show();
        }
    }

    private void showSongList() {
        if(songList.size() > 0) {
            if(adapter == null) {
                adapter = new SongsAdapter(this, songList);
                listSongs.setAdapter(adapter);
                listSongs.setTextFilterEnabled(true);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private boolean titleIsNotEmpty() {
        return !TextUtils.isEmpty(inputTitle.getText().toString());
    }

    private boolean artistIsNotEmpty() {
        return !TextUtils.isEmpty(inputArtist.getText().toString());
    }

    private boolean albumIsNotEmpty() {
        return !TextUtils.isEmpty(inputAlbum.getText().toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(titleIsNotEmpty()) {
            outState.putString(TITLE, inputTitle.getText().toString());
        }
        if(artistIsNotEmpty()) {
            outState.putString(ARTIST, inputArtist.getText().toString());
        }
        if(albumIsNotEmpty()) {
            outState.putString(ALBUM, inputAlbum.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(!TextUtils.isEmpty(savedInstanceState.getString(TITLE))) {
            inputTitle.setText(savedInstanceState.getString(TITLE));
        }
        if(!TextUtils.isEmpty(savedInstanceState.getString(ARTIST))) {
            inputArtist.setText(savedInstanceState.getString(ARTIST));
        }
        if(!TextUtils.isEmpty(savedInstanceState.getString(ALBUM))) {
            inputAlbum.setText(savedInstanceState.getString(ALBUM));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

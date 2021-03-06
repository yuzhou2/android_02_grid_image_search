package com.yuzhou.viewer.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.yuzhou.viewer.R;
import com.yuzhou.viewer.model.GoogleImage;
import com.yuzhou.viewer.service.GoogleApiParam;
import com.yuzhou.viewer.service.GoogleApiTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private static final int MAX_PAGE = 8;

    private EditText etSearch;
    private ImageAdapter adapter;
    private EventBus eventBus;
    private GoogleApiParam searchPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventBus = new EventBus();
        eventBus.register(this);

        etSearch = (EditText) findViewById(R.id.etSearch);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_laucher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        adapter = new ImageAdapter(this, new ArrayList<GoogleImage>());
        GridView gridView = (GridView) findViewById(R.id.glSearch);
        gridView.setAdapter(adapter);
        gridView.setOnScrollListener(new EndlessScrollListener(MAX_PAGE)
        {
            @Override
            public void onLoadMore(int page, int totalItemsCount)
            {
                queryImages(page);
            }
        });

        searchPrefs = new GoogleApiParam();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView miSearch = (SearchView) MenuItemCompat.getActionView(searchItem);
        miSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                String query = etSearch.getText().toString();
                miSearch.setQuery(query, false);
            }
        });
        miSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                etSearch.setText(query);
                onClickSearchButton(miSearch);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                // click back also evoke
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy()
    {
        eventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onAdvancedSearch(item);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            searchPrefs = (GoogleApiParam) data.getParcelableExtra("new_prefs");
            adapter.clear();

            if (!isNetworkAvailable()) {
                Toast.makeText(this, R.string.error_unavailable_network, Toast.LENGTH_LONG).show();
                return;
            }

            new GoogleApiTask(eventBus, this).execute(searchPrefs);
        }
    }

    @Subscribe
    public void onFetchImageEvent(List<GoogleImage> items)
    {
        adapter.addAll(items);
        adapter.notifyDataSetChanged();
    }

    public void onClickSearchButton(View view)
    {
        String query = etSearch.getText().toString();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.bnSearch).getWindowToken(), 0);

        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.error_unavailable_network, Toast.LENGTH_LONG).show();
            return;
        }

        adapter.clear();
        if (!query.isEmpty()) {
            searchPrefs.clear();
            searchPrefs.setQuery(query);

            new GoogleApiTask(eventBus, this).execute(searchPrefs);
        }
    }

    public void onAdvancedSearch(MenuItem item)
    {
        EditText editText = (EditText) findViewById(R.id.etSearch);
        String query = editText.getText().toString();
        searchPrefs.setQuery(query);

        Intent intent = new Intent(this, AdvancedSearchActivity.class);
        intent.putExtra("prefs", searchPrefs);
        startActivityForResult(intent, 0);
    }

    private void queryImages(int page)
    {
        int start = (page - 1) * searchPrefs.getQuantity();
        searchPrefs.setStart(start);
        new GoogleApiTask(eventBus, this).execute(searchPrefs);
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
package com.example.carlybaja.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.carlybaja.nytimessearch.dialogs.ImageFiltersDialogs;
import com.example.carlybaja.nytimessearch.models.Article;
import com.example.carlybaja.nytimessearch.adapters.ArticleArrayAdapter;
import com.example.carlybaja.nytimessearch.R;
import com.example.carlybaja.nytimessearch.models.SearchOptions;
import com.example.carlybaja.nytimessearch.utils.EndlessScrollListener;
import com.example.carlybaja.nytimessearch.utils.ArticleSearchClient;
import com.example.carlybaja.nytimessearch.utils.Network;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity  implements ImageFiltersDialogs.ImageFiltersDialogListener {

    // variable declaration
    GridView gvResults;
    private SearchOptions searchOptions;
    private boolean isFetching;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setupViews();
    }

    public void setupViews(){

        gvResults = (GridView)findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                submitQuery(false);
            }
        });

        // hook up listener for grid click
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create the intent to display the article
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                // get the article to display
                Article article = articles.get(position);
                // pass in that article into intent
                i.putExtra("article", article);
                // launch the activity
                startActivity(i);
            }
        });
        searchOptions = new SearchOptions("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                searchOptions.searchTerm = query;
                submitQuery(true);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    /**
     *
     * @param isNewQuery
     */
    private void submitQuery(final boolean isNewQuery) {
        if (!Network.isNetworkAvailable(this)) {
            Toast.makeText(getApplicationContext(), "No internet! Try again later...", Toast.LENGTH_SHORT).show();
        }
        if (isFetching) {
            return;
        }

        ArticleSearchClient.searchArticles(searchOptions, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                isFetching = false;
                JSONArray articleJsonResults = null;
                try {
                    if (isNewQuery) {
                        searchOptions.start = "0";
                        adapter.clear();
                    }

                    JSONArray articlesJSON = response.getJSONObject("response").getJSONArray("docs");
                    for (int i = 0; i < articlesJSON.length(); i++) {
                        JSONObject articleJSON = articlesJSON.getJSONObject(i);
                        Article article = new Article();
                        article.webUrl = articleJSON.getString("web_url");
                        article.headline = articleJSON.getJSONObject("headline").getString("main");

                        JSONArray multimedia = articleJSON.getJSONArray("multimedia");
                        if (multimedia.length() > 0) {
                            JSONObject multimediaJson = multimedia.getJSONObject(0);
                            article.thumbNail = "http://www.nytimes.com/" + multimediaJson.getString("url");
                        } else {
                            article.thumbNail = "";
                        }
                        adapter.add(article);
                    }
                    adapter.notifyDataSetChanged();

                    if (adapter.getCount() < 12) {
                        submitQuery(false);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Search failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_filters:
                showFiltersDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFiltersDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ImageFiltersDialogs imageFiltersDialog = ImageFiltersDialogs.newInstance("Advanced Filters",
                searchOptions);
        imageFiltersDialog.show(fm, "fragment_image_filters");
    }


    public void onFinishImageFiltersDialog(SearchOptions searchOptions) {
        this.searchOptions = searchOptions;
        submitQuery(true);
    }


}

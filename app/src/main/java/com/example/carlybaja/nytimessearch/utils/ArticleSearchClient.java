package com.example.carlybaja.nytimessearch.utils;

import com.example.carlybaja.nytimessearch.models.SearchOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by carlybaja on 2/12/16.
 */
public class ArticleSearchClient {

    private static final String BASE_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    private static final String SEARCH_PARAM = "q";
    private static final String BEGIN_DATE = "begin_date";
    private static final String SORT_ORDER= "sot_order";
    private static final String DESK_VALUES = "desk_values";
    private static final String START_PARAM = "start";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void searchArticles(SearchOptions searchOptions, JsonHttpResponseHandler responseHandler) {
        RequestParams params = constructParamsFromSearchOptions(searchOptions);

        // default required params
        params.put("api-key","25e1b95b05d51685062956f3fb15ffd7:3:74386186");
      //  params.put("page",0);


        client.get(BASE_URL, params, responseHandler);
    }

    private static RequestParams constructParamsFromSearchOptions(SearchOptions searchOptions) {
        RequestParams params = new RequestParams();
        if (searchOptions.beginDate != null) {
            params.put(BEGIN_DATE, searchOptions.beginDate);
        }
        if (searchOptions.sortOrder != null) {
            params.put(SORT_ORDER, searchOptions.sortOrder);
        }
        if (searchOptions.deskValues != null) {
            params.put(DESK_VALUES, searchOptions.deskValues);
        }
        if (searchOptions.searchTerm != null) {
            params.put(SEARCH_PARAM, searchOptions.searchTerm);
        }

        if (searchOptions.start != null) {
            params.put(START_PARAM, searchOptions.start);
        }

        return  params;
    }






}

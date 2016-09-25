package com.sam_chordas.android.stockhawk.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by baguzzzaji on 24/09/2016.
 */

public interface QuoteInterface {
    @GET
    Call<QueryResult> getQuotes(
            @Url String url);
}

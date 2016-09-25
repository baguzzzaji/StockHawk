package com.sam_chordas.android.stockhawk.rest;

import com.google.gson.annotations.SerializedName;
import com.sam_chordas.android.stockhawk.data.Query;


/**
 * Created by baguzzzaji on 24/09/2016.
 */

public class QueryResult {

    @SerializedName("query")
    private Query query;

    public Query getQuery() {
        return query;
    }
}

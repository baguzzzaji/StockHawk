package com.sam_chordas.android.stockhawk.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by baguzzzaji on 25/09/2016.
 */

public class Results {
    @SerializedName("quote")
    private List<Quote> quotes;

    public List<Quote> getQuotes() {
        return quotes;
    }
}

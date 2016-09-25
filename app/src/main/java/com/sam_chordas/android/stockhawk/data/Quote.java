package com.sam_chordas.android.stockhawk.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baguzzzaji on 24/09/2016.
 */

public class Quote {
    @SerializedName("Date")
    private String date;
    @SerializedName("Open")
    private String open;
    @SerializedName("High")
    private String high;
    @SerializedName("Low")
    private String low;
    @SerializedName("Close")
    private String close;
    @SerializedName("Volume")
    private String volume;
    @SerializedName("Adj_Close")
    private String adjclose;

    public String getDate() {
        return date;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getClose() {
        return close;
    }

    public String getVolume() {
        return volume;
    }

    public String getAdjclose() {
        return adjclose;
    }
}

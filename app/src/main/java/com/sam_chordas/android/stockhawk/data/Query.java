package com.sam_chordas.android.stockhawk.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baguzzzaji on 25/09/2016.
 */

public class Query {
    @SerializedName("results")
    private Results results;
    @SerializedName("count")
    private String count;
    @SerializedName("created")
    private String created;

    @SerializedName("lang")
    private String lang;

    public Results getResults ()
    {
        return results;
    }

    public void setResults (Results results)
    {
        this.results = results;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String getCreated ()
    {
        return created;
    }

    public void setCreated (String created)
    {
        this.created = created;
    }

    public String getLang ()
    {
        return lang;
    }

    public void setLang (String lang)
    {
        this.lang = lang;
    }
}

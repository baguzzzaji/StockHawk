package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.Quote;
import com.sam_chordas.android.stockhawk.rest.QueryResult;
import com.sam_chordas.android.stockhawk.rest.QuoteInterface;
import com.sam_chordas.android.stockhawk.rest.RetrofitHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStockActivity extends AppCompatActivity {
    private static String TAG = DetailStockActivity.class.getSimpleName();

    private String symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        symbol = getIntent().getStringExtra("symbol");
        getStockDetails();
    }

    private void getStockDetails() {
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, -3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String q = "select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%27"+ symbol + "%27%20and%20startDate%20%3D%20%27" + sdf.format(c.getTime()) +"%27%20and%20endDate%20%3D%20%27" + sdf.format(now) + "%27";
        String format = "json";
        String diagnostics = "true";
        String env = "store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        String url = "yql?q=" + q + "&format=" + format + "&diagnostics=" + diagnostics + "&env=" + env;
        QuoteInterface quoteInterface = RetrofitHelper.getClient().create(QuoteInterface.class);
        Call<QueryResult> call = quoteInterface.getQuotes(url);

        call.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                List<Quote> quotes = response.body().getQuery().getResults().getQuotes();
                drawChart(quotes);
                Log.d(TAG, "onResponse: " + response.body().getQuery().getCount());
            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void drawChart(List<Quote> quotes) {
        LineChartView lineChart = (LineChartView) findViewById(R.id.linechart);

        LineSet dataSet = new LineSet();
        float min = Float.valueOf(quotes.get(0).getHigh());
        float max = 0;
        for (Quote q :
                quotes) {
            String date = q.getDate();
            float high = Float.parseFloat(q.getHigh());
            dataSet.addPoint(date, high);

            if (high > max) {
                max = high;
            }

            if (high < min) {
                min = high;
            }

        }
        lineChart.addData(dataSet);
        lineChart
                .setAxisBorderValues(Math.round(min) - 1, Math.round(max) + 1)
                .setXLabels(AxisController.LabelPosition.NONE)
                .setXAxis(true)
                .setYAxis(true);
        lineChart.show();
    }
}

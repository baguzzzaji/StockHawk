package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.Utils;

/**
 * Created by baguzzzaji on 25/09/2016.
 */
public class QuoteWidgetRemoteViewsService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        return new RemoteViewsFactory() {
            Cursor cursor = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (cursor != null) {
                    cursor.close();
                }

                final long token = Binder.clearCallingIdentity();

                cursor = getContentResolver().query(
                        QuoteProvider.Quotes.CONTENT_URI,
                        new String[] {
                                QuoteColumns._ID,
                                QuoteColumns.SYMBOL,
                                QuoteColumns.BIDPRICE,
                                QuoteColumns.PERCENT_CHANGE,
                                QuoteColumns.CHANGE,
                                QuoteColumns.ISUP
                        },
                        QuoteColumns.ISCURRENT + " = ?",
                        new String[]{"1"},
                        null
                );
                Binder.restoreCallingIdentity(token);
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return cursor == null ? 0 : cursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int i) {
                if (i == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(i)) {

                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_collection_item);

                views.setTextViewText(R.id.stock_symbol, cursor.getString(cursor.getColumnIndex("symbol")));

                if (cursor.getInt(cursor.getColumnIndex("is_up")) == 1) {
                    views.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_green);
                } else {
                    views.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
                }

                if (Utils.showPercent) {
                    views.setTextViewText(R.id.change, cursor.getString(cursor.getColumnIndex("percent_change")));
                } else {
                    views.setTextViewText(R.id.change, cursor.getString(cursor.getColumnIndex("change")));
                }

                Intent intent2 = new Intent();
                intent2.putExtra("symbol", cursor.getString(cursor.getColumnIndex("symbol")));
                views.setOnClickFillInIntent(R.id.widget_list_item, intent2);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int i) {
                if (cursor != null && cursor.moveToPosition(i)) {
                    int QUOTE_ID_COL = 0;
                    return cursor.getLong(QUOTE_ID_COL);
                }
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}

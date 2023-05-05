package com.example.evaware.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class LinearScrollListView {
    private static final String TAG = "LinearScrollListView";

    public static void justifyListViewHeightBasedOnChildren (ListView listView) {
        BaseAdapter adapter = (BaseAdapter) listView.getAdapter();

        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            Log.d(TAG, "justifyListViewHeightBasedOnChildren: " + listItem.getMeasuredHeight());
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight;
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    public static void justifyListViewHeightBasedOnChildren (ListView listView, int offset) {
        BaseAdapter adapter = (BaseAdapter) listView.getAdapter();

        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            Log.d(TAG, "justifyListViewHeightBasedOnChildren: " + listItem.getMeasuredHeight());
            totalHeight += listItem.getHeight() - offset;
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}

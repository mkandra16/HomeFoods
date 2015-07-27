package com.apps.b3bytes.homefoods.utils;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public class HorizontialListView extends AdapterView<ListAdapter> {

    public HorizontialListView(Context context) {
        super(context);
    }

    @Override
    public ListAdapter getAdapter() {
        return null;
    }

    @Override
    public void setAdapter(ListAdapter listAdapter) {

    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int i) {

    }


}

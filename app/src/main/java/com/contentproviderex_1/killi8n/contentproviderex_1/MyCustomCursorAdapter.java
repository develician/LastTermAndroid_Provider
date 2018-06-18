package com.contentproviderex_1.killi8n.contentproviderex_1;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyCustomCursorAdapter extends CursorAdapter {

    TextView nameText, numberText, ageText;

    public MyCustomCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_row, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        nameText = (TextView) view.findViewById(R.id.nameText);
        numberText = (TextView) view.findViewById(R.id.numberText);
        ageText = (TextView) view.findViewById(R.id.ageText);

        nameText.setText(cursor.getString(2));
        numberText.setText(cursor.getString(1));
        ageText.setText(cursor.getString(3));
    }
}

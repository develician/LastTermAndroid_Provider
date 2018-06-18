package com.contentproviderex_1.killi8n.contentproviderex_1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class MyProvider extends ContentProvider {

    private static final Uri CONTENT_URI = Uri.parse("content://com.contentproviderex_1.killi8n.contentproviderex_1/students");
    static final int ALLDATA = 1;
    static final int ONEDATA = 2;

    SQLiteDatabase db;

    private final static UriMatcher matcher;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(BuildConfig.APPLICATION_ID, "students", ALLDATA);
        matcher.addURI(BuildConfig.APPLICATION_ID, "students/*", ONEDATA);
    }

    @Override
    public boolean onCreate() {
        MyDBOpenHelper helper = new MyDBOpenHelper(getContext(), "mydb-3.db", null, 1);
        db = helper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String sql = "SELECT * FROM students ORDER BY _id DESC";
        if(matcher.match(uri) == ONEDATA) {
            sql += " WHERE number = " + uri.getPathSegments().get(1) + ";";
        }
        return db.rawQuery(sql, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if(matcher.match(uri) == ONEDATA) {
            return "vnd.Jenn.cursor.item/number";
        } else if(matcher.match(uri) == ALLDATA) {
            return "vnd.Jenn.cursor.dir/students";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long row = db.insert("students", null, values);
        if(row > 0) {
            Uri result_uri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(result_uri, null);
            return result_uri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (matcher.match(uri)) {
            case ALLDATA:
                count = db.delete("students", selection, selectionArgs);
                break;
            case ONEDATA:
                String where;
                where = "number = " + uri.getPathSegments().get(1) + ";";
                if(TextUtils.isEmpty(selection) == false) {
                    where += " AND " + selection;
                }
                count = db.delete("students", where, selectionArgs);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        db.update("students", values, selection, selectionArgs);
        return 0;
    }
}

class MyDBOpenHelper extends SQLiteOpenHelper {

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE students (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "number INTEGER, " +
                "name TEXT, " +
                "age INTEGER" +
                ");";

        String insertMockDataSQL = "INSERT INTO students (number, name, age) VALUES (201307046, 'Dongho Choi', 24)";

        db.execSQL(createTableSQL);
        db.execSQL(insertMockDataSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
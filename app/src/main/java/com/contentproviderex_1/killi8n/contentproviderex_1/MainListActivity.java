package com.contentproviderex_1.killi8n.contentproviderex_1;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainListActivity extends AppCompatActivity {


    private ListView listView;

    private Cursor cursor;
    private ContentResolver contentResolver;

    private static final Uri CONTENT_URI = Uri.parse("content://com.contentproviderex_1.killi8n.contentproviderex_1/students");

    private static final int MENU_INSERT = Menu.FIRST;
    private static final int MENU_UPDATE = Menu.FIRST + 1;
    private static final int MENU_DELETE = Menu.FIRST + 2;

    private static final int RQ_CODE_INSERT = 1;
    private static final int RQ_CODE_UPDATE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        contentResolver = getContentResolver();
        cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        MyCustomCursorAdapter adapter = new MyCustomCursorAdapter(MainListActivity.this, cursor);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_INSERT, Menu.NONE, "추가하기");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_INSERT:
                Intent intent = new Intent(this, InsertActivity.class);
                intent.putExtra("ver", "insert");
                startActivityForResult(intent, RQ_CODE_INSERT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, MENU_UPDATE, Menu.NONE, "수정하기");
        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "삭제하기");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        cursor.moveToPosition(info.position);
        int _id = cursor.getInt(0);
        int number = cursor.getInt(1);
        String name = cursor.getString(2);
        int age = cursor.getInt(3);
        switch (item.getItemId()) {
            case MENU_UPDATE:
                Intent intent = new Intent(this, InsertActivity.class);
                intent.putExtra("number", number);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("ver", "update");
                intent.putExtra("_id", _id);
                startActivityForResult(intent, RQ_CODE_UPDATE);
                break;
            case MENU_DELETE:
                contentResolver.delete(CONTENT_URI, "_id = " + _id, null);
                cursor.requery();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQ_CODE_INSERT:
                    cursor.requery();
                    break;
                case RQ_CODE_UPDATE:
                    cursor.requery();
                    break;
            }
        }
    }
}

package com.contentproviderex_1.killi8n.contentproviderex_1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertActivity extends AppCompatActivity {

    private EditText numberEditText, nameEditText, ageEditText;
    private Button insertButton;
    private ContentResolver resolver;
    private static final Uri CONTENT_URI = Uri.parse("content://com.contentproviderex_1.killi8n.contentproviderex_1/students");

    private int number, age, _id;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        resolver = getContentResolver();

        numberEditText = (EditText) findViewById(R.id.numberEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        ageEditText = (EditText) findViewById(R.id.ageEditText);
        insertButton = (Button) findViewById(R.id.insertButton);


        Intent intent = getIntent();
        if(intent.hasExtra("number")) {
            number = intent.getIntExtra("number", -1);
//            Log.d("GetIntent,,,", number + "");
            numberEditText.setText(number + "");
        }
        if(intent.hasExtra("name")) {
            name = intent.getStringExtra("name");
            nameEditText.setText(name);
//            Log.d("GetIntent,,,", name + "");
        }
        if(intent.hasExtra("age")) {
            age = intent.getIntExtra("age", -1);
            ageEditText.setText(age + "");
//            Log.d("GetIntent,,,", age + "");

        }
        if(intent.hasExtra("_id")) {
            _id = intent.getIntExtra("_id", -1);
        }


        if(intent.hasExtra("ver")) {
            switch (intent.getStringExtra("ver")) {
                case "update":
                    insertButton.setText("수정하기");
                    insertButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ContentValues row = new ContentValues();

                            int number = Integer.parseInt(numberEditText.getText().toString());
                            String name = nameEditText.getText().toString();
                            int age = Integer.parseInt(ageEditText.getText().toString());

                            row.put("number", number);
                            row.put("name", name);
                            row.put("age", age);

                            resolver.update(CONTENT_URI, row, "_id = " + _id, null);
                            setResult(RESULT_OK, getIntent());
                            finish();
                        }
                    });
                    break;
                case "insert":
                    insertButton.setText("추가하기");
                    insertButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int number = Integer.parseInt(numberEditText.getText().toString());
                            String name = nameEditText.getText().toString();
                            int age = Integer.parseInt(ageEditText.getText().toString());

                            ContentValues row = new ContentValues();
                            row.put("number", number);
                            row.put("name", name);
                            row.put("age", age);
                            resolver.insert(CONTENT_URI, row);
                            setResult(RESULT_OK, getIntent());
                            finish();
                        }
                    });
                    break;
            }
        }








    }
}

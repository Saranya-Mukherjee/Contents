package com.saranya.contents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DBHandler db = new DBHandler(this);
//        db.drop();
    }

    public void go_main_page(View view) {
        Intent intent = new Intent(this, Table1.class);
        startActivity(intent);
    }
}

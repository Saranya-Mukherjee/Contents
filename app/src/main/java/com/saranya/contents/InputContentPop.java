package com.saranya.contents;

import static com.saranya.contents.R.id.topic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class InputContentPop extends AppCompatActivity {

    TextInputLayout teacher;
    TextInputLayout topic;
    TextInputLayout pg_start;
    TextInputLayout pg_end;
    int start=0,end=0;
    int id_copy=0;
    String copy="";
    int min_page=0;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_content_pop);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        getWindow().setLayout((int)(width*0.78),1050);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
//        lp.x=0;
//        lp.y=-300;
        getWindow().setAttributes(lp);
        teacher=findViewById(R.id.teacher);
        teacher.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        topic=findViewById(R.id.topic);
        pg_start=findViewById(R.id.pg_start);
        Objects.requireNonNull(pg_start.getEditText()).setText("0");
        pg_end=findViewById(R.id.pg_end);
        Objects.requireNonNull(pg_end.getEditText()).setText("0");
        id_copy=getIntent().getIntExtra("id",-1);
        copy=getIntent().getStringExtra("name");
        dbHandler=new DBHandler(this);
        set_pg_nos();
    }

    public void dec_page_start(View view){
        if(Integer.parseInt(String.valueOf(Objects.requireNonNull(pg_start.getEditText()).getText()))>min_page){
            int pg=Integer.parseInt(String.valueOf(Objects.requireNonNull(pg_start.getEditText()).getText()));
            pg--;
            Objects.requireNonNull(pg_start.getEditText()).setText(String.valueOf(pg));
        }
    }

    public void dec_page_end(View view){
        if(Integer.parseInt(String.valueOf(Objects.requireNonNull(pg_end.getEditText()).getText()))>min_page){
            int pg=Integer.parseInt(String.valueOf(Objects.requireNonNull(pg_end.getEditText()).getText()));
            pg--;
            Objects.requireNonNull(pg_end.getEditText()).setText(String.valueOf(pg));
        }
    }

    public void inc_page_start(View view){
        int pg=Integer.parseInt(String.valueOf(Objects.requireNonNull(pg_start.getEditText()).getText()));
        int pg2=Integer.parseInt(String.valueOf(Objects.requireNonNull(pg_end.getEditText()).getText()));
        if(pg<pg2) {
            pg++;
            Objects.requireNonNull(pg_start.getEditText()).setText(String.valueOf(pg));
        }
    }

    public void inc_page_end(View view){
        int pg2=Integer.parseInt(String.valueOf(Objects.requireNonNull(pg_end.getEditText()).getText()));
        pg2++;
        Objects.requireNonNull(pg_end.getEditText()).setText(String.valueOf(pg2));
    }

    public void set_pg_nos(){
//        dbHandler.drop();
        int max=dbHandler.get_max_pg(copy);
        min_page=max;
        Objects.requireNonNull(pg_start.getEditText()).setText(String.valueOf(max));
        Objects.requireNonNull(pg_end.getEditText()).setText(String.valueOf(max));
    }

    public void send_data(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        Intent intent = new Intent();
        String teach = String.valueOf(Objects.requireNonNull(teacher.getEditText()).getText());
        String top = String.valueOf(Objects.requireNonNull(topic.getEditText()).getText());
        if(top.length()>22){
            top=top.substring(0,23);
            Toast.makeText(this, "Excess cut off", Toast.LENGTH_SHORT).show();
        }
        String start = String.valueOf(Objects.requireNonNull(pg_start.getEditText()).getText());
        String end = String.valueOf(Objects.requireNonNull(pg_end.getEditText()).getText());
        if(teach.length() == 0 || top.length() == 0){
            finish();
        }
        intent.putExtra("teach",teach);
        intent.putExtra("topic",top);
        intent.putExtra("start",start);
        intent.putExtra("end",end);
        setResult(100,intent);
        finish();
    }

}
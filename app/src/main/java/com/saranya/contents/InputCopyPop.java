package com.saranya.contents;

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
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class InputCopyPop extends AppCompatActivity {

    TextInputLayout copyName;
    Button copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_copy_pop);
        copyName=findViewById(R.id.copy_name);
        copyName.requestFocus();
        copy=findViewById(R.id.submit);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        getWindow().setLayout((int)(width*0.78),527);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
//        Log.d("database", "H"+String.valueOf((int)(height*0.23)));
//        Log.d("database", "W"+String.valueOf((int)(width*0.78)));
    }

    @Override
    protected void onStop() {
        copy.performClick();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void send_data(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        Intent intent = new Intent();
        String data = String.valueOf(Objects.requireNonNull(copyName.getEditText()).getText());
        if(data.length() == 0){
            finish();
        }
        intent.putExtra("data",data);
        setResult(100,intent);
        finish();
    }
}

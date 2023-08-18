package com.saranya.contents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class Table_contents extends AppCompatActivity {

    MaterialToolbar toolbar;
    String copy;
    DBHandler dbhandler;
    TextView textView;
    String contents="";
    RecyclerView contentView;
    ArrayList<Content> content;
    ContentAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_contents);
        toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        ActionBar ab=getSupportActionBar();
        assert ab != null;
        copy=intent.getStringExtra("name");
        ab.setTitle(copy);
        dbhandler=new DBHandler(this);
        textView=findViewById(R.id.text1);
        contentView=findViewById(R.id.contentView);
        content=new ArrayList<>();
        read_all_contents();
        contentAdapter= new ContentAdapter(content,this);
        contentView.setAdapter(contentAdapter);
        contentView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tablemenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void new_content(int id){
        Intent intent = new Intent(this, InputContentPop.class);
        intent.putExtra("id",id);
        intent.putExtra("name",copy);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String teach = data != null ? data.getStringExtra("teach") : null;
        String top = data != null ? data.getStringExtra("topic") : null;
        String start = data != null ? data.getStringExtra("start") : null;
        String end = data != null ? data.getStringExtra("end") : null;
        if(teach != null && teach.length() > 0) {
            add_content(teach,top,start,end);
//            Toast.makeText(this, teach, Toast.LENGTH_SHORT).show();
        }
    }

    private void add_content(String teach, String top, String start, String end) {
        dbhandler.add_content(teach,top,start,end,copy);
//        content.add(new Content(teach,top,start,end,copy));
        contentAdapter.add_new_content(new Content(teach,top,start,end,copy));
        textView.setText(contents);
    }

    public void delete_all_contents(){
        dbhandler.delete_all_contents(copy);
        contentAdapter.delete_all_contents();
    }

    public void read_all_contents(){
        content.addAll(dbhandler.get_contents(copy));
        textView.setText(contents);
    }

    public int get_max_id(){
        int id=-1;
        id=dbhandler.get_copy_id(copy);
        return id;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                new_content(get_max_id());
                break;
            case R.id.del:
                Toast.makeText(this, "del",Toast.LENGTH_SHORT).show();
                break;
            case R.id.clear:
                delete_all_contents();
                break;
            case R.id.logout:
                Toast.makeText(this, "Not implemented",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
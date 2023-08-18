package com.saranya.contents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;


@SuppressWarnings("deprecation")
public class Table1 extends AppCompatActivity {

    MaterialToolbar toolbar;
    ListView copies;
    ArrayList<String> copies_array = new ArrayList<>();
    ArrayAdapter<String> copies_adapter;
    private DBHandler dbhandler;
    String selection="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table1);
        toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        assert ab != null;
        ab.setTitle("Your Copies");
        copies=findViewById(R.id.copies);
        copies_adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, copies_array);
        copies.setAdapter(copies_adapter);
        copies.setOnItemClickListener((parent, view, position, id) -> {
            if(selection.length()==0) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                go_to_copy(selectedItem);
            }
            else {
                deselect_copies();
            }
        });
        copies.setOnItemLongClickListener((parent, view, pos, id) -> {
            if(selection.length()==0) {
                String selectedItem = (String) parent.getItemAtPosition(pos);
                select_copy(selectedItem);
            }
            else{
                deselect_copies();
                String selectedItem = (String) parent.getItemAtPosition(pos);
                select_copy(selectedItem);
            }
            return true;
        });
        dbhandler = new DBHandler(this);
        read_stored_copies();
    }

    public void deselect_copies(){
        int c=0;
        for(String copy :copies_array){
            if(copy.startsWith("*")&&copy.endsWith("*")){
                copies_array.set(c,copy.substring(1,copy.length()-1));
                selection="";
            }
            c++;
        }
        copies_adapter.notifyDataSetChanged();
    }

    public void delete_copy(String name){
        dbhandler.delete_copy(name);
        selection="";
        copies_array.clear();
        read_stored_copies();
    }

    public void select_copy(String name){
        int c=0;
        for(String copy :copies_array){
            if(copy.equals(name)){
                copies_array.set(c,"*"+name+"*");
                selection=name;
            }
            c++;
        }
        copies_adapter.notifyDataSetChanged();
    }

    public void go_to_copy(String name){
        Intent intent = new Intent(this,Table_contents.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    public void read_stored_copies(){
        copies_array.addAll(dbhandler.get_copies());
        copies_adapter.notifyDataSetChanged();
    }

    public void add_copy(String name){
        selection="";
        deselect_copies();
        int flag=1;
        for(String copy :copies_array){
            if (copy.equals(name)) {
                flag = 0;
                break;
            }
        }
        if(flag==1){
            copies_array.add(name);
            copies_adapter.notifyDataSetChanged();
            dbhandler.add_copy(name);
        }
        else{
            Toast.makeText(this,"Already Exists",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tablemenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void new_copy(){
        Intent intent = new Intent(this, InputCopyPop.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onResume() {
        Log.d("database","called");
        View view = new View(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String value = data != null ? data.getStringExtra("data") : null;
        if(value != null && value.length() > 0) {
            add_copy(value);
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar.getApplicationWindowToken(),0);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                new_copy();
                break;
            case R.id.del:
                if(selection.length()>0){
                    delete_copy(selection);
                }
                else{
                    Toast.makeText(this,"No Copy Selected",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clear:
                dbhandler.delete_all_copies();
                copies_array.clear();
                read_stored_copies();
                Toast.makeText(this,"Deleted All", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, "Not implemented",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
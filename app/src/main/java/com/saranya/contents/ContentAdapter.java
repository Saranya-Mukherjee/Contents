package com.saranya.contents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    ArrayList<Content> contents;
    Context context;
    public static int width=0;

    public ContentAdapter(ArrayList<Content> contents, Context context) {
        this.contents = contents;
        this.context = context;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void add_new_content(Content newContent) {
        contents.add(newContent);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void delete_all_contents() {
        contents.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.content_row,parent,false);
        width=(int)parent.getMeasuredWidth();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.ViewHolder holder, int position) {
        Content content=contents.get(position);
        Objects.requireNonNull(holder.teach.getEditText()).setText(content.getTeacher());
        String topic=content.getTopic();
        char ch=32;
        if(topic.length()>22){
            topic=topic.substring(0,22)+"\n"+topic.substring(22,topic.length());
        }
        Objects.requireNonNull(holder.top.getEditText()).setText(topic);
        String pages=content.getPg_start()+"-"+content.getPg_end();
        Objects.requireNonNull(holder.pg.getEditText()).setText(pages);
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextInputLayout teach,top,pg;
        TextInputEditText teach_edit,top_edit,pg_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Constants constants=new Constants();
            teach=itemView.findViewById(R.id.teach);
            teach_edit=itemView.findViewById(R.id.teach_edit);
            teach_edit.setWidth((int)(constants.width*0.2));
            top=itemView.findViewById(R.id.top);
            top_edit=itemView.findViewById(R.id.top_edit);
            top_edit.setWidth((int)(constants.width*0.5));
            pg=itemView.findViewById(R.id.start);
            pg_edit=itemView.findViewById(R.id.pg_edit);
            pg_edit.setWidth((int)(constants.width*0.26));
        }
    }
}

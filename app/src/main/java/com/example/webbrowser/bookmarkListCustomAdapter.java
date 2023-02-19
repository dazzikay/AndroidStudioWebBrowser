package com.example.webbrowser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class bookmarkListCustomAdapter extends BaseAdapter {
    private List<websites> itemList;
    private LayoutInflater inflater;


    public bookmarkListCustomAdapter(Activity context, List<websites> itemList){
        this.itemList = itemList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public static class ViewHolder{
        TextView textViewTitleBM;
        TextView textViewSubtitleBM;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;

        if(convertView == null){
            holder = new bookmarkListCustomAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.websiterow_bookmark, null);

            holder.textViewTitleBM = (TextView)convertView.findViewById(R.id.textViewTitleBM);
            holder.textViewSubtitleBM =(TextView)convertView.findViewById(R.id.textViewSubtitleBM);

            convertView.setTag(holder);
        }
        else
            holder = (bookmarkListCustomAdapter.ViewHolder)convertView.getTag();

        websites website = (websites)itemList.get(position);
        holder.textViewTitleBM.setText(website.getTitle());
        holder.textViewSubtitleBM.setText(website.getUrl());

        return convertView;
    }
}

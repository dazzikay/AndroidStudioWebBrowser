package com.example.webbrowser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class historyListCustomAdapter extends BaseAdapter {

        private List<websites> itemList;
        private LayoutInflater inflater;

        public historyListCustomAdapter(Activity context, List<websites> itemList){
                this.itemList = itemList;
                this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

//Abstract method from parent
        @Override
        public int getCount() {
                return itemList.size();
        }

        @Override
        public Object getItem(int position) {
                return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
                return position;
        }

      public static class ViewHolder{
                TextView textViewTitle;
                TextView textViewSubtitle;
      }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;

                if(convertView == null){
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.websiterow_history, null);

                    holder.textViewTitle = (TextView)convertView.findViewById(R.id.textViewTitle);
                    holder.textViewSubtitle =(TextView)convertView.findViewById(R.id.textViewSubtitle);

                    convertView.setTag(holder);
                }

                else
                    holder = (ViewHolder)convertView.getTag();

                websites website = (websites)itemList.get(position);
                holder.textViewTitle.setText(website.getTitle());
                holder.textViewSubtitle.setText(website.getUrl());

                return convertView;
        }
}

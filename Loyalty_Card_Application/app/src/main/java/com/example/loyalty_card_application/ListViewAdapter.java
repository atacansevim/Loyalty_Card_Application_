package com.example.loyalty_card_application;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<ListViewElement> list;
    public ListViewAdapter(Activity activity, List<ListViewElement> mList)
    {
        layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list=mList;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View elementView;
        elementView = layoutInflater.inflate(R.layout.cardelement,null);
        ImageView iv = (ImageView)elementView.findViewById(R.id.CardImageView);
        TextView tv = (TextView)elementView.findViewById(R.id.CardNameTextView);

        ListViewElement cardlist = list.get(position);
        tv.setText(cardlist.getName().toString());
        iv.setImageResource(R.drawable.common_full_open_on_phone);

        return elementView;
    }
}

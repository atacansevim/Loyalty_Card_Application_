package com.example.loyalty_card_application;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class
GridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GridViewElement> arrayList;


    public GridViewAdapter(Context context, ArrayList<GridViewElement> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;

    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Button button;

        if(convertView == null)
        {
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(arrayList.get(position).getWidth(),arrayList.get(position).getHeight()));
            button.setPadding(0,20,2,20);
            button.setText(arrayList.get(position).getName());
            button.setBackgroundColor(arrayList.get(position).getBgcolor());
            button.setTextColor(arrayList.get(position).txtcolor);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,button.getText().toString(),Toast.LENGTH_LONG).show();

                }
            });
        }
        else
        {
            button = (Button)convertView;
        }
        return button;
    }
}

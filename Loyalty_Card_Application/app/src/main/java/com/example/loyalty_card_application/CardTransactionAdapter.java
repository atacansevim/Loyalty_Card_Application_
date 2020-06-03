package com.example.loyalty_card_application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CardTransactionAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CardTransactionElement> arrayList;
    Context c;


    public CardTransactionAdapter(Context context, ArrayList<CardTransactionElement> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
        c = context;
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



        View row;
        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.cardtransactionlist, null);
        TextView date = (TextView)row.findViewById(R.id.cardtransactiondate);
        TextView amount = (TextView)row.findViewById(R.id.cardtransactionamount);
        CardTransactionElement _CardTransactionlist = arrayList.get(position);

        date.setText(_CardTransactionlist.getDate());
        amount.setText(_CardTransactionlist.getAmount());
        return row;
    }


}

package com.example.loyalty_card_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChooseCardFromListActivity extends AppCompatActivity {
    ListView lsttop;
    ListView lstall;
    ListViewAdapter adapter_top;
    ListViewAdapter adapter_all;
    EditText SearchFilter = (EditText)findViewById(R.id.searchFilter);
    List<ListViewElement> allcards = new ArrayList<ListViewElement>();
    List<ListViewElement> topcards = new ArrayList<ListViewElement>();
    String[] topname = {"X","Y","Z"};
    String[] allname = {"X","Y","Z","R","T","E","A"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_card_from_list);
        lsttop = (ListView) findViewById(R.id.toplistview);
        lstall = (ListView) findViewById(R.id.alllistview);
        FillListView();
        adapter_top = new ListViewAdapter(this,topcards);
        adapter_all = new ListViewAdapter(this,allcards);
        lstall.setAdapter(adapter_all);
        lsttop.setAdapter(adapter_top);


    }
    public void FillListView()
    {
        for(int i = 0; i < allname.length;i++)
        {
            allcards.add(new ListViewElement(allname[i]));
        }
        for(int i = 0; i < topname.length;i++)
        {
            topcards.add(new ListViewElement(topname[i]));
        }

    }
}

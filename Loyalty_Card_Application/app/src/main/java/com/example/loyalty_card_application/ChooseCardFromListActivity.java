package com.example.loyalty_card_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChooseCardFromListActivity extends AppCompatActivity{
    ListView lsttop;
    ListView lstall;
    ListViewAdapter adapter_top;
    ListViewAdapter adapter_all;
    EditText SearchFilter;
    ArrayList<ListViewElement> allcards = new ArrayList<ListViewElement>();
    ArrayList<ListViewElement> topcards = new ArrayList<ListViewElement>();
    String[] topname = {"X","Y","Z"};
    String[] allname = {"X","Y","Z","R","T","E","A"};
    int[] topimages = {R.drawable.common_google_signin_btn_icon_light_normal,R.drawable.common_google_signin_btn_icon_light_normal,R.drawable.common_google_signin_btn_icon_light_normal};
    int[] allimages = {R.drawable.common_google_signin_btn_icon_light_normal,R.drawable.common_google_signin_btn_icon_light_normal,R.drawable.common_google_signin_btn_icon_light_normal,R.drawable.common_google_signin_btn_icon_light_normal,R.drawable.common_google_signin_btn_icon_light_normal,R.drawable.common_google_signin_btn_icon_light_normal,R.drawable.common_google_signin_btn_icon_light_normal};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_card_from_list);
        lsttop = (ListView) findViewById(R.id.toplistview);
        lstall = (ListView) findViewById(R.id.alllistview);
        SearchFilter = (EditText)findViewById(R.id.editText);
        FillListView();
        adapter_top = new ListViewAdapter(this,topcards);
        adapter_all = new ListViewAdapter(this,allcards);
        lstall.setAdapter(adapter_all);
        lsttop.setAdapter(adapter_top);
        SearchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ChooseCardFromListActivity.this).adapter_all.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lsttop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ChoosenElement = topname[position].toString();
                Intent intent = new Intent(ChooseCardFromListActivity.this,ScanBarcode.class);
                intent.putExtra("CardName",ChoosenElement);
                startActivity(intent);

            }
        });
        lstall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ChoosenElement = allname[position].toString();
                Intent intent = new Intent(ChooseCardFromListActivity.this,ScanBarcode.class);
                intent.putExtra("CardName",ChoosenElement);
                startActivity(intent);
            }
        });


    }
    public void FillListView()
    {
        for(int i = 0; i < allname.length;i++)
        {
            allcards.add(new ListViewElement(allname[i],allimages[i]));
        }
        for(int i = 0; i < topname.length;i++)
        {
            topcards.add(new ListViewElement(topname[i],topimages[i]));
        }

    }
}

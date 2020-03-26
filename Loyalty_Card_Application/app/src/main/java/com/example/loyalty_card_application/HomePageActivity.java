package com.example.loyalty_card_application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    GridView cardslist;
    int gridviewheight;
    int getGridviewwidth;
    ArrayList<GridViewElement> array_characters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        cardslist = (GridView)findViewById(R.id.cardlist);

        FillListView();
        if(getIntent() != null && getIntent().getStringExtra("CardName") != null)
        {
            String name = getIntent().getStringExtra("CardName");
            int txtcolor =  getIntent().getIntExtra("textcolor",Color.WHITE);
            int bgcolor = getIntent().getIntExtra("bgcolor",Color.rgb(255,165,0));
            array_characters.add(new GridViewElement(name,txtcolor,bgcolor, 525, 525));
        }
        GridViewAdapter adapter = new GridViewAdapter(this,array_characters);
        cardslist.setAdapter(adapter);

    }
    public void FillListView()
    {
        getGridviewwidth =  cardslist.getWidth();
       //length get size firebasedan
        array_characters.add(new GridViewElement("BIM", Color.RED,Color.WHITE,525,525));
        array_characters.add(new GridViewElement("MIGROS",Color.rgb(255,165,0),Color.WHITE,525,525));


    }


    public void addcard(View view)
    {
        Intent intent = new Intent(HomePageActivity.this,ChooseCardFromListActivity.class);
        startActivity(intent);
    }

}

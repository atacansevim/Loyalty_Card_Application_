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
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        cardslist = (GridView)findViewById(R.id.cardlist);

        FillListView();
        if(getIntent() != null && getIntent().getStringExtra("CardName") != null)
        {
            String name = getIntent().getStringExtra("CardName");
            int txtcolor =  getIntent().getIntExtra("textcolor",Color.WHITE);
            int bgcolor = getIntent().getIntExtra("bgcolor",Color.BLACK);
            array_characters.add(new GridViewElement(name,txtcolor,bgcolor, 200, 200));
        }
        GridViewAdapter adapter = new GridViewAdapter(this,array_characters);
        cardslist.setAdapter(adapter);

    }
    public void FillListView()
    {
        getGridviewwidth =  cardslist.getWidth();
        for(int i = 0; i < 5;i++)//length get size firebasedan
        {
            array_characters.add(new GridViewElement(Integer.toString(getGridviewwidth), Color.YELLOW,Color.BLACK,200,200));
        }

    }


    public void addcard(View view)
    {
        Intent intent = new Intent(HomePageActivity.this,ChooseCardFromListActivity.class);
        startActivity(intent);
    }

}

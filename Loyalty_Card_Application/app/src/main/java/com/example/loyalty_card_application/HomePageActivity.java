package com.example.loyalty_card_application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    GridView cardslist;
    int gridviewheight;
    int getGridviewwidth;
    String currentuseremail;String useremal;
    ArrayList<GridViewElement> array_characters;
    String test = "zz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        cardslist = (GridView)findViewById(R.id.cardlist);
        array_characters = new ArrayList<>();
        getdataFromFirebase();
        /*if(getIntent() != null && getIntent().getStringExtra("CardName") != null)
        {
            String name = getIntent().getStringExtra("CardName");
            int txtcolor =  getIntent().getIntExtra("textcolor",Color.WHITE);
            int bgcolor = getIntent().getIntExtra("bgcolor",Color.rgb(255,165,0));
            array_characters.add(new GridViewElement(name,txtcolor,bgcolor, 525, 525));
        }*/

        cardslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ChoosenElement = array_characters.get(position).toString();
                Intent intent = new Intent(HomePageActivity.this,CardDetailsActivity.class);
                intent.putExtra("CardName",ChoosenElement);
                startActivity(intent);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        String ChoosenElement = array_characters.get(1).name;
                        Intent intent = new Intent(HomePageActivity.this,CardDetailsActivity.class);
                        intent.putExtra("CardName","Migros");
                        startActivity(intent);
                        break;
                    case R.id.navigation_add_card:
                        Intent a = new Intent(HomePageActivity.this,ChooseCardFromListActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_offers:

                        break;
                        case R.id.navigation_account:
                            Intent b = new Intent(HomePageActivity.this,AccountPageActivity.class);
                            startActivity(b);

                        break;
                }
                return false;
            }
        });

    }


    public void addcard(View view)
    {
        Intent intent = new Intent(HomePageActivity.this,ChooseCardFromListActivity.class);
        startActivity(intent);
    }

    public void getdataFromFirebase()
    {

        CollectionReference collectionReference = firebaseFirestore.collection("CardData");
        collectionReference.whereEqualTo("userEmail",currentuseremail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                {

                }
                if(queryDocumentSnapshots != null)
                {
                    int count = 0;
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                    {
                        if(count %2 == 0) {
                            count++;
                            array_characters.add(new GridViewElement(d.get("CardName").toString(), Color.RED, Color.WHITE, 525, 525));
                        }
                        else
                            array_characters.add(new GridViewElement(d.get("CardName").toString(),Color.rgb(255,165,0),Color.WHITE, 525, 525));



                    }
                    GridViewAdapter adapter = new GridViewAdapter(HomePageActivity.this,array_characters);
                    cardslist.setAdapter(adapter);
                }
            }
        });




    }

}

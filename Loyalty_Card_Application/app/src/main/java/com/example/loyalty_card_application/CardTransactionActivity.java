package com.example.loyalty_card_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardTransactionActivity extends AppCompatActivity {
    ListView CardTransaction;
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    String currentuseremail;
    String CardName;
    String CardNumber;
    ImageView cardlogo;
    ArrayList<CardTransactionElement> transactionlist = new ArrayList<CardTransactionElement>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_transaction);
        CardTransaction = (ListView)findViewById(R.id.transaction_view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        CardName = getIntent().getStringExtra("CardName");
        CardNumber = getIntent().getStringExtra("CardNumber");
        cardlogo = (ImageView)findViewById(R.id.cardLogo);
        GetUrl();
        getdataFromFirebase();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent CardDetailsFromListActivity = new Intent(CardTransactionActivity.this,HomePageActivity.class);
                        startActivity(CardDetailsFromListActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(CardTransactionActivity.this,ChooseCardFromListActivity.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(CardTransactionActivity.this,OfferActivity.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:
                        Intent AccountPageActivity = new Intent(CardTransactionActivity.this,AccountPageActivity.class);
                        startActivity(AccountPageActivity);

                        break;
                }
                return false;
            }
        });
    }

    public void GetUrl()
    {
        StorageReference urlreferance = FirebaseStorage.getInstance().getReference("logo/"+CardName.toLowerCase()+".png");
        urlreferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String dowlandurl = uri.toString();
                Picasso.get().load(dowlandurl).into(cardlogo);
            }
        });
    }


    public void getdataFromFirebase()
    {

        CollectionReference collectionReference = firebaseFirestore.collection("ShopData");
        collectionReference.whereEqualTo("userEmail",currentuseremail).whereEqualTo("CardName",CardName).whereEqualTo("CardNumber",CardNumber).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                {

                }
                if(queryDocumentSnapshots != null)
                {
                    transactionlist.add(new CardTransactionElement("DATE","AMOUNT"));
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                    {

                        transactionlist.add(new CardTransactionElement(d.get("Date").toString(),d.get("Amount").toString()));

                    }

                    CardTransactionAdapter adapter = new CardTransactionAdapter(CardTransactionActivity.this,transactionlist);
                    CardTransaction.setAdapter(adapter);
                }
            }
        });
    }
}

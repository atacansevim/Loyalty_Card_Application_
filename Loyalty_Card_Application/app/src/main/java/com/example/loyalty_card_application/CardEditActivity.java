package com.example.loyalty_card_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class CardEditActivity extends AppCompatActivity {

   EditText cardnumber;
   EditText carddescription;
   String CardDescription;
   String CardNumber;
   String CardName;
   String currentuseremail;
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    String DocumentId;
    ImageView cardlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_edit);
        cardnumber = (EditText)findViewById(R.id.cardnumber);
        carddescription = (EditText)findViewById(R.id.carddescription);
        cardlogo = (ImageView)findViewById(R.id.cardImageView2);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail().toString();
        CardName = getIntent().getStringExtra("CardName");
        CardNumber = getIntent().getStringExtra("CardNumber");
        DocumentId = getIntent().getStringExtra("DocumentId");
        CardDescription = getIntent().getStringExtra("CardDescription");
        carddescription.setText(CardDescription);
        cardnumber.setText(CardNumber);
        Picasso.get().load(storageReference.child("logo/"+CardName.toLowerCase()+".png").getDownloadUrl().getResult()).into(cardlogo);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent CardDetailsFromListActivity = new Intent(CardEditActivity.this,HomePageActivity.class);
                        startActivity(CardDetailsFromListActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(CardEditActivity.this,ChooseCardFromListActivity.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(CardEditActivity.this,OfferActivity.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:
                        Intent AccountPageActivity = new Intent(CardEditActivity.this,AccountPageActivity.class);
                        startActivity(AccountPageActivity);

                        break;
                }
                return false;
            }
        });


    }
    public void Update(View view)
    {
        DocumentReference UserDataRef = firebaseFirestore.collection("CardData").document(DocumentId);

        UserDataRef.update("CardNumber",cardnumber.getText().toString(),"CardDescription",carddescription.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(CardEditActivity.this,"Succes",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(CardEditActivity.this,CardDetailsActivity.class);
                    intent.putExtra("CardName",CardName);
                    intent.putExtra("CardNumber",cardnumber.getText().toString());
                    finish();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(CardEditActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();

                }
            }
        });

    }





}

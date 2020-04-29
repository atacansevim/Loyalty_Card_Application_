package com.example.loyalty_card_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ManuallyCardAdding extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    EditText cardnumberText,descriptionText;
    ImageView cardimage;
    String cardName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_card_adding);
        cardnumberText = findViewById(R.id.cardnumber);
        descriptionText = findViewById(R.id.description);
        cardimage = findViewById(R.id.CardImageView);
        cardName = getIntent().getStringExtra("CardName");
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        if(cardName.equals("Migros"))
        {
            cardimage.setImageResource(R.drawable.migros);
        }
        else if(cardName.equals("Teknosa"))
        {
            cardimage.setImageResource(R.drawable.teknosa_logo);
        }
        else
        {
            cardimage.setImageResource(R.drawable.migros);
        }
    }

    public void addcard(View view)
    {
        if(cardnumberText.getText().toString() != null && cardnumberText.getText().toString() != "")
        {
            HashMap<String,Object> CardData = new HashMap<>();
            firebaseFirestore = FirebaseFirestore.getInstance();
            String currentuseremail = firebaseAuth.getCurrentUser().getEmail();
            CardData.put("userEmail",currentuseremail);
            CardData.put("CardName",cardName);
            CardData.put("CardNumber",cardnumberText);
            CardData.put("CardDescription",descriptionText);
            firebaseFirestore.collection("CardData").add(CardData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Intent intent = new Intent(ManuallyCardAdding.this,HomePageActivity.class);
                    intent.putExtra("CardName",cardName);
                    intent.putExtra("textcolor", Color.rgb(255,165,0));
                    intent.putExtra("bgcolor",Color.WHITE);
                    finish();
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ManuallyCardAdding.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

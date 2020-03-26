package com.example.loyalty_card_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ManuallyCardAdding extends AppCompatActivity {
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
    public void SaveCard()
    {

    }

    public void addcard(View view)
    {
        if(cardnumberText.getText().toString() != null && cardnumberText.getText().toString() != "")
        {
            Intent intent = new Intent(ManuallyCardAdding.this,HomePageActivity.class);
            intent.putExtra("CardName",cardName);
            intent.putExtra("textcolor", Color.rgb(255,165,0));
            intent.putExtra("bgcolor",Color.WHITE);
            finish();
            startActivity(intent);
        }
    }
}

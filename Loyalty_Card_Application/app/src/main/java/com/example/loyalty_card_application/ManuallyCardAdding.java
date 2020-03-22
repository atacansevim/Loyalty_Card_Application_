package com.example.loyalty_card_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class ManuallyCardAdding extends AppCompatActivity {
    EditText cardnumberText,descriptionText;
    ImageView cardimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_card_adding);
        cardnumberText = findViewById(R.id.cardnumber);
        descriptionText = findViewById(R.id.description);
        cardimage = findViewById(R.id.CardImageView);
        String cardName = getIntent().getStringExtra("CardName");

        //String test1 = "R.drawable.common_google_signin_btn_icon_light_normal";
        //int test = Integer.parseInt(test1);
        //cardimage.setImageResource(test);




    }
}

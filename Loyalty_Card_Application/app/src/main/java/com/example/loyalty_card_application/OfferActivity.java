package com.example.loyalty_card_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OfferActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private Button mNextBtn;
    private Button mBackBtn;
    private int myCurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        mNextBtn=(Button)findViewById(R.id.nextBtn);
        mBackBtn=(Button)findViewById(R.id.prevBtn);
        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndıcator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(myCurrentPage+1);
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(myCurrentPage-1);
            }
        });
    }
    public void addDotsIndıcator(int position){
        mDots=new TextView[3];
        mDotLayout.removeAllViews();
        for(int i=0;i<mDots.length;i++)
        {
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.cardview_light_background));
            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length>0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));

        }
    }
    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        addDotsIndıcator(position);
        myCurrentPage=position;
        if(position==0)
        {
            mNextBtn.setEnabled(true);
            mBackBtn.setEnabled(false);
            mBackBtn.setVisibility(View.INVISIBLE);

            mNextBtn.setText("Next");
            mBackBtn.setText("");
        }
        else if(position==mDots.length-1)
        {
            mNextBtn.setEnabled(true);
            mBackBtn.setEnabled(true);
            mBackBtn.setVisibility(View.VISIBLE);

            mNextBtn.setText("Finish");
            mBackBtn.setText("Back");
        }
        else{
            mNextBtn.setEnabled(true);
            mBackBtn.setEnabled(true);
            mBackBtn.setVisibility(View.VISIBLE);

            mNextBtn.setText("Next");
            mBackBtn.setText("Back");

        }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

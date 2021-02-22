package com.sharpflux.shetkarimaza.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;

public class SubscriptionPlanActivity extends Activity {

    CardView cardView_1, cardView_2, cardView_3, cardView_4;
    TextView textView_amount1, textView_amount2, textView_amount3,textView_amount4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_plan);

        cardView_1 = findViewById(R.id.cardView_1);
        cardView_2 = findViewById(R.id.cardView_2);
        cardView_3 = findViewById(R.id.cardView_3);
        cardView_4 = findViewById(R.id.cardView_4);

        textView_amount1 = findViewById(R.id.textView_amount1);
        textView_amount2 = findViewById(R.id.textView_amount2);
        textView_amount3 = findViewById(R.id.textView_amount3);
        textView_amount4 = findViewById(R.id.textView_amount4);


        onClickListener();


    }

    private void onClickListener() {

        onCardClick(cardView_1, textView_amount1);
        onCardClick(cardView_2, textView_amount2);
        onCardClick(cardView_3, textView_amount3);
        onCardClick(cardView_4, textView_amount4);


    }

    private void onCardClick(CardView cardView, final TextView textView_amount) {

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubscriptionPlanActivity.this, PaymentActivity.class);
                intent.putExtra("Amount", Integer.parseInt(textView_amount.getText().toString()));
                startActivity(intent);
            }
        });
    }
}
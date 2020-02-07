package com.example.egoapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class DisplayTicket  extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.display_ticket);
        TextView startCity = findViewById(R.id.showedStartCity);
        startCity.setText(ShareData.tripSelectedStartCity);
        TextView endCity = findViewById(R.id.showedEndCity);
        endCity.setText(ShareData.tripSelectedEndCity);
        TextView dateToGo = findViewById(R.id.showedDate);
        dateToGo.setText(ShareData.tripSelectedDate);
        TextView timeToGo = findViewById(R.id.showedTime);
        timeToGo.setText(ShareData.tripSelectedTime);
        TextView numOfPassenger = findViewById(R.id.showedNumberOfPassenger);
        numOfPassenger.setText(ShareData.tripNumberOfPassenger);

        TextView tax = findViewById(R.id.showedTax);
        TextView amount = findViewById(R.id.showedAmount);

        amount.setText("10");

        TextView total = findViewById(R.id.showedTotalAmount);

        // calculate amount & tax
        // 1. covert amount
        String toConvertStr;
        toConvertStr = amount.getText().toString();
        int toConvertAmount = Integer.parseInt(toConvertStr);

        // 2. convert passenger
        toConvertStr = numOfPassenger.getText().toString();
        int toConvertPassenger = Integer.parseInt(toConvertStr);
        // 3. assign tax
        float convertTax = 0.13f;
        float totalAmount = 0.0f;

        convertTax = convertTax * toConvertAmount * toConvertPassenger;
        totalAmount = convertTax + toConvertAmount * toConvertPassenger;

        // assign the value for ticket price
        tax.setText(Float.toString(convertTax));
        total.setText(Float.toString(totalAmount));

        //TextView amountPerCustomer = findViewById(R. id.amountPerCustomer);
        //amountPerCustomer.setText(ShareData.amountPerCustomer);

        // Toast myToast = Toast.makeText(getApplicationContext(), "we are in layout 2 and the new value is " + ShareData.something + " and " + ShareData.something2, Toast.LENGTH_SHORT);
        // myToast.show();
        Button myBtn = findViewById(R.id.displayTicketBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DisplayTicket.this, DisplayTicket.class);
                startActivity(myIntent);
            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(DisplayTicket.this, DisplayTicket.class);
        startActivity(myIntent);

    }
}

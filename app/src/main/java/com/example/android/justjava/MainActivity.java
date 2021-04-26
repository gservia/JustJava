package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this, "100 it's the limit", Toast.LENGTH_SHORT).show();
        } else {
            quantity += 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You need at least a cup of coffee", Toast.LENGTH_SHORT).show();
        } else {
            quantity -= 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText textName = (EditText) findViewById(R.id.name_text);
        String userName = textName.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whippedCream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String request = createOrderSummary(userName, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
          intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + userName);
          intent.putExtra(Intent.EXTRA_TEXT, request);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int pricePerCoffee = 5;

        if (hasWhippedCream) {
            pricePerCoffee += 1;
        }
        if (hasChocolate) {
            pricePerCoffee += 2;
        }

        int price = quantity * pricePerCoffee;

        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method creates a summary text with quantity and price. Then returns the message.
     */
    private String createOrderSummary(String addName, int price, boolean addWhippedCream, boolean addChocolate) {
        String summaryText = "Name: " + addName +
                "\nAdd whipped cream? " + addWhippedCream +
                "\nAdd chocolate? " + addChocolate +
                "\nQuantity: " + quantity +
                "\nTotal: $" + price +
                "\nThank you!";
        return summaryText;
    }

}
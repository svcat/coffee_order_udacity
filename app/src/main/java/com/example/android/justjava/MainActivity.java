package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

  int quantity = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /**
   * This method is called when the + button is clicked.
   */
  public void increment(View view) {
    if (quantity < 100) {
      quantity = quantity + 1;
    } else {
      showToast("You can't order more then 100 cups of coffee");
    }
    displayQuantity(quantity);
  }

  /**
   * This method is called when the - button is clicked.
   */
  public void decrement(View view) {
    if (quantity > 1) {
      quantity = quantity - 1;
    } else {
      showToast("You can't order less then 1 cup of coffee");
    }
    displayQuantity(quantity);

  }

  public void showToast(String textOfToast) {
    Context context = getApplicationContext();
    CharSequence text = textOfToast;
    int duration = Toast.LENGTH_SHORT;

    Toast toast = Toast.makeText(context, text, duration);
    toast.show();
  }

  /**
   * This method is called when the order button is clicked.
   */
  public void submitOrder(View view) {
    //Check if Whipped Cream CheckBox was clicked
    CheckBox isWhippedCheckBoxStatus = findViewById(R.id.is_whipped_cream);
    boolean isWhipped = isWhippedCheckBoxStatus.isChecked();
    //Check if Chocolate CheckBox was clicked
    CheckBox isChocolateCheckBox = findViewById(R.id.is_chocolate);
    boolean isChocolate = isChocolateCheckBox.isChecked();

    int totalPrice = calculateTotalPrice(isWhipped, isChocolate);

    EditText customersNameEditText = findViewById(R.id.customers_name);
    String customerName = customersNameEditText.getText().toString();

    String subject = getString(R.string.subject_title, customerName);
    String orderSummery = createOrderSummery(totalPrice, isWhipped, isChocolate, customerName);
    composeEmail(subject, orderSummery);

    //displayMessage(createOrderSummery(totalPrice, isWhipped, isChocolate, customerName));

  }

  /**
   * Calculates the total price of the order.
   *
   * @param isWhipped if customer chose to add Whipped cream
   * @param isChocolate if customer chose to add Chocolate
   * @return total price
   */
  private int calculateTotalPrice(boolean isWhipped, boolean isChocolate) {
    int price = 5;
    int whipped_price = 1;
    int chocolate_price = 2;
    // add whipped_price if user wants add whipped cream
    if (isWhipped) {
      price = price + whipped_price;
    }
    // add chocolate_price if user wants add whipped cream
    if (isChocolate) {
      price = price + chocolate_price;
    }
    // total price
    return quantity * price;
  }

  /**
   * Create summary of the order.
   *
   * @param isWhippedCream is whether or not the user wants whipped cream topping
   * @param isChocolate is whether or not the user wants chocolate topping
   * @param customerName the name of the customer
   * @param totalPrice of the order
   * @return summery message of the order
   */
  private String createOrderSummery(int totalPrice, boolean isWhippedCream, boolean isChocolate,
      String customerName) {
    String priceMessage = getString(R.string.customer_name, customerName);
    priceMessage += "\n" + getString(R.string.order_summery_whipped_cream, isWhippedCream);
    priceMessage += "\n" + getString(R.string.order_summery_chocolate, isChocolate);
    priceMessage += "\n" + getString(R.string.order_summery_quantity, quantity);
    priceMessage += "\n" + getString(R.string.order_summery_total, totalPrice);
    priceMessage += "\n" + getString(R.string.thank_you);
    return priceMessage;
  }


  public void composeEmail(String subject, String text) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("*/*");
    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    intent.putExtra(Intent.EXTRA_TEXT, text);
    if (intent.resolveActivity(getPackageManager()) != null) {
      startActivity(intent);
    }
  }

  /**
   * This method displays the given quantity value on the screen.
   */
  private void displayQuantity(int notTheNumber) {
    TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
    quantityTextView.setText("" + notTheNumber);
  }

}
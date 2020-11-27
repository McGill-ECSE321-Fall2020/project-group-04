package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ListingActivity extends AppCompatActivity {
    private ArrayList<String> listings;
    private ArrayAdapter<String> listingAdapter;
    Spinner listingSpinner;
    private final HashMap<String, Integer> artworkNameToListingIDMap = new HashMap<>();
    private String username;

    /**
     * @author Viet
     * Displays all the listings information and buttons when the page is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing);
        // get username string equivalent
        username = getIntent().getStringExtra("USERNAME"); // current username
        getListings(); // display all the listings
        Spinner paymentSpinner = findViewById(R.id.payment_spinner); // the payment method spinner
        Spinner shippingSpinner = findViewById(R.id.shipping_spinner); // the delivery method spinner

        ArrayList<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("credit");
        paymentMethods.add("paypal");
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentMethods);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter); // fill the payment spinner

        ArrayList<String> shippingMethods = new ArrayList<>();
        shippingMethods.add("shipping");
        shippingMethods.add("pickup");
        ArrayAdapter<String> shippingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shippingMethods);
        shippingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shippingSpinner.setAdapter(shippingAdapter); // fill the shipping spinner

        // Display the selected selecting's information
        listingSpinner = findViewById(R.id.listing_spinner);
        listingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) { // If a listing is selected
                displayListing();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    /**
     * @author OliverStappas
     * Display the information about the selected listing.
     */
    private void displayListing() {
        String artworkName = listingSpinner.getSelectedItem().toString();
        Integer listingID = artworkNameToListingIDMap.get(artworkName);
        HttpUtils.get("/listing/" + listingID, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // Display all the listings information on the page, by getting the appropriate TextView
                    // and information from the JSON response
                    TextView title = findViewById(R.id.listing_title);
                    title.setText("Title: " + response.getJSONObject("artwork").getString("name"));

                    TextView year = findViewById(R.id.listing_year);
                    year.setText("Year Created: " + response.getJSONObject("artwork").getInt("year"));

                    TextView price = findViewById(R.id.listing_price);
                    price.setText("Price: " + response.getJSONObject("artwork").getDouble("price"));

                    TextView style = findViewById(R.id.listing_style);
                    style.setText("Art Style: " + response.getJSONObject("artwork").getString("artStyle"));

                    TextView height = findViewById(R.id.listing_height);
                    height.setText("Height(cm): " + response.getJSONObject("artwork").getDouble("height"));

                    TextView width = findViewById(R.id.listing_width);
                    width.setText("Width(cm): " + response.getJSONObject("artwork").getDouble("width"));

                    TextView weight = findViewById(R.id.listing_weight);
                    weight.setText("Weight(kg): " + response.getJSONObject("artwork").getDouble("weight"));

                    TextView sold = findViewById(R.id.listing_sold);
                    boolean available =response.getBoolean("sold");
                    String soldText = available?"Sold":"Available";
                    sold.setText("Availability: " + soldText);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *
     * @author OliverStappas*
     * Gets all the listings that are not sold and adds them to a dropdown
     */
    public void getListings() {
        HttpUtils.get("/listing", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    artworkNameToListingIDMap.clear(); // Re-determine which listings are not sold
                    listingSpinner = findViewById(R.id.listing_spinner);
                    listings = new ArrayList<>();
                    // Store the artwork name mapped to the listing ID for later reference
                    for (int i = 0; i < response.length(); i++) {
                        if (!response.getJSONObject(i).getBoolean("sold")) { // Only get unsold listings
                            artworkNameToListingIDMap.put(response.getJSONObject(i).getJSONObject("artwork").getString("name"),
                                    response.getJSONObject(i).getInt("listingID")); // Add to hashmap for later access
                            listings.add(response.getJSONObject(i).getJSONObject("artwork").getString("name")); // For dropdown
                        }
                    }
                    // Fill the listing spinner
                    listingAdapter = new ArrayAdapter<>(ListingActivity.this, android.R.layout.simple_spinner_item, listings);
                    listingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listingSpinner.setAdapter(listingAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @author OliverStappas
     * @param view
     * Logs out the user by sending them to the log in page
     *
     */
    public void logOut(View view) {
        Intent intent = new Intent(ListingActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * @author OliverStappas
     * @param view
     * Creates a transaction with the current user and the listing they're viewing when they click on the "purchase" button
     */
    public void createTransaction(View view) {
        String artworkName = listingSpinner.getSelectedItem().toString(); // artwork to purchase
        int listingID = artworkNameToListingIDMap.get(artworkName);

        Spinner paymentSpinner = findViewById(R.id.payment_spinner);
        String paymentMethod = paymentSpinner.getSelectedItem().toString(); // Get selected payment method

        Spinner shippingSpinner = findViewById(R.id.shipping_spinner);
        String deliveryMethod = shippingSpinner.getSelectedItem().toString(); // Get selected delivery method

        // Create the transaction
        HttpUtils.post("/transaction/?paymentMethod=" + paymentMethod + "&deliveryMethod=" +
                deliveryMethod + "&username=" + username + "&listingID=" + listingID, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(ListingActivity.this, "Transaction was Successful", Toast.LENGTH_SHORT).show();
                getListings();
                displayListing();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Toast.makeText(ListingActivity.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @author OliverStappas, ZachS
     * @param view
     * Goes to the user's profile based on if they are an artist or a customer
     */
    public void viewProfile(View view) {
        HttpUtils.get("/artist/name/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // redirect to artist  profile, passing the username in a parameter
                Intent intent = new Intent(ListingActivity.this, ViewArtist.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                // redirect to customer profile, passing the username in a parameter
                Intent intent = new Intent(ListingActivity.this, ViewCustomer.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }
}

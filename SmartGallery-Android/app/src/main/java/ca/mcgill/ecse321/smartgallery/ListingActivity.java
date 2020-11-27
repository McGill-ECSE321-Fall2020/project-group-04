package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
    private String error = null;
    private ArrayList<String> paymentMethods;
    private ArrayAdapter<String> paymentAdapter;
    private ArrayList<String> shippingMethods;
    private ArrayAdapter<String> shippingAdapter;
    private ArrayList<String> listings;
    private ArrayAdapter<String> listingAdapter;
    Spinner listingSpinner;
    private HashMap<String, Integer> artworkNameToListingIDMap;

    //get intent from profile
    Intent intent = getIntent();
    //get username string equivalent
    String username = intent.getStringExtra("Username");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_profile);
        getListings();
        Spinner paymentSpinner = findViewById(R.id.payment_spinner);
        Spinner shippingSpinner = findViewById(R.id.shipping_spinner);

        paymentMethods = new ArrayList<>();
        paymentMethods.add("credit");
        paymentMethods.add("paypal");
        paymentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentMethods);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);

        shippingMethods = new ArrayList<>();
        shippingMethods.add("shipping");
        shippingMethods.add("pickup");
        shippingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shippingMethods);
        shippingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shippingSpinner.setAdapter(shippingAdapter);


        listingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                displayListing();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    private void displayListing() {
        String artworkName = listingSpinner.getSelectedItem().toString();
        Integer listingID = artworkNameToListingIDMap.get(artworkName);

        error = "";
        HttpUtils.get("/listing/" + listingID, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    TextView title = findViewById(R.id.listing_title);
                    title.setText(response.getJSONObject("artwork").getString("name"));

                    TextView year = findViewById(R.id.listing_year);
                    year.setText(String.valueOf(response.getJSONObject("artwork").getInt("year")));

                    TextView price = findViewById(R.id.listing_price);
                    price.setText(String.valueOf(response.getJSONObject("artwork").getDouble("price")));

                    TextView style = findViewById(R.id.listing_style);
                    style.setText(response.getJSONObject("artwork").getString("artStyle"));

                    TextView height = findViewById(R.id.listing_height);
                    height.setText(String.valueOf(response.getJSONObject("artwork").getDouble("height")));

                    TextView width = findViewById(R.id.listing_width);
                    width.setText(String.valueOf(response.getJSONObject("artwork").getDouble("width")));

                    TextView weight = findViewById(R.id.listing_weight);
                    weight.setText(String.valueOf(response.getJSONObject("artwork").getDouble("weight")));

                    TextView sold = findViewById(R.id.listing_sold);
                    sold.setText(String.valueOf(response.getBoolean("sold")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Gets all the listings that are not sold and adds them to a dropdown
     */
    public void getListings() {
        error = "";
        HttpUtils.get("/listing", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    artworkNameToListingIDMap.clear(); // Re-determine which listings are not sold
                    listingSpinner = findViewById(R.id.listing_spinner);
                    listings = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        if (!response.getJSONObject(i).getBoolean("sold")) { // Only get unsold listings
                            artworkNameToListingIDMap.put(response.getJSONObject(i).getJSONObject("artwork").getString("name"),
                                    response.getJSONObject(i).getInt("listingID")); // Add to hashmap for later access
                            listings.add(response.getJSONObject(i).getJSONObject("artwork").getString("name")); // For dropdown
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        listingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shippingMethods);
        listingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listingSpinner.setAdapter(listingAdapter);
    }

    public void createTransaction(View view) {
        error = "";
        String artworkName = listingSpinner.getSelectedItem().toString();
        int listingID = artworkNameToListingIDMap.get(artworkName);

        Spinner paymentSpinner = view.findViewById(R.id.payment_spinner);
        String paymentMethod = paymentSpinner.getSelectedItem().toString();

        Spinner shippingSpinner = view.findViewById(R.id.shipping_spinner);
        String deliveryMethod = shippingSpinner.getSelectedItem().toString();


        HttpUtils.post("/transaction/?paymentMethod=" + paymentMethod + "&deliveryMethod=" +
                deliveryMethod + "&username=" + username + "&listingID=" + listingID, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // alert the user the transaction worked etc.
            }
        });
    }

    public void viewProfile(View view) {
        HttpUtils.get("/artist/name/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //redirect to customer profile, passing the username in a parameter
                Intent intent = new Intent(ListingActivity.this, ViewArtist.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Intent intent = new Intent(ListingActivity.this, ViewCustomer.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }
}

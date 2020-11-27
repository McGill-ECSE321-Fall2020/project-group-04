package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class TransactionActivity extends AppCompatActivity {

    private HashMap<String, Integer> transactions = new HashMap<>();
    private ArrayList<String> artworkNames;
    private ArrayAdapter transactionAdapter;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("USERNAME");
        setContentView(R.layout.transaction);
        getTransactions();
        Spinner transactionSpinner = findViewById(R.id.transaction_spinner);
        transactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                displayTransaction();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    private void displayTransaction() {
        Spinner transactionSpinner = findViewById(R.id.transaction_spinner);
        String artworkName = transactionSpinner.getSelectedItem().toString();
        Integer id = transactions.get(artworkName);
        HttpUtils.get("/transaction/" + id, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    TextView date = findViewById(R.id.transaction_date);
                    TextView payment = findViewById(R.id.transaction_payment);
                    TextView name = findViewById(R.id.transaction_name);
                    date.setText("Payment Date: " + response.getString("paymentDate"));
                    payment.setText("Payment Method: " + response.getString("paymentMethod"));
                    name.setText("Title: " + response.getJSONObject("listing").getJSONObject("artwork").getString("name"));
                    System.out.println(name.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Retrieve the transactions of a specific user
     */
    public void getTransactions() {
        HttpUtils.get("/transaction/search/username/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    transactions.clear();
                    Spinner transactionSpinner = findViewById(R.id.transaction_spinner);
                    artworkNames = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        transactions.put(response.getJSONObject(i).getJSONObject("listing").getJSONObject("artwork").getString("name"),
                                response.getJSONObject(i).getInt("transactionID")); // Add to hashmap for later access
                        artworkNames.add(response.getJSONObject(i).getJSONObject("listing").getJSONObject("artwork").getString("name")); // For dropdown
                    }
                    transactionAdapter = new ArrayAdapter<>(TransactionActivity.this, android.R.layout.simple_spinner_item, artworkNames);
                    transactionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    transactionSpinner.setAdapter(transactionAdapter);
                    displayTransaction();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Goes to a user's profile
     *
     * @param view
     */
    public void viewProfile(View view) {
        HttpUtils.get("/artist/name/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //redirect to customer profile, passing the username in a parameter
                Intent intent = new Intent(TransactionActivity.this, ViewArtist.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Intent intent = new Intent(TransactionActivity.this, ViewCustomer.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

    public void logOut(View view) {
        Intent intent = new Intent(TransactionActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

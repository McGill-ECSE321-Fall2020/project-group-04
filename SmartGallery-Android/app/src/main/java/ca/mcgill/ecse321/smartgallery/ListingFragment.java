package ca.mcgill.ecse321.smartgallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ca.mcgill.ecse321.smartgallery.HttpUtils;
import ca.mcgill.ecse321.smartgallery.R;
import cz.msebera.android.httpclient.Header;

public class ListingFragment extends Fragment {
    private String error = null;
    private ArrayList<String> paymentMethods;
    private ArrayAdapter<String> paymentAdapter;
    private ArrayList<String> shippingMethods;
    private ArrayAdapter<String> shippingAdapter;
    private ArrayList<String> listings;
    private ArrayAdapter<String> listingAdapter;
    Spinner listingSpinner;
    private HashMap<String, Integer> artworkNameToListingIDMap;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.listing_view, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner paymentSpinner = view.findViewById(R.id.payment_spinner);
        Spinner shippingSpinner = view.findViewById(R.id.shipping_spinner);

        paymentMethods = new ArrayList<>();
        paymentMethods.add("credit");
        paymentMethods.add("paypal");
        paymentAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, paymentMethods);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);

        shippingMethods = new ArrayList<>();
        shippingMethods.add("shipping");
        shippingMethods.add("pickup");
        shippingAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, shippingMethods);
        shippingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shippingSpinner.setAdapter(shippingAdapter);

        getListings(view);

        listingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                displayListing(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void displayListing(View view) {
        String artworkName = listingSpinner.getSelectedItem().toString();
        Integer listingID = artworkNameToListingIDMap.get(artworkName);

        error = "";
        HttpUtils.get("/listing/" + listingID, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    TextView title = (TextView) view.findViewById(R.id.listing_title);
                    title.setText(response.getJSONObject("artwork").getString("name"));

                    TextView year = (TextView) view.findViewById(R.id.listing_year);
                    year.setText(String.valueOf(response.getJSONObject("artwork").getInt("year")));

                    TextView price = (TextView) view.findViewById(R.id.listing_price);
                    price.setText(String.valueOf(response.getJSONObject("artwork").getDouble("price")));

                    TextView style = (TextView) view.findViewById(R.id.listing_style);
                    style.setText(response.getJSONObject("artwork").getString("artStyle"));

                    TextView height = (TextView) view.findViewById(R.id.listing_height);
                    height.setText(String.valueOf(response.getJSONObject("artwork").getDouble("height")));

                    TextView width = (TextView) view.findViewById(R.id.listing_width);
                    width.setText(String.valueOf(response.getJSONObject("artwork").getDouble("width")));

                    TextView weight = (TextView) view.findViewById(R.id.listing_weight);
                    weight.setText(String.valueOf(response.getJSONObject("artwork").getDouble("weight")));

                    TextView sold = (TextView) view.findViewById(R.id.listing_sold);
                    sold.setText(String.valueOf(response.getBoolean("sold")));

//                    TextView payment = (TextView) view.findViewById(R.id.listing_payment);
//                    //??
//
//                    TextView shipping = (TextView) view.findViewById(R.id.listing_shipping);
//                    //?

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Gets all the listings that are not sold and adds them to a dropdown
     * @param view
     */
    public void getListings(View view) {
        error = "";
        HttpUtils.get("/listing", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    try {
                        artworkNameToListingIDMap.clear(); // Re-determine which listings are not sold
                        listingSpinner = view.findViewById(R.id.listing_spinner);
                        listings = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            if (!response.getJSONObject(i).getBoolean("sold")) { // Only get unsold listings
                                artworkNameToListingIDMap.put(response.getJSONObject(i).getJSONObject("artwork").getString("name"),
                                        response.getJSONObject(i).getInt("listingID")); // Add to hashmap for later access
                                listings.add(response.getJSONObject(i).getJSONObject("artwork").getString("name")); // For dropdown
                            }
                        }
                        listingAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listings);
                        listingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        listingSpinner.setAdapter(listingAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });
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
        deliveryMethod + "&username=" + "INSERT_USERNAME_HERE" + "&listingID=" + listingID, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // alert the user the transaction worked etc.
            }
        });
    }
}

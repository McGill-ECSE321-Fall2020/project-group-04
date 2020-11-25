package ca.mcgill.ecse321.smartgallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import ca.mcgill.ecse321.smartgallery.HttpUtils;
import ca.mcgill.ecse321.smartgallery.R;
import cz.msebera.android.httpclient.Header;

public class ListingFragment extends Fragment {
    private String error = null;
    private ArrayList<String> deliveryMethods;
    private ArrayAdapter<String> deliveryAdapter;
    private ArrayList<String> shippingMethods;
    private ArrayAdapter<String> shippingAdapter;
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

        Spinner deliverySpinner = view.findViewById(R.id.delivery_spinner);
        Spinner shippingSpinner = view.findViewById(R.id.shipping_spinner);

        deliveryMethods = new ArrayList<>();
        deliveryMethods.add("credit");
        deliveryMethods.add("paypal");
        deliveryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, deliveryMethods);
        deliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliverySpinner.setAdapter(deliveryAdapter);

        shippingMethods = new ArrayList<>();
        shippingMethods.add("shipping");
        shippingMethods.add("pickup");
        shippingAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, shippingMethods);
        shippingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shippingSpinner.setAdapter(shippingAdapter);

    }


    public void getListings() {
        error = "";
        HttpUtils.get("/listing", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }
        });


    }


}

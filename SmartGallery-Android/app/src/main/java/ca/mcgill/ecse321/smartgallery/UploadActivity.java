package ca.mcgill.ecse321.smartgallery;

import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class UploadActivity extends AppCompatActivity {

    private String error = null;
    private ArrayList<String> artStyleOptions;
    private ArrayAdapter<String> styleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Add adapters to spinner lists and refresh spinner content
        Spinner styleSpinner = (Spinner) findViewById(R.id.upload_spinner);
        artStyleOptions = new ArrayList<>();
        artStyleOptions.add("realist");
        artStyleOptions.add("renaissance");
        artStyleOptions.add("surrealist");
        artStyleOptions.add("impressionist");


        styleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, artStyleOptions);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);


        refreshErrorMessage();

    }





    public void uploadArtwork(View v) {
        error = "";
         EditText artworkName = (EditText) findViewById(R.id.upload_name);
         EditText year = (EditText) findViewById(R.id.upload_year);
         EditText price = (EditText) findViewById(R.id.upload_price);
         EditText height = (EditText) findViewById(R.id.upload_height);
         EditText weight = (EditText) findViewById(R.id.upload_weight);
         EditText width = (EditText) findViewById(R.id.upload_width);
         Spinner artStyle = (Spinner) findViewById(R.id.upload_spinner);


        RequestParams rp = new RequestParams();
        rp.add("year", year.getText().toString());
        rp.add("price", price.getText().toString());
        //add art style
        rp.add("style", artStyle.getSelectedItem().toString());
        rp.add("height", height.getText().toString());
        rp.add("weight", weight.getText().toString());
        rp.add("width", width.getText().toString());
        //add artist
        rp.add("gallery", "testGallery");

        HttpUtils.post("artwork/" + artworkName.getText().toString(), rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();

            }
        });
    }


    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.upload_error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

}

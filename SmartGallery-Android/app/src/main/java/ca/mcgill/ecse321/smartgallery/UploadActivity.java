package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    private String username = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Add adapters to spinner lists and refresh spinner content
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_view);
        Spinner styleSpinner = findViewById(R.id.upload_spinner);
        artStyleOptions = new ArrayList<>();
        artStyleOptions.add("realist");
        artStyleOptions.add("renaissance");
        artStyleOptions.add("surrealist");
        artStyleOptions.add("impressionist");

        styleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, artStyleOptions);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);
        refreshErrorMessage();

        //Transition for exit button
        Button exit = findViewById(R.id.upload_exit);
        exit.setOnClickListener(v -> {
            Intent intent = new Intent(UploadActivity.this,
                    ViewArtist.class);
            intent.putExtra("Username", username);
            startActivity(intent);
        });

    }


    public void uploadArtwork(View v) {
        error = "";
        EditText artworkName = findViewById(R.id.upload_name);
        EditText year = findViewById(R.id.upload_year);
        EditText price = findViewById(R.id.upload_price);
        EditText height = findViewById(R.id.upload_height);
        EditText weight = findViewById(R.id.upload_weight);
        EditText width = findViewById(R.id.upload_width);
        Spinner artStyle = findViewById(R.id.upload_spinner);


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
        TextView tvError = findViewById(R.id.upload_error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

}

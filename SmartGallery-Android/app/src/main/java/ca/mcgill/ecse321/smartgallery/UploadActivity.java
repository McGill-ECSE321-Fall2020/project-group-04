package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import cz.msebera.android.httpclient.Header;

public class UploadActivity extends AppCompatActivity {

    //initialize fields
    //declare error string for error handling
    private String error = null;
    //arraylists for art style spinner
    private ArrayList<String> artStyleOptions;
    private ArrayAdapter<String> styleAdapter;
    //private String artworkID = "";


    //get username string equivalent
    String username;

    /**
     * @param savedInstanceState Overrides the existing onCreate() method
     *                           Initializes art style spinner options
     *                           Sets navigation from exit button to Artist profile page
     * @author Stavros Mitsoglou
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Add adapters to spinner lists and refresh spinner content
        //call super method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_view);

        //get the username from intent
        username = getIntent().getStringExtra("Username");
        //get the art style spinner form upload_view.xml
        Spinner styleSpinner = findViewById(R.id.upload_spinner);
        artStyleOptions = new ArrayList<>();
        //add art style options to spinner
        artStyleOptions.add("realist");
        artStyleOptions.add("renaissance");
        artStyleOptions.add("surrealist");
        artStyleOptions.add("impressionist");

        //set the adapter with options
        styleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, artStyleOptions);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);
        //check for errors
        refreshErrorMessage();

        //Transition for exit button
        Button exit = findViewById(R.id.upload_exit);
        exit.setOnClickListener(v -> {
            //navigate to Artist profile page
            Intent intent = new Intent(UploadActivity.this,
                    ViewArtist.class);
            intent.putExtra("Username", username);
            startActivity(intent);
        });

        Button btnUpload = (Button) findViewById(R.id.upload_button);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadAndListArtwork();
            }
        });


    }


    /**
     * @author Stavros Mitsoglou
     * <p>
     * This method takes the values of the EditExt fields in the upload_view.xml page and uploads an artwork on success.
     * Success occurs when all fields are filled with appropriate values. On failure, a message is displayed on screen through
     * a TextView component.
     **/
    public void uploadAndListArtwork() {
        //initialize error for handling
        error = "";
        //get EditText inputs as well as spinner option
        EditText artworkName = findViewById(R.id.upload_name);
        EditText year = findViewById(R.id.upload_year);
        EditText price = findViewById(R.id.upload_price);
        EditText height = findViewById(R.id.upload_height);
        EditText weight = findViewById(R.id.upload_weight);
        EditText width = findViewById(R.id.upload_width);
        Spinner artStyle = findViewById(R.id.upload_spinner);


        //initialize request parameters (all but the artwork name)
        RequestParams rp = new RequestParams();
        rp.add("year", year.getText().toString());
        rp.add("price", price.getText().toString());
        rp.add("style", artStyle.getSelectedItem().toString());
        rp.add("height", height.getText().toString());
        rp.add("weight", weight.getText().toString());
        rp.add("width", width.getText().toString());
        rp.add("artist", username);
        rp.add("gallery", "testGallery");

        HttpUtils.post("artwork/" + artworkName.getText().toString().trim(), rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                //create the listing with artwork
                createListingOnSuccess();

            }

            /*
                display error message in text box on failure of uploading artwork
                no navigation
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();

                System.out.println("Failure");

            }
        });


    }

    public void createListingOnSuccess() {

        //getArtworkIdFromJson();
        String artworkID = this.getArtworkID();
        System.out.println("calling getArtworkId " + artworkID);
        EditText price = findViewById(R.id.upload_price);
        RequestParams rp = new RequestParams();
        rp.add("price", price.getText().toString());
        rp.add("gallery", "testGallery");


        HttpUtils.post("listing/" + artworkID, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //redirect to customer profile, passing the username in a parameter
                //transition for the  upload button
                refreshErrorMessage();
                Button upload = findViewById(R.id.upload_button);
                upload.setOnClickListener(v -> {
                    //navigate to the Artist profile page
                    Intent intent = new Intent(UploadActivity.this,
                            ViewArtist.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();

                System.out.println("Failure in listing");
            }
        });


    }


    /**
     * @return String representative of the artwork ID
     */

    private String getArtworkID()
    {
        String artworkID = "";
        EditText artworkName = findViewById(R.id.upload_name);
        String artworkNameStr = artworkName.getText().toString().trim();
        int hashCode = artworkNameStr.hashCode() * username.hashCode();
        artworkID = Integer.toString(hashCode);
        System.out.println("Hashcode is " +artworkID);
        return artworkID;

    }

    /*
    private void getArtworkIdFromJson() {
        final String[] artworkID = {""};
        EditText artworkToFind = findViewById(R.id.upload_name);
        String artworkToFindString = artworkToFind.getText().toString().trim();
        HttpUtils.get("artist/name/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray artworkArray = response.getJSONArray("artworks");
                    for (int i = 0, size = artworkArray.length(); i < size; i++) {
                        JSONObject artwork = artworkArray.getJSONObject(i);

                        String artworkName = artwork.getString("name");
                        if (artworkName.equals(artworkToFindString)) {
                            int artworkIdInt = artwork.getInt("artworkID");
                            artworkID[0] = Integer.toString(artworkIdInt);
                            System.out.println("Got artwork ID");
                            System.out.println(artworkID[0]);
                            setArtworkID(artworkID[0]);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

                System.out.println("Failure in fetching artwork ID");
            }
        });


        System.out.println("return value" + artworkID[0]);

    }
*/

    /**
     * Method to obtain new error message in lieu of failed request
     */
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

    /*
    public void setArtworkID(String str) {
        this.artworkID = str;
    }

    public String getArtworkID()
    {
        return this.artworkID;
    }
*/

}

package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewArtist extends AppCompatActivity {

    //get string equivalent of username
    String ausername;
    private String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_profile);
        ausername = getIntent().getStringExtra("USERNAME");
        getArtist();

        //Bind upload button to transition
        Button upload = findViewById(R.id.upload_artwork_button);
        upload.setOnClickListener(v -> {
            Intent intent = new Intent(ViewArtist.this, UploadActivity.class);
            intent.putExtra("USERNAME", ausername);
            startActivity(intent);
        });

        //Bind button to go to listings
        Button listing = findViewById(R.id.artist_view_listing_button);
        listing.setOnClickListener(v -> {
            Intent intent = new Intent(ViewArtist.this, ListingActivity.class);
            intent.putExtra("USERNAME", ausername);
            startActivity(intent);
        });

        Button transaction = findViewById(R.id.artist_view_transaction);
        transaction.setOnClickListener(v -> {
            Intent intent = new Intent(ViewArtist.this, TransactionActivity.class);
            intent.putExtra("USERNAME", ausername);
            startActivity(intent);
        });

    }

    /**
     * Retrieves an Artist object from the backend
     */
    public void getArtist() {
        TextView username = findViewById(R.id.artist_username);
        TextView email = findViewById(R.id.artist_email);
        TextView defaultPayment = findViewById(R.id.artist_default_payment_method);
        TextView creationDate = findViewById(R.id.artist_creation_date);

        HttpUtils.get("/artist/name/" + ausername, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    username.setText("Username: " + response.getString("username"));
                    email.setText("Email: " + response.getString("email"));
                    defaultPayment.setText("Default Payment Method: " + response.getString("defaultPaymentMethod"));
                    creationDate.setText("Creation Date" + response.getString("creationDate"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error += statusCode + " Invalid Name";
            }
        });
    }

    /**
     * Shows the update password fields and buttons
     *
     * @param v
     */
    public void showPass(View v) {
        findViewById(R.id.artist_update_password_button).setVisibility(View.GONE);
        EditText oldPassword = findViewById(R.id.artist_oldpassword);
        EditText newPassword = findViewById(R.id.artist_newPassword);
        Button change = findViewById(R.id.artist_changepass_button);
        Button cancel = findViewById(R.id.artist_cancelpass_button);
        oldPassword.setVisibility(View.VISIBLE);
        newPassword.setVisibility(View.VISIBLE);
        change.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the update password fields and buttons
     *
     * @param v
     */
    public void hidePass(View v) {
        findViewById(R.id.artist_update_password_button).setVisibility(View.VISIBLE);
        EditText oldPassword = findViewById(R.id.artist_oldpassword);
        EditText newPassword = findViewById(R.id.artist_newPassword);
        Button change = findViewById(R.id.artist_changepass_button);
        Button cancel = findViewById(R.id.artist_cancelpass_button);
        oldPassword.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        change.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
    }


    /**
     * Updates a user's password
     */
    public void updatePassword(View v) {
        EditText oldPassword = findViewById(R.id.artist_oldpassword);
        EditText newPassword = findViewById(R.id.artist_newPassword);
        HttpUtils.post("/password/change/?username=" + ausername + "&oldPassword=" + oldPassword.getText().toString() + "&newPassword=" + newPassword.getText().toString(), new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if(responseString.equals("false")){
                    Toast.makeText(ViewArtist.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                }else {
                    oldPassword.setText("");
                    newPassword.setText("");
                    hidePass(v);
                    Toast.makeText(ViewArtist.this, "Update Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Shows the update email fields and buttons
     *
     * @param v
     */
    public void showEmail(View v) {
        findViewById(R.id.artist_update_email_button).setVisibility(View.GONE);
        findViewById(R.id.artist_newemail).setVisibility(View.VISIBLE);
        findViewById(R.id.artist_password).setVisibility(View.VISIBLE);
        findViewById(R.id.artist_changeemail_button).setVisibility(View.VISIBLE);
        findViewById(R.id.artist_cancelemail_button).setVisibility(View.VISIBLE);
    }

    /**
     * Hides the update email fields and buttons
     *
     * @param V
     */
    public void hideEmail(View V) {
        findViewById(R.id.artist_update_email_button).setVisibility(View.VISIBLE);
        findViewById(R.id.artist_newemail).setVisibility(View.GONE);
        findViewById(R.id.artist_password).setVisibility(View.GONE);
        findViewById(R.id.artist_changeemail_button).setVisibility(View.GONE);
        findViewById(R.id.artist_cancelemail_button).setVisibility(View.GONE);
    }

    /**
     * Updates a user's email address
     *
     * @param v
     */
    public void updateEmail(View v) {
        EditText newEmail = findViewById(R.id.artist_newemail);
        EditText password = findViewById(R.id.artist_password);
        HttpUtils.post("email/change/?username=" + ausername + "&password=" + password.getText() + "&newEmail=" + newEmail.getText(), new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                error += statusCode + "succesfully changed email";
                //Refresh info
                getArtist();
                newEmail.setText("");
                password.setText("");
                hideEmail(v);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ViewArtist.this.error += statusCode + " Invalid Password or email";
            }
        });

    }

    /**
     * Deletes the current user's account
     *
     * @param V
     */
    public void deleteAccount(View V) {

        HttpUtils.post("/artist/delete/" + ausername, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(ViewArtist.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });
    }

}

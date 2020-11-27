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
    private String ausername;

    /**
     * On creation of the artist activity, set the proper layout, as well as the onClicks.
     * @author Viet Tran
     * @param savedInstanceState
     */
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

        //Bind transaction button to go to transactions
        Button transaction = findViewById(R.id.artist_view_transaction);
        transaction.setOnClickListener(v -> {
            Intent intent = new Intent(ViewArtist.this, TransactionActivity.class);
            intent.putExtra("USERNAME", ausername);
            startActivity(intent);
        });

    }

    /**
     * Retrieves an Artist object from the backend
     * @author Viet Tran
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

            }
        });
    }

    /**
     * Shows the update password fields and buttons
     * @author Viet Tran
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
     * @author Viet Tran
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
     * @author Viet Tran
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
     * @author Viet Tran
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
     * @author Viet Tran
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
     * @author Viet Tran
     * @param v
     */
    public void updateEmail(View v) {
        EditText newEmail = findViewById(R.id.artist_newemail);
        EditText password = findViewById(R.id.artist_password);
        HttpUtils.post("email/change/?username=" + ausername + "&password=" + password.getText().toString() + "&newEmail=" + newEmail.getText().toString(), new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            //Response is a boolean
            //If the change is successful, the method will hide the fields and prompt the user
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if(responseString.equals("false")){
                    Toast.makeText(ViewArtist.this, "Invalid password or email format", Toast.LENGTH_SHORT).show();
                }else{
                    //Refresh info
                    getArtist();
                    newEmail.setText("");
                    password.setText("");
                    hideEmail(v);
                    Toast.makeText(ViewArtist.this, "Updated email", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     * Logout the current user
     * @param view
     */
    public void logOut(View view) {
        HttpUtils.post("/logout?username="+ausername, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Intent intent = new Intent(ViewArtist.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}

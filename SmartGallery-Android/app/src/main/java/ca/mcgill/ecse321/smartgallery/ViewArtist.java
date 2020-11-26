package ca.mcgill.ecse321.smartgallery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewArtist extends AppCompatActivity {
    private String ausername = "artist1";
    private String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_profile_view);
        getArtist();
    }

    /**
     * Retrieves an Artist object from the backend
     */
    public void getArtist() {
        TextView username = findViewById(R.id.artist_username);
        TextView email = findViewById(R.id.artist_email);
        TextView defaultPayment = findViewById(R.id.artist_default_payment_method);
        TextView creationDate = findViewById(R.id.artist_creation_date);

        //TODO Testing with hardcoded artist, need to change this
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
        HttpUtils.post("/password/change/?username=" + ausername + "&oldPassword=" + oldPassword.getText() + "&newPassword=" + newPassword.getText(), new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error += statusCode + "succesfully changed password";
                hidePass(v);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error += statusCode + " Invalid Password";
            }
        });
    }

    /**
     * Shows the update email fields and buttons
     *
     * @param v
     */
    public void showEmail(View v){
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
    public void hideEmail(View V){
        findViewById(R.id.artist_update_email_button).setVisibility(View.VISIBLE);
        findViewById(R.id.artist_newemail).setVisibility(View.GONE);
        findViewById(R.id.artist_password).setVisibility(View.GONE);
        findViewById(R.id.artist_changeemail_button).setVisibility(View.GONE);
        findViewById(R.id.artist_cancelemail_button).setVisibility(View.GONE);
    }

    /**
     * Updates a user's email address
     * @param V
     */
    public void updateEmail(View V) {
        EditText newEmail = findViewById(R.id.artist_newemail);
        EditText password = findViewById(R.id.artist_password);
        HttpUtils.post("/email/change/?username=" + ausername + "&password=" + password.getText() + "&newEmail=" + newEmail.getText(), new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error += statusCode + "succesfully changed password";
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error += statusCode + " Invalid Password";
            }
        });

    }

    /**
     * Deletes the current user's account
     * @param V
     */
    public void deleteAccount(View V){

        HttpUtils.post("/artist/delete/" + ausername, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error += statusCode + " Invalid Password";
            }
        });
    }

}

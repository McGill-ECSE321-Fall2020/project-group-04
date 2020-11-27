package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewCustomer extends AppCompatActivity {
    private String cusername = "";
    private String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_profile);
        cusername = getIntent().getStringExtra("USERNAME");
        getCustomer();
    }

    /**
     * Retrieves an Artist object from the backend
     */
    public void getCustomer() {
        TextView username = findViewById(R.id.customer_username);
        TextView email = findViewById(R.id.customer_email);
        TextView defaultPayment = findViewById(R.id.customer_default_payment_method);
        TextView creationDate = findViewById(R.id.customer_creation_date);

        HttpUtils.get("/customer/name/" + cusername, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    username.setText("Username: " + response.getString("username"));
                    email.setText("Email: " + response.getString("email"));
                    defaultPayment.setText("Default Payment Method: " + response.getString("defaultPaymentMethod"));
                    creationDate.setText("Creation Date: " + response.getString("creationDate"));
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
        findViewById(R.id.customer_update_password_button).setVisibility(View.GONE);
        findViewById(R.id.customer_oldpassword).setVisibility(View.VISIBLE);
        findViewById(R.id.customer_newpassword).setVisibility(View.VISIBLE);
        findViewById(R.id.customer_changepass_button).setVisibility(View.VISIBLE);
        findViewById(R.id.customer_cancelpass_button).setVisibility(View.VISIBLE);
    }

    /**
     * Hides the update password fields and buttons
     *
     * @param v
     */
    public void hidePass(View v) {
        findViewById(R.id.customer_update_password_button).setVisibility(View.VISIBLE);
        findViewById(R.id.customer_oldpassword).setVisibility(View.GONE);
        findViewById(R.id.customer_newpassword).setVisibility(View.GONE);
        findViewById(R.id.customer_changepass_button).setVisibility(View.GONE);
        findViewById(R.id.customer_cancelpass_button).setVisibility(View.GONE);
    }


    /**
     * Updates a user's password
     */
    public void updatePassword(View v) {
        EditText oldPassword = findViewById(R.id.customer_oldpassword);
        EditText newPassword = findViewById(R.id.customer_newpassword);
        HttpUtils.post("/password/change/?username=" + cusername + "&oldPassword=" + oldPassword.getText().toString() + "&newPassword=" + newPassword.getText().toString(), new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                error += statusCode + "succesfully changed password";
                oldPassword.setText("");
                newPassword.setText("");
                hidePass(v);
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ViewCustomer.this.error += statusCode + " Invalid Password";
            }


        });
    }

    /**
     * Shows the update email fields and buttons
     *
     * @param v
     */
    public void showEmail(View v){
        findViewById(R.id.customer_update_email_button).setVisibility(View.GONE);
        findViewById(R.id.customer_newemail).setVisibility(View.VISIBLE);
        findViewById(R.id.customer_password).setVisibility(View.VISIBLE);
        findViewById(R.id.customer_changeemail_button).setVisibility(View.VISIBLE);
        findViewById(R.id.customer_cancelemail_button).setVisibility(View.VISIBLE);
    }

    /**
     * Hides the update email fields and buttons
     *
     * @param V
     */
    public void hideEmail(View V){
        findViewById(R.id.customer_update_email_button).setVisibility(View.VISIBLE);
        findViewById(R.id.customer_newemail).setVisibility(View.GONE);
        findViewById(R.id.customer_password).setVisibility(View.GONE);
        findViewById(R.id.customer_changeemail_button).setVisibility(View.GONE);
        findViewById(R.id.customer_cancelemail_button).setVisibility(View.GONE);
    }

    /**
     * Updates a user's email address
     * @param v
     */
    public void updateEmail(View v) {
        EditText newEmail = findViewById(R.id.customer_newemail);
        EditText password = findViewById(R.id.customer_password);
        HttpUtils.post("email/change/?username=" + cusername + "&password=" + password.getText() + "&newEmail=" + newEmail.getText(), new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                error += statusCode + "succesfully changed email";
                //Refresh info
                getCustomer();
                newEmail.setText("");
                password.setText("");
                hideEmail(v);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ViewCustomer.this.error += statusCode + " Invalid Password or email";
            }
        });

    }

    /**
     * Deletes the current user's account
     * @param V
     */
    public void deleteAccount(View V){

        HttpUtils.post("/customer/delete/" + cusername, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(ViewCustomer.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error += statusCode + " Invalid Password";
            }
        });
    }
}

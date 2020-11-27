package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * The Registration activity. Uses the activity_registration layout file. Users can input the
 * required information to create a new account, or can redirect themselves back to the login page.
 */
public class RegistrationActivity extends AppCompatActivity {
    Button btnRegister;
    Button btnBackToLogin;

    EditText etUsername;
    EditText etPassword;
    EditText etConfirmPassword;
    EditText etEmail;
    Spinner spAccountType;
    Spinner spPaymentType;

    /**
     * On creation of the registration activity, set the proper layout, as well as the onClicks.
     * @author zsiciliani
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the layout
        setContentView(R.layout.activity_registration);

        //set the onClick methods for the Register and Back to login buttons
        btnRegister = findViewById(R.id.register_button);
        btnRegister.setOnClickListener(this::register);

        btnBackToLogin = findViewById(R.id.register_back_to_login);
        btnBackToLogin.setOnClickListener(this::goToLogin);
    }

    /**
     * Register a client for a new account. Verify that they have input valid parameters, and then
     * register them as either a customer or as an artist.
     * @author zsiciliani
     * @param view
     */
    public void register(View view) {
        //Assign each view item to its variable.
        etUsername = findViewById(R.id.register_username_input);
        etPassword = findViewById(R.id.register_password_input);
        etConfirmPassword = findViewById(R.id.register_confirm_password_input);
        etEmail = findViewById(R.id.register_email_input);
        spAccountType = findViewById(R.id.account_type_spinner);
        spPaymentType = findViewById(R.id.payment_type_spinner);

        //Read the strings input by the user in the EditTexts.
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String email = etEmail.getText().toString();
        String accountType = spAccountType.getSelectedItem().toString();
        String paymentType = spPaymentType.getSelectedItem().toString();

        //Check the registration mistakes that could be made by a user. If they made one, alert them
        //of it.
        if (username.equals("")) {
            Toast.makeText(this, getString(R.string.empty_username), Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.equals("")) {
            Toast.makeText(this, getString(R.string.empty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.equals("")) {
            Toast.makeText(this, getString(R.string.empty_email), Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 7) {
            Toast.makeText(this, getString(R.string.short_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, getString(R.string.mismatched_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!(accountType.equalsIgnoreCase(getString(R.string.type_customer))
                || accountType.equalsIgnoreCase(getString(R.string.type_artist))))
        {
            Toast.makeText(this, getString(R.string.empty_account_type), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!(paymentType.equalsIgnoreCase(getString(R.string.type_credit))
                || accountType.equalsIgnoreCase(getString(R.string.type_paypal)))) {
            Toast.makeText(this, getString(R.string.empty_payment_type), Toast.LENGTH_SHORT).show();
            return;
        }

        //Assuming all forms were properly filled out, register the user as either a Customer or an Artist.
        if (accountType.equalsIgnoreCase(getString(R.string.type_customer))) {
            registerCustomer(username, password, email, paymentType, view);
        }

        else if (accountType.equalsIgnoreCase(getString(R.string.type_artist))) {
            registerArtist(username, password, email, paymentType, view);
        }
    }

    /**
     * Register a customer for the SmartGallery by using the parameters they had input.
     * @author zsiciliani
     * @param username The customer's username
     * @param password The customer's password
     * @param email The customer's email
     * @param paymentType The customer's default payment type
     * @param view
     */
    public void registerCustomer(String username, String password, String email, String paymentType, View view) {
        HttpUtils.post("/customer/" + username + "/" + password + "/" + email + "/" + paymentType + "?smartGalleryID=123",
                new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        successfulRegistration(view);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                        unsuccessfulRegistration();
                    }
                });
    }

    /**
     * Register an artist for the SmartGallery by using the parameters they had input.
     * @author zsiciliani
     * @param username The artist's username
     * @param password The artist's password
     * @param email The artist's email
     * @param paymentType The artist's default payment type
     * @param view
     */
    public void registerArtist(String username, String password, String email, String paymentType, View view) {
        String url = "/artist/" + username + "/?password=" + password + "&email=" + email +
                "&defaultPaymentMethod=" + paymentType + "&smartGalleryID=123";
        HttpUtils.post(url, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                successfulRegistration(view);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                unsuccessfulRegistration();
            }
        });
    }

    /**
     * Upon a successful registration, congratulate the user on their new account and bring them back
     * to the login page.
     * @author zsiciliani
     * @param view
     */
    public void successfulRegistration(View view) {
        Toast.makeText(this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
        goToLogin(view);
    }

    /**
     * Upon an unsuccessful registration, ask the user to verify their email address or to choose a
     * new username.
     * @author zsiciliani
     */
    public void unsuccessfulRegistration() {
        Toast.makeText(this, getString(R.string.registration_failure), Toast.LENGTH_SHORT).show();
        etPassword.setText("");
        etConfirmPassword.setText("");
    }

    /**
     * Go back to the login page
     * @author zsiciliani
     * @param view
     */
    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
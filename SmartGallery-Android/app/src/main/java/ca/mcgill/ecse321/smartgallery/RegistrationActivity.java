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

public class RegistrationActivity extends AppCompatActivity {
    Button btnRegister;

    EditText etUsername;
    EditText etPassword;
    EditText etConfirmPassword;
    EditText etEmail;
    Spinner spAccountType;
    Spinner spPaymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegister = (Button) findViewById(R.id.register_button);
        btnRegister.setOnClickListener(this::register);
    }

    public void register(View view) {
        etUsername = (EditText) findViewById(R.id.register_username_input);
        etPassword = (EditText) findViewById(R.id.register_password_input);
        etConfirmPassword = (EditText) findViewById(R.id.register_confirm_password_input);
        etEmail = (EditText) findViewById(R.id.register_email_input);
        spAccountType = (Spinner) findViewById(R.id.account_type_spinner);
        spPaymentType = (Spinner) findViewById(R.id.payment_type_spinner);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String email = etEmail.getText().toString();
        String accountType = spAccountType.getSelectedItem().toString();
        String paymentType = spPaymentType.getSelectedItem().toString();

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
        }

        if (!(accountType.equalsIgnoreCase(getString(R.string.type_customer))
                || accountType.equalsIgnoreCase(getString(R.string.type_artist))))
        {
            Toast.makeText(this, getString(R.string.empty_account_type), Toast.LENGTH_SHORT).show();
        }

        if (!(paymentType.equalsIgnoreCase(getString(R.string.type_credit))
                || accountType.equalsIgnoreCase(getString(R.string.type_paypal)))) {
            Toast.makeText(this, getString(R.string.empty_payment_type), Toast.LENGTH_SHORT).show();
        }

        if (accountType.equalsIgnoreCase(getString(R.string.type_customer))) {
            registerCustomer(username, password, email, paymentType, view);
        }

        else if (accountType.equalsIgnoreCase(getString(R.string.type_artist))) {
            registerArtist(username, password, email, paymentType, view);
        }
    }

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

    public void registerArtist(String username, String password, String email, String paymentType, View view) {
        HttpUtils.post("/artist/" + username + "?password=" + password + "&email=" + email +
                "&defaultMethod=" + paymentType + "&smartGalleryID=123", new RequestParams(), new JsonHttpResponseHandler() {
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

    public void successfulRegistration(View view) {
        Toast.makeText(this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
        goToLogin(view);
    }

    public void unsuccessfulRegistration() {
        Toast.makeText(this, getString(R.string.registration_failure), Toast.LENGTH_SHORT).show();
        etPassword.setText("");
        etConfirmPassword.setText("");
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
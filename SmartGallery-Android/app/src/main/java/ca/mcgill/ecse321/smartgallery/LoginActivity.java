package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnRegister;

    EditText etUsername;
    EditText etPassword;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnRegister = (Button) findViewById(R.id.goto_register_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRegistration(view);
            }
        });
    }

    public void gotoRegistration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void login() {

        //Get the username and password input by the user
        etUsername = (EditText) findViewById(R.id.login_username_input);
        etPassword = (EditText) findViewById(R.id.login_password_input);
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        //Check that neither field is empty
        if (username.equals("")) {
            Toast.makeText(this, getString(R.string.empty_username), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(this, getString(R.string.empty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        //Attempt logging in
        HttpUtils.post("/login/?username=" + username + "&password=" + password, new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                //Redirect to profile
                successfulLogin();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(LoginActivity.this, getString(R.string.login_warning), Toast.LENGTH_SHORT).show();
                etUsername.setText("");
                etPassword.setText("");
                System.out.println(statusCode);
            }
        });
    }

    public void successfulLogin() {
        System.out.println("We have a success!");
        Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();

        //If login was successful, the profile exists. Check if it is an artist, and if so, redirect to artist profile
        //page. If not, they must be a customer, so redirect to customer page.
        HttpUtils.get("/artist/name/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //redirect to customer profile, passing the username in a parameter
                Intent intent = new Intent(LoginActivity.this, ViewArtist.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Intent intent = new Intent(LoginActivity.this, ViewCustomer.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });


    }
}

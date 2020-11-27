package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * The Login activity. Uses the activity_login layout file. Allows the user to login, or to redirect
 * themselves to the registration activity.
 */
public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnRegister;

    EditText etUsername;
    EditText etPassword;
    String username;
    String password;

    /**
     * On creation of the login activity, set the proper layout, as well as the onClicks.
     * @author zsiciliani
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createGalleries();

        btnLogin = findViewById(R.id.login_button);
        btnLogin.setOnClickListener(view -> login());

        btnRegister = findViewById(R.id.goto_register_button);
        btnRegister.setOnClickListener(this::gotoRegistration);
    }

    /**
     * Go to the registration activity.
     * @author zsiciliani
     * @param view
     */
    public void gotoRegistration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * Verify that the user has input valid credentials. If they have, log them in and
     * redirect them to their profile page.
     * @author zsiciliani
     */
    public void login() {

        //Get the username and password input by the user
        etUsername = findViewById(R.id.login_username_input);
        etPassword = findViewById(R.id.login_password_input);
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
        HttpUtils.post("/login/?username=" + username + "&password=" + password, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //A failure will not occur in the situation. When incorrect credentials are received, rather
                //than throwing an exception, the login backend method will simply return false. This is addressed
                //in the onSuccess method.
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //If false is returned, an incorrect username or password was typed. Inform the
                //user of this and empty the text fields.
                if (responseString.equals("false")) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_warning), Toast.LENGTH_SHORT).show();
                    etUsername.setText("");
                    etPassword.setText("");
                } else {
                    //If their username and password match, log them in.
                    Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                    //Redirect to profile
                    successfulLogin();
                }

            }


        });
    }

    /**
     * Upon a successful login, inform the customer that they have been logged in, and redirect to the
     * correct profile page based on whether they are a customer or an artist.
     * @author zsiciliani
     */
    public void successfulLogin() {
        Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();

        //If login was successful, the profile exists. Check if it is an artist, and if so, redirect to artist profile
        //page. If not, they must be a customer, so redirect to customer page.
        HttpUtils.get("/artist/name/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //redirect to an artist profile, passing the username in the intent.
                Intent intent = new Intent(LoginActivity.this, ViewArtist.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                //redirect to a customer profile, passing the username in the intent.
                Intent intent = new Intent(LoginActivity.this, ViewCustomer.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

    /**
     * Attempt to create a new SmartGallery and Gallery. If they already exist, the post method
     * will fail, and nothing needs to be done. If not, they will be created.
     */
    public void createGalleries() {
        HttpUtils.post("/smartGallery/123" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {

            }
        });

        HttpUtils.post("/gallery/123/testGallery/" + username, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {

            }
        });

    }
}

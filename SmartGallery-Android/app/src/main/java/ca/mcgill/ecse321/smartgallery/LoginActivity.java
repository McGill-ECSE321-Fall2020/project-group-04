package ca.mcgill.ecse321.smartgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnRegister;

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
        final EditText etUsername = (EditText) findViewById(R.id.login_username_input);
        final EditText etPassword = (EditText) findViewById(R.id.login_password_input);
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        //Check that neither field is empty
        if (username == "") {
            Toast.makeText(this, getString(R.string.empty_username), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password == "") {
            Toast.makeText(this, getString(R.string.empty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        //Attempt logging in
        HttpUtils.post("/login/?username=" + username + "&password=" + password, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                //Redirect to profile
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(LoginActivity.this, getString(R.string.login_warning), Toast.LENGTH_SHORT).show();
                etUsername.setText("");
                etPassword.setText("");
            }
        });
    }
}

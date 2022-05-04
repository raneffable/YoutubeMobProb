package com.example.therealproject;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button login, signup;
    String sendUrl = "https://mobileprog2019.000webhostapp.com/checkLogin.php";
    RequestQueue req;
    int success;
    boolean lgn = false;
    String TAG_SUCCESS = "success";
    String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    String s = "";
    public void goToYoutube(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goSignUp(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.TxtUsername);
        password = findViewById(R.id.TxtPassword);
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSignup);

        signup.setOnClickListener(view -> { goSignUp(); });

        req = Volley.newRequestQueue(getApplicationContext());

        login.setOnClickListener(view -> { send_Data(); });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
    }


    public void send_Data(){
        try{
            URL url = new URL("https://mobileprog2019.000webhostapp.com/dummy.php");
            URLConnection ucon = url.openConnection();
            InputStream in = ucon.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            while(data != -1){
                char current = (char) data;
                s = s + current;
                data = isw.read();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        StringRequest request = new StringRequest(Request.Method.POST, sendUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    success = jobj.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();
                        lgn = true;
                    } else {
                        Toast.makeText(Login.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                        s = "";
                    }
                    if(lgn){
                        lgn = false;
                        s = "";
                        goToYoutube();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Login.this, "Error Occurred " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            public Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("Username",username.getText().toString());
                params.put("Password",password.getText().toString());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        req.add(request);
    }
}
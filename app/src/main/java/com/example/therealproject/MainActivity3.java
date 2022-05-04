package com.example.therealproject;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


public class MainActivity3 extends AppCompatActivity {

    EditText username, password,nameInput,passInput;
    TextView editInput;
    String tesuser,tespass;
    Button signup,login;
    String sendUrl = "https://mobprob.000webhostapp.com/insertData.php";
    RequestQueue requestQueue;
    int success;
    String TAG_SUCESS = "success";
    String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        username = findViewById(R.id.txtUsername1);
        password = findViewById(R.id.txtPassword1);
        signup = findViewById(R.id.buttonSignUp);
        login = findViewById(R.id.buttonLogin);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        login.setOnClickListener(view -> {
//                openActivity1();
            ClickedConnectivity();

        });


        signup.setOnClickListener(view -> sendData());

    }
    public void openActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void ClickedConnectivity()
    {
        editInput = findViewById(R.id.txtResult1);
        nameInput = findViewById(R.id.txtUsername1);
        passInput =  findViewById(R.id.txtPassword1);

        tesuser = nameInput.getText().toString();
        tespass= passInput.getText().toString();

        if(tesuser.equals("bapak")&& tespass.equals("kaw"))
        {

            String s="";
            try
            {
                URL url = new URL("https://mobprob.000webhostapp.com/tes1.php?a=abc");
                URLConnection ucon = url.openConnection();
                InputStream in = ucon.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();
                while(data != -1)
                {
                    char current = (char) data;
                    s += current;
                    data = isw.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            editInput.setText(s);

        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "Invalid User ID/Password, or Disabled Account!",Toast.LENGTH_LONG).show();
            editInput.setText("Gabisa wekk :p");
        }
    }
    public void sendData ()
    {
        StringRequest request = new StringRequest(Request.Method.POST, sendUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    success = jobj.getInt(TAG_SUCESS);
                    if (success == 1) {
                        Toast.makeText(MainActivity3.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity3.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity3.this, "Error Occurred " +e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity3.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Username", username.getText().toString());
                params.put("Password", password.getText().toString());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        requestQueue.add(request);
    }
}
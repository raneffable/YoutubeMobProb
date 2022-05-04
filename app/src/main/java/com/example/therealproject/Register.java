package com.example.therealproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText regUsername, regPass, regConfPass;
    Button btnSignUp;
    String sendUrl1 = "https://mobileprog2019.000webhostapp.com/insertData.php";
    RequestQueue req;
    int success;
    boolean lgn = false;
    String TAG_SUCCESS = "success";
    String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUsername = findViewById(R.id.editTextTextPersonName);
        regPass = findViewById(R.id.editTextTextPassword);
        regConfPass = findViewById(R.id.editTextTextPassword2);
        btnSignUp = findViewById(R.id.button2);

        req = Volley.newRequestQueue(getApplicationContext());

        btnSignUp.setOnClickListener(view -> {
            if (regPass.getText().toString().equals(regConfPass.getText().toString()))
                send_Data();
            else
                Toast.makeText(Register.this, "Password and Confirm Password not same", Toast.LENGTH_SHORT).show();
        });
    }

    public void send_Data(){
        StringRequest request = new StringRequest(Request.Method.POST, sendUrl1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    success = jobj.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Toast.makeText(Register.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                        lgn = true;
                    } else {
                        Toast.makeText(Register.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Register.this, "Error Occurred " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            public Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("Username",regUsername.getText().toString());
                params.put("Password",regPass.getText().toString());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        req.add(request);
    }
}
package com.example.therealproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {
    private Button btn;
    private EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        View overlay= findViewById(R.id.layout);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_FULLSCREEN);

        btn = findViewById(R.id.btnSearch);
        txt = findViewById(R.id.TextInput);
        VideoView v= findViewById(R.id.v);
        MediaController mediaController = new MediaController(this);
        v.setMediaController(mediaController);
        mediaController.setAnchorView(v);
        Uri uri = Uri.parse("http://techslides.com/demos/sample-videos/small.mp4");

        v.setVideoURI(uri);
        v.requestFocus();
        v.start();

        ImageView img = findViewById(R.id.imageView);
        ImageButton btnHome = findViewById(R.id.imageButton3);
        btnHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                alert("Home Button");
                Log.i("UIElementsPracticeLog","OnClick: Button: Home Pressed");
            }
        });
        ImageButton btnList = findViewById(R.id.imageButton2);
        btnList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                alert("List Button");
                Log.i("UIElementsPracticeLog","OnClick: Button: List Pressed");
            }
        });
        ImageButton btnYoutube= findViewById(R.id.imageButton);
        btnYoutube.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                alert("Youtube Button");
                Log.i("UIElementsPracticeLog","OnClick: Button: Youtube Pressed");
            }
        });
    }

    public void openActivity2(View view)
    {
        Intent intent = new Intent(this, MainActivity2.class);
        String search = txt.getText().toString();
        intent.putExtra("keysearch",search);
        startActivity(intent);
    }

    public void alert(String msg)
    {
        AlertDialog dlg = new AlertDialog.Builder(MainActivity.this).
        setTitle("Message")
                .setMessage(msg)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialogInterface,int i ){
                dialogInterface.dismiss();
            }
            }).create();

        dlg.show();
    }

}
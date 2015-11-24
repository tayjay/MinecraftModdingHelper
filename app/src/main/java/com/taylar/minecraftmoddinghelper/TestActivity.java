package com.taylar.minecraftmoddinghelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        int fileID = R.raw.test2;

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        //WebView mbrowser = (WebView) findViewById(R.id.webView); //get the WebView from the layout XML
        //TextView tv = (TextView) findViewById(R.id.textView);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.verticalLayout);
        FileHelper.readFileAndBuild(linearLayout,getResources().openRawResource(fileID));
        //tv.setText(Html.fromHtml(FileHelper.readFileClean(getResources().openRawResource(fileID))));
        //File file = new File("/assets/"+"index.html");
        //String filepath = "file:///android_assets/res/assets/index.html";
        //mbrowser.setWebViewClient(new WebViewClient());
        //mbrowser.loadUrl("http://www.github.com/"); //set the HTML
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v)
    {
        Button button = (Button) v;
        button.setText("Hello");
        Toast.makeText(this,"Is the id",Toast.LENGTH_LONG);
    }


}

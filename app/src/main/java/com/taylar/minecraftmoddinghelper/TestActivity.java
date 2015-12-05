package com.taylar.minecraftmoddinghelper;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
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

    private static TestActivity instance;

    public TestActivity() {
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.verticalLayout);
        String fileName = "";
        if(intent.getStringExtra(basic_menu.FILE_NAME) != null)
        {
            fileName = intent.getStringExtra(basic_menu.FILE_NAME);
        }
        FileHelper.readFileAndBuild(linearLayout,getResources().openRawResource(
                getResources().getIdentifier("raw/"+fileName,
                        "raw", getPackageName())));

/*
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        //WebView mbrowser = (WebView) findViewById(R.id.webView); //get the WebView from the layout XML
        //TextView tv = (TextView) findViewById(R.id.textView);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.verticalLayout);
        //FileHelper.readFileAndBuild(linearLayout,getResources().openRawResource(fileID));

        FileHelper.readFileAndBuild(linearLayout,getResources().openRawResource(
                getResources().getIdentifier("raw/test2",
                        "raw", getPackageName())));


        //tv.setText(Html.fromHtml(FileHelper.readFileClean(getResources().openRawResource(fileID))));
        //File file = new File("/assets/"+"index.html");
        //String filepath = "file:///android_assets/res/assets/index.html";
        //mbrowser.setWebViewClient(new WebViewClient());
        //mbrowser.loadUrl("http://www.github.com/"); //set the HTML
        */
    }

    public static TestActivity getInstance()
    {
        return instance;
    }

    public LinearLayout getVerticalLayout()
    {
        return (LinearLayout) findViewById(R.id.verticalLayout);
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
        /*
        Button button = (Button) v;
        button.setText("Hello");
        Toast.makeText(this,"Is the id",Toast.LENGTH_LONG);
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
        */
        int id = 32;
        ImageView imageView = (ImageView) findViewById(id);

        if(imageView.getPaddingBottom()<0)
        {
            imageView.setPadding(0,0,0,0);
        }
        else
        {
            imageView.setPadding(0,0,0,-200000);
        }
    }

    public void homeClick(View v)
    {
        Button button = (Button) v;
        //button.setText(button.getHint());
        try {
            //LinearLayout linearLayout = (LinearLayout) button.getParent();
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.verticalLayout);
            ScrollView scrollView = (ScrollView) linearLayout.getParent();
            //linearLayout.removeViews(0, 100);
            linearLayout.removeAllViews();
            FileHelper.readFileAndBuild(linearLayout,linearLayout.getContext().getResources().openRawResource(
                    linearLayout.getContext().getResources().getIdentifier("raw/reference",
                            "raw", linearLayout.getContext().getPackageName())));
            scrollView.scrollTo(0, 0);

        } catch (NullPointerException e) {

        } catch (Resources.NotFoundException e) {

        }
    }

    public void closeClick(View v)
    {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.verticalLayout);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    try {
                        //LinearLayout linearLayout = (LinearLayout) button.getParent();
                        ScrollView scrollView = (ScrollView) linearLayout.getParent();
                        Button button = (Button) linearLayout.findViewById(9001);
                        if(button!=null) {
                            if(!button.getHint().equals("home")) {
                                //linearLayout.removeViews(0, 100);
                                linearLayout.removeAllViews();
                                FileHelper.readFileAndBuild(linearLayout, linearLayout.getContext().getResources().openRawResource(
                                        linearLayout.getContext().getResources().getIdentifier("raw/" + button.getHint(),
                                                "raw", linearLayout.getContext().getPackageName())));
                                scrollView.scrollTo(0, 0);
                            }else
                            {
                                return super.onKeyDown(keyCode, event);
                            }
                        }else
                        {
                            return super.onKeyDown(keyCode, event);
                        }

                    } catch (NullPointerException e) {

                    } catch (Resources.NotFoundException e) {

                    }
                    return true;


            }

        }
        return super.onKeyDown(keyCode, event);
    }


}

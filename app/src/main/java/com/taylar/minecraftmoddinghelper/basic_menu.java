package com.taylar.minecraftmoddinghelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class basic_menu extends AppCompatActivity {
    public final static String FILE_NAME = "com.taylar.minecraftmoddinghelper.MESSAGE";
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_menu);
        sharedPreferences = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = sharedPreferences.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_basic_menu, menu);
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



    public void menuClick(View v) {
        Button button = (Button) v;
        if(button.getId()==R.id.start_learning) {
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra(FILE_NAME, button.getHint());
            startActivity(intent);
        }
        else if(button.getId()==R.id.find_page)
        {
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra(FILE_NAME, "reference");
            startActivity(intent);
        }
        else if(button.getId()==R.id.continue_learning)
        {
            Intent intent = new Intent(this, TestActivity.class);
            String page = sharedPreferences.getString("currentPage","getting_started");
            intent.putExtra(FILE_NAME, page);
            startActivity(intent);
        }
    }



}

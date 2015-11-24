package com.taylar.minecraftmoddinghelper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by tayjm_000 on 2015-11-23.
 */
public class OnClickHandler extends Activity implements View.OnClickListener {


    public static OnClickHandler INSTANCE = new OnClickHandler();
    public static String url;
    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        button.setText(v.getId() + "");
        Toast.makeText(v.getContext(), "Is the id", Toast.LENGTH_SHORT);
        Intent intent = new Intent(v.getContext(), WebActivity.class);
        intent.putExtra("url",button.getHint());
        v.getContext().startActivity(intent);
    }
}
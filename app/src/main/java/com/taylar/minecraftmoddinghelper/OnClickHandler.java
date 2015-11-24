package com.taylar.minecraftmoddinghelper;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by tayjm_000 on 2015-11-23.
 */
public class OnClickHandler implements View.OnClickListener {
    public static OnClickHandler INSTANCE = new OnClickHandler();
    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        button.setText(v.getId()+"");
        Toast.makeText(v.getContext(), "Is the id", Toast.LENGTH_SHORT);
    }
}
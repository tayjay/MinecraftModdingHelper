package com.taylar.minecraftmoddinghelper;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Created by tayjm_000 on 2015-11-23.
 */
public class OnClickHandler extends Activity {
    public static class ClickListeners {

        //public static OnClickHandler INSTANCE = new OnClickHandler();
        public static View.OnClickListener pageClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        public static View.OnClickListener webClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                //button.setText(v.getId() + "");
                Toast.makeText(v.getContext(), "Is the id", Toast.LENGTH_SHORT);
                Intent intent = new Intent(v.getContext(), WebActivity.class);
                intent.putExtra("url", button.getHint());
                v.getContext().startActivity(intent);
            }
        };

        public static String url;

        public static View.OnClickListener pageClick(View v) {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    button.setText(button.getHint());
                    try {
                        LinearLayout linearLayout = (LinearLayout) button.getParent();
                        ScrollView scrollView = (ScrollView) linearLayout.getParent();
                        //linearLayout.removeViews(0, 100);
                        linearLayout.removeAllViews();
                        FileHelper.readFileAndBuild(linearLayout, linearLayout.getContext().getResources().openRawResource(
                                linearLayout.getContext().getResources().getIdentifier("raw/" + button.getHint().toString(),
                                        "raw", linearLayout.getContext().getPackageName())));
                        scrollView.scrollTo(0, 0);
                        basic_menu.preferenceEditor.putString("currentPage", button.getHint().toString());
                        basic_menu.preferenceEditor.commit();

                    } catch (NullPointerException e) {

                    } catch (Resources.NotFoundException e) {

                    }

                }
            };
            return onClickListener;
        }

        public static View.OnClickListener webClick(View v) {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    //button.setText(v.getId() + "");
                    Toast.makeText(v.getContext(), "Is the id", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.putExtra("url", button.getHint());
                    v.getContext().startActivity(intent);
                }
            };
            return onClickListener;
        }

        public static View.OnClickListener showClick(View v)
        {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    LinearLayout linearLayout = (LinearLayout) button.getParent();
                    int id = Integer.parseInt((String)button.getHint());
                    ImageView imageView = (ImageView) linearLayout.findViewById(id);
                    if(imageView.getPaddingBottom()<0)
                    {
                        imageView.setPadding(0,0,0,0);
                    }
                    else
                    {
                        imageView.setPadding(-2000,0,0,-200000);
                    }
                }
            };
            return onClickListener;

        }


    }
}
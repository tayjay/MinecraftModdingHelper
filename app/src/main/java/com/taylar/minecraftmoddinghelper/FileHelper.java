package com.taylar.minecraftmoddinghelper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import junit.framework.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjm_000 on 2015-11-23.
 */
public class FileHelper {

    /**
     * Read the inputStream as a plain text file.
     * @param inputStream   Text file
     * @return              The final string that is built.
     */
    public static String readFileClean(InputStream inputStream)
    {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                total.append(line);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return total.toString();
    }

    /**
     * Read the file and evaluate the tags.
     * @param view          View to reference
     * @param inputStream   File to read from
     */
    public static void readFileAndBuild(View view,InputStream inputStream)
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try
        {
            while((line = bufferedReader.readLine())!=null)
            {
                if(line!=null) {

                    if (getCommand(line) == Commands.BUTTON)
                    {
                        makeButton(view,line);
                    }
                    else if(getCommand(line)==Commands.TEXTVIEW)
                    {
                        makeTextView(view,line,bufferedReader);
                    }
                    else if(getCommand(line)==Commands.WEBVIEW)
                    {
                        makeWebView(view,line);
                    }
                    else if(getCommand(line)==Commands.BACKGROUND)
                    {
                        setBackground(view,line);
                    }
                    else if(getCommand(line)==Commands.IMAGE)
                    {
                        makeImage(view,line);
                    }
                }
            }

        }
        catch(IOException e)
        {
            return;
        }




        /*
        LinearLayout linearLayout = (LinearLayout) view;
        if(view instanceof LinearLayout)
        {
            linearLayout = (LinearLayout) view;
        }

        linearLayout.addView(new Button(view.getContext()));
        TextView tv = new TextView(view.getContext());
        tv.setText("THIS IS DYNAMICALLY ADDED!!!");
        linearLayout.addView(tv);
        */



        return;
    }




    /**
     * Determine if there is something to interpret
     * @param line  String to check
     * @return      The id of command to run from the Commands class.
     */
    private static int getCommand(String line)
    {
        if(line.contains("<TextView"))
        {
            return Commands.TEXTVIEW;
        }
        else if(line.contains("<Button"))
        {
            return Commands.BUTTON;
        }
        else if(line.contains("<WebView"))
        {
            return Commands.WEBVIEW;
        }
        else if(line.contains("<Background"))
        {
            return Commands.BACKGROUND;
        }
        else if(line.contains("<Image"))
        {
            return Commands.IMAGE;
        }
        return 0;
    }

    private static void setBackground(View view, String line) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.verticalLayout);
        linearLayout.setBackgroundColor(Color.GRAY);
    }

    private static void makeImage(View view, String line) {
        String[] arguments = getArguments(line);
        ImageView imageView = new ImageView(view.getContext());
        LinearLayout linearLayout = (LinearLayout) view;
        //imageView.setPadding(-10,-100,-10,-100);
        imageView.setImageResource(Reference.ImageID.get(""));
        for(String argument: arguments)
        {
            String argumentName= argument.substring(0,argument.indexOf("="));
            String argumentValue= argument.substring(argument.indexOf("=")+1);
            argumentValue.replace("\"", "");

            if(argumentName.equals("src"))
            {
                imageView.setImageResource(Reference.ImageID.get(argumentValue));
            }
        }
        //imageView.setPadding((int)(imageView.getScaleX()*-0.7),(int)(imageView.getScaleY()),(int)(imageView.getScaleX()*-0.7),(int)(imageView.getScaleY()*-0.7));

        //imageView.setImageResource(R.drawable.squares);

        linearLayout.addView(imageView);
    }

    /**
     * Make a button on the screen from the page's text file.
     * <Button id=buttonIdNumber, text=DisplayText, onClick=onClick/>
     * @param v     View to reference
     * @param line  The String to be evaluated and make the button
     */
    public static void makeButton(View v, String line)
    {
        String[] arguments = getArguments(line);
        Button button = new Button(v.getContext());
        button.setMaxHeight(20);
        button.animate();
        LinearLayout linearLayout = (LinearLayout) v;
        for(String argument:arguments)
        {
            String argumentName= argument.substring(0,argument.indexOf("="));
            String argumentValue= argument.substring(argument.indexOf("=")+1);
            argumentValue.replace("\"", "");
            //button.setText(argumentValue);
            if(argumentName.toString().equals("text"))
            {
                button.setText(argumentValue);
            }
            else if(argumentName.toString().equals("id"))
            {
                button.setId(Integer.parseInt(argumentValue));
            }
            else if(argumentName.toString().equals("onClick"))
            {
                button.setOnClickListener(OnClickHandler.INSTANCE);
                button.setHint(argumentValue);
                //OnClickHandler.url=argumentValue;
            }
            else if(argumentName.toString().equals("textColor"))
            {
                button.setTextColor(Color.parseColor(argumentValue));
            }
            else if(argumentName.toString().equals("backgroundColor"))
            {
                button.setBackgroundColor(Color.parseColor(argumentValue));
            }

        }
        linearLayout.addView(button);
    }


    /**
     * Make a TextView to add to the screen. It will be formatted to the text tag's specifications.
     *
     * <TextView id=TextViewIdInt, fontSize=FontSizeDouble/>
     * @param v                 View to reference.
     * @param line              String to evaluate
     * @param bufferedReader    Reader to populate the textview
     */
    public static void makeTextView(View v,String line, BufferedReader bufferedReader)
    {
        TextView tv = new TextView(v.getContext());
        StringBuilder total = new StringBuilder();
        LinearLayout linearLayout = (LinearLayout) v;
        String[] arguments = getArguments(line);
        String argumentName;
        String argumentValue;
        final int BUFFERSIZE = 100000;

        for(String argument:arguments)
        {
            argumentName= argument.substring(0,argument.indexOf("="));
            argumentValue= argument.substring(argument.indexOf("=")+1);
            argumentValue.replace("\"", "");
            if(argumentName.equals("id"))
            {
                tv.setId(Integer.parseInt(argumentValue));
            }
            else if(argumentName.equals("fontSize"))
            {
                tv.setTextSize(Float.parseFloat(argumentValue));
            }
        }

        while(bufferedReader!=null) {
            try {
                bufferedReader.mark(BUFFERSIZE);
                String line1 = bufferedReader.readLine();
                if (line1 != null && getCommand(line1) == 0) {
                    total.append(line1);
                } else {
                    bufferedReader.reset();
                    break;
                }
            } catch (IOException e) {
                return;
            }
        }
        tv.setText(Html.fromHtml(total.toString()));
        linearLayout.addView(tv);


    }

    public static void makeWebView(View v, String line)
    {
        WebView wv = new WebView(v.getContext());
        LinearLayout linearLayout = (LinearLayout) v;
        String[] arguments = getArguments(line);
        String argumentName;
        String argumentValue;
        for(String argument:arguments)
        {
            argumentName = argument.substring(0,argument.indexOf("="));
            argumentValue= argument.substring(argument.indexOf("=")+1);
            if(argumentName.equals("id"))
            {
                wv.setId(Integer.parseInt(argumentValue));
            }
            else if(argumentValue.equals("url"))
            {
                wv.setWebViewClient(new WebViewClient());
                wv.loadUrl(argumentValue);
            }
        }
        //linearLayout.removeAllViews();
        linearLayout.addView(wv);
    }


    /**
     *
     * @param line  Expression to get arguments from
     * @return
     */
    public static String[] getArguments(String line)
    {
        String temp1;
        temp1 = line.replace("<TextView ","");
        line = temp1;
        temp1 = line.replace("<Button ","");
        line = temp1;
        temp1 = line.replace("<WebView ","");
        line = temp1;
        temp1 = line.replace("/>","");
        line = temp1;
        temp1 = line.replace("<Image ","");
        line = temp1;
        String[] array = line.split(",");
        /* Clean up the elements of the Array */
        for(int i=0;i<array.length;i++)
        {
            /* Remove any leading spaces */
            if(array[i].charAt(0)==' ') {
                for (int j = 0; j < array[i].length(); j++) {
                    if(array[i].charAt(j)==' ')
                    {
                        String temp = array[i].replaceFirst(" ", "");
                        array[i] = temp;

                    }
                    else
                    {
                        break;
                    }
                }

            }
            else
            {

            }
            array[i].replace("<TextView ","");
            array[i].replace("<Button ", "");
            array[i].replace("<WebView ", "");
            array[i].replace("Image ","");
            array[i].replace("/>", "");
            array[i].replaceAll("\"", "");
        }
        return array;
    }

    public final class Commands
    {
        public static final int NULL=0;
        public static final int NEWLINE=1;
        public static final int TAB=2;
        public static final int TEXTVIEW=3;
        public static final int BUTTON=4;
        public static final int WEBVIEW=5;
        public static final int BACKGROUND=6;
        public static final int IMAGE=7;
    }
}


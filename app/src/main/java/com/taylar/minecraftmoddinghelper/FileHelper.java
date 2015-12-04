package com.taylar.minecraftmoddinghelper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import junit.framework.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        LinearLayout linearLayout = (LinearLayout) view;
        linearLayout.removeAllViews();
        try
        {
            while((line = bufferedReader.readLine())!=null)
            {
                if(line!=null) {
                    int command = getCommand(line);
                    if (command == Commands.BUTTON)
                    {
                        makeButton(view,line);
                    }
                    else if(command==Commands.TEXTVIEW)
                    {
                        makeTextView(view,line,bufferedReader);
                    }
                    else if(command==Commands.WEBVIEW)
                    {
                        makeWebView(view,line);
                    }
                    else if(command==Commands.BACKGROUND)
                    {
                        setBackground(view,line);
                    }
                    else if(command==Commands.IMAGE)
                    {
                        makeImage(view,line);
                    }
                    else if(command==Commands.TITLE)
                    {
                        setTitle(view,line);
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

    public static String[] getAllInputStreamInRaw(Context context)
    {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("raw/");
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        File folder = new File("android.resource://com.taylar.minecraftmoddinghelper/raw/");
        File[] filesInFolder = folder.listFiles();
        /*
        InputStream[] inputStreams = new InputStream[filesInFolder.length];
        for(int i = 0;i<filesInFolder.length;i++)
        {
            try {
                inputStreams[i] = new FileInputStream(filesInFolder[i]);
            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        */
        return files;
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
        else if(line.contains("<Title"))
        {
            return Commands.TITLE;
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
        Drawable img;
        int scrWidth  = TestActivity.getInstance().getWindowManager().getDefaultDisplay().getWidth();
        int scrHeight = TestActivity.getInstance().getWindowManager().getDefaultDisplay().getHeight();
        //imageView.setPadding(-10,-100,-10,-100);
        imageView.setImageResource(Reference.ImageID.get(""));
        for(String argument: arguments)
        {
            String argumentName= argument.substring(0,argument.indexOf("="));
            String argumentValue= argument.substring(argument.indexOf("=")+1);
            argumentValue.replace("\"", "");

            if(argumentName.equals("id"))
            {
                imageView.setId(Integer.parseInt(argumentValue));
            }
            else if(argumentName.equals("src"))
            {
                img = linearLayout.getContext().getResources().getDrawable(linearLayout.getContext().getResources().getIdentifier("drawable/" + argumentValue, "drawable", linearLayout.getContext().getPackageName()), linearLayout.getContext().getTheme());
                //imageView.getContext().getResources().getIdentifier("drawable/"+argumentValue,
                        //"drawable", linearLayout.getContext().getPackageName());
                imageView.setImageDrawable(img);
                imageView.setPadding(0, (int)((scrHeight-img.getMinimumHeight())*-0.1),0,(int)((scrHeight-img.getMinimumHeight())*-0.1));//-0.2 y scale
            }
        }
        //imageView.setPadding((int)(imageView.getScaleX()*-0.7),(int)(imageView.getScaleY()),(int)(imageView.getScaleX()*-0.7),(int)(imageView.getScaleY()*-0.7));

        //imageView.setImageResource(R.drawable.squares);

        imageView.setVisibility(View.VISIBLE);
        imageView.setVerticalScrollBarEnabled(true);
        imageView.canScrollVertically(2);
        imageView.setPadding(-2000, -2000, -2000, -2000);
        imageView.setCropToPadding(true);
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
        //button.animate();
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
                //button.setOnClickListener(OnClickHandler.INSTANCE.pageClick(v));
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
            else if(argumentName.equals("type"))
            {
                if(argumentValue.equals("web"))
                {
                    button.setOnClickListener(OnClickHandler.ClickListeners.webClick(v));
                }
                else if(argumentValue.equals("page"))
                {
                    button.setOnClickListener(OnClickHandler.ClickListeners.pageClick(v));
                }
                else if(argumentValue.equals("show"))
                {
                    button.setOnClickListener(OnClickHandler.ClickListeners.showClick(v));
                }
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
            try {
                argumentName = argument.substring(0, argument.indexOf("="));
                argumentValue = argument.substring(argument.indexOf("=") + 1);
                argumentValue.replace("\"", "");
                if (argumentName.equals("id")) {
                    tv.setId(Integer.parseInt(argumentValue));
                } else if (argumentName.equals("fontSize")) {
                    tv.setTextSize(Float.parseFloat(argumentValue));
                }
            }catch (StringIndexOutOfBoundsException e)
            {

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

    public static void setTitle(View v, String line)
    {
        LinearLayout linearLayout = (LinearLayout) v;
        ScrollView scrollView = (ScrollView) linearLayout.getParent();
        LinearLayout parent = (LinearLayout) scrollView.getParent();
        TextView titleBar = (TextView) parent.findViewById(R.id.titleBar);
        String[] arguments = getArguments(line);
        String argumentName;
        String argumentValue;
        for(String argument: arguments)
        {
            argumentName = argument.substring(0,argument.indexOf("="));
            argumentValue= argument.substring(argument.indexOf("=")+1);
            if(argumentName.equals("text"))
            {
                titleBar.setText(argumentValue);
            }
        }
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
        temp1 = line.replace("<Title ","");
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
        public static final int TITLE=8;

    }
}


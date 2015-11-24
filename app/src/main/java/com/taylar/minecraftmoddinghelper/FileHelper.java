package com.taylar.minecraftmoddinghelper;

import android.text.Html;
import android.view.View;
import android.widget.Button;
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

    public static String readFileAndBuild(View view,InputStream inputStream)
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
                }
            }

        }
        catch(IOException e)
        {
            return null;
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



        return null;
    }

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
        return 0;
    }

    public static void makeButton(View v, String line)
    {
        String[] arguments = getArguments(line);
        Button button = new Button(v.getContext());
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
            }

        }
        linearLayout.addView(button);
    }


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



    public static String[] getArguments(String line)
    {
        String temp1;
        temp1 = line.replace("<TextView ","");
        line = temp1;
        temp1 = line.replace("<Button ","");
        line = temp1;
        temp1 = line.replace("/>","");
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
    }
}


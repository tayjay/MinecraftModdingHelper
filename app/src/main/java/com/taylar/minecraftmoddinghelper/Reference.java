package com.taylar.minecraftmoddinghelper;

/**
 * Created by tayjm_000 on 2015-11-23.
 */
public class Reference {
    public static class ImageID
    {
        public static final int squares = R.drawable.squares;
        public static final int non = R.drawable.non;
        public static final int codeTest = R.drawable.code_test;

        public static int get(String name)
        {
            if(name.equals("squares"))
            {
                return squares;
            }
            else if(name.equals("codeTest"))
            {
                return codeTest;
            }

            return non;
        }
    }


}

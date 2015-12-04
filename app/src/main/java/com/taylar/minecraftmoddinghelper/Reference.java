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

    public static class pageID
    {
        public static final int pageBlank = 0;
        public static final int page1 = R.raw.getting_started;
        public static final int page2 = R.raw.test2;
        public static int currentPage=0;

        public static int getPage(String pageName)
        {
            if(pageName.equals("getting_started"))
            {
                return R.raw.getting_started;
            }
            else if(pageName.equals("test2"))
            {
                return R.raw.test2;
            }
            return 0;
        }
    }


}

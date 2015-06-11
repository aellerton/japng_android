package net.ellerton.japng.android.demo.index;

import android.content.Context;
import android.util.Log;

import net.ellerton.japng.android.demo.util.IoHelp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Processes PNG Suite items into the main selection list
 */
public class IndexItems {
    public static List<PngSuiteIndexItem> items;// = parsePngSuite();

    /*
     From pngsuite site:

     filename:                               g04i2c08.png
                                             || ||||
     test feature (in this case gamma) ------+| ||||
     parameter of test (here gamma-value) ----+ ||||
     interlaced or non-interlaced --------------+|||
     color-type (numerical) ---------------------+||
     color-type (descriptive) --------------------+|
     bit-depth ------------------------------------+

     */
    static String filenamePatternString = "(\\w)(\\w\\w)(i|n)([02346][gcpa])(\\d\\d)";//\\.png";
    static Pattern filenamePattern = Pattern.compile(filenamePatternString);
    //static Pattern linePattern = Pattern.compile("(\\w{8})\\s+\\-\\s+(\\w.*)");
    static Pattern linePattern = Pattern.compile("("+filenamePatternString+")\\s+\\-\\s+(\\w.*)");

    //@TargetApi(Build.VERSION_CODES.KITKAT)
    public static List<PngSuiteIndexItem> initialisePngSuite(Context context) throws IOException {
        if (items != null) {
            return items;
        }

        List<PngSuiteIndexItem> i = new ArrayList<>(175);
        InputStream is = null;
        try {
            is = context.getAssets().open("pngsuite/index.txt");
            for (String line : IoHelp.getLines(is)) {
                Matcher matcher = linePattern.matcher(line);
                if (matcher.matches()) {
                    String basename = matcher.group(1);
                    String category = matcher.group(2);
                    String description = matcher.group(7);

                    //i.add(new PngSuiteIndexItem(basename, description, category));
                } else {
                    Log.e("startup", "Failed to match <" + line + ">");

                }
            }
        } catch (Exception e) {
            Log.e(IndexItems.class.toString(), "Failed to initialise png suite", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // NOP
                }
            }
        }
        return items = i;
        //return items;
    }

//    static {
//        items = new ArrayList<>(175);
//
//    }

    public static Iterable<? extends PngSuiteIndexItem> category(String categoryName) {
        List<PngSuiteIndexItem> sub = new ArrayList<>(10);
        for (PngSuiteIndexItem item : items) {
            if (item.basename.startsWith("bas")) {
                sub.add(item);
            }
        }
        return sub;
    }
}

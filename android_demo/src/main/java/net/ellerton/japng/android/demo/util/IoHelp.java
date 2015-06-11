package net.ellerton.japng.android.demo.util;

import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aellerton on 29/05/2015.
 */
public class IoHelp {



    public static Iterable<String> getLines(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<String> list = new ArrayList<String>(128);
        String s;
        try {
            while (true) {
                s = reader.readLine();
                if (s == null) {
                    break;
                }
                s = s.trim();
                if (s.isEmpty() || s.startsWith("#")) {
                    continue; // skip blank and comment lines;
                }
                list.add(s);
            }
        } catch (IOException e) {
            try {
                reader.close();
            } catch (IOException e1) {
                // NOP
            }
        }
        return list;
    }

    public static InputStream openStream(Context context, Uri uri) throws IOException {
        if (null != uri && null != uri.getScheme() && uri.getScheme().equals("file") && uri.getPath().startsWith("/android_asset/")) {
            String path = uri.getPath().replace("/android_asset/", ""); // TODO: should be at start only
            return context.getAssets().open(path);
        } else {
            return context.getContentResolver().openInputStream(uri);
        }
    }
}

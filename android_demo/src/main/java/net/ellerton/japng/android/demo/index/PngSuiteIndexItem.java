package net.ellerton.japng.android.demo.index;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import net.ellerton.japng.android.demo.util.IntentInflater;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by aellerton on 29/05/2015.
 */
public class PngSuiteIndexItem implements IntentInflater {
    /*
     basn0g01 - black & white
    basn0g02 - 2 bit (4 level) grayscale
    basn0g04 - 4 bit (16 level) grayscale
    basn0g08 - 8 bit (256 level) grayscale
    basn0g16 - 16 bit (64k level) grayscale
    basn2c08 - 3x8 bits rgb color
    basn2c16 - 3x16 bits rgb color
    basn3p01 - 1 bit (2 color) paletted
    basn3p02 - 2 bit (4 color) paletted
    basn3p04 - 4 bit (16 color) paletted
    basn3p08 - 8 bit (256 color) paletted
    basn4a08 - 8 bit grayscale + 8 bit alpha-channel
    basn4a16 - 16 bit grayscale + 16 bit alpha-channel
    basn6a08 - 3x8 bits rgb color + 8 bit alpha-channel
    basn6a16 - 3x16 bits rgb color + 16 bit alpha-channel
     */
    public final String basename;
//    public final String kind;
//    public final String depth;
    public final String description;
    //public final String category;

    public PngSuiteIndexItem(String basename, String description) {
        this.basename = basename;
        this.description = description;
        //this.category = category;
    }

    @Override
    public String toString() {
        //return basename+": "+description+"";
        return description;
    }

    @Override
    public Intent inflate(Intent intent) {
        //try {
            intent.putExtra(Intent.EXTRA_STREAM,
                    //new URI("file", "android_assets", "pngsuite/"+basename+".png")
                    //Uri.fromParts("file", "/android_assets/pngsuite/" + basename + ".png", null)
                    Uri.parse("file:///android_asset/pngsuite/"+basename+".png")
                    //"file:///android_asset/pngsuite/"+basename+".png"
            );
            intent.putExtra("description", description);
            intent.setType("image/png");
            intent.setAction(Intent.ACTION_SEND);
            // Note:
            // > res2.getSchemeSpecificPart
            // String = /android_assets/pngsuite/abc.png
            // > res2.getScheme
            // String = file

//        } catch (URISyntaxException e) {
//            // TODO warn e.printStackTrace();
//            Log.w(getClass().toString(), "URI creation failed: "+e.getMessage());
//        }
        return intent;
    }
}

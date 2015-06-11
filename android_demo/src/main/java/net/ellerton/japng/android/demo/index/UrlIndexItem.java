package net.ellerton.japng.android.demo.index;

import android.content.Intent;
import android.net.Uri;

import net.ellerton.japng.android.demo.util.IntentInflater;

/**
 * Created by aellerton on 5/06/2015.
 */
public class UrlIndexItem implements IntentInflater {

    private Uri uri;
    private String description;
    private String type;

    public UrlIndexItem(Uri uri, String description) {
        this(uri, description, "image/png");
    }

    public UrlIndexItem(Uri uri, String description, String type) {
        this.uri = uri;
        this.description = description;
        this.type = type;
    }

    @Override
    public String toString() {
        return description;
    }

    public Uri getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Intent inflate(Intent intent) {
        intent.putExtra(Intent.EXTRA_STREAM,
                //Uri.fromParts("file", "/android_assets/"+this.path, null) // or "asset"??
//                    Uri.parse("android.resource:///assets/"+this.path)
                //Uri.parse(this.path) //"file:///android_asset/" + this.path)
                uri
        );
        intent.putExtra("description", this.description);
        //intent.setType(this.type);
        intent.setAction(Intent.ACTION_SEND);
        return intent;
    }
}

package net.ellerton.japng.android.demo.index;

import android.content.Intent;
import android.net.Uri;

import net.ellerton.japng.android.demo.util.IntentInflater;

//import java.net.URI;
//import java.net.URISyntaxException;

/**
 * Created by aellerton on 29/05/2015.
 */
public class ResourceIndexItem implements IntentInflater {
    private String path;
    private String description;
    private String type;

    public ResourceIndexItem(String path, String description) {
        this(path, description, "image/png");
    }

    public ResourceIndexItem(String path, String description, String type) {
        this.path = path;
        this.description = description;
        this.type = type;
    }

    @Override
    public String toString() {
        return description;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Intent inflate(Intent intent) {
        //try {
            intent.putExtra(Intent.EXTRA_STREAM,
                    //Uri.fromParts("file", "/android_assets/"+this.path, null) // or "asset"??
//                    Uri.parse("android.resource:///assets/"+this.path)
                    Uri.parse("file:///android_asset/"+this.path)
            );
            intent.putExtra("description", this.description);
            intent.setType(this.type);
            intent.setAction(Intent.ACTION_SEND);

//        } catch (URISyntaxException e) {
//            // TODO warn e.printStackTrace();
//            Log.w(getClass().toString(), "URI creation failed: " + e.getMessage());
        //}
        return intent;
    }
}

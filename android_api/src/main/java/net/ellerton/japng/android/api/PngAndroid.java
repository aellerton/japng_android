package net.ellerton.japng.android.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import net.ellerton.japng.argb8888.Argb8888Bitmap;
import net.ellerton.japng.argb8888.Argb8888Processor;
import net.ellerton.japng.error.PngException;
import net.ellerton.japng.reader.DefaultPngChunkReader;
import net.ellerton.japng.reader.PngReadHelper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Convenience functions to load PNGs for Android.
 */
public class PngAndroid {
    public static Bitmap toBitmap(Argb8888Bitmap src) {
        int offset=0;
        int stride=src.width;
        return Bitmap.createBitmap(src.getPixelArray(), offset, stride, src.width, src.height, Bitmap.Config.ARGB_8888);
    }

    public static Drawable readDrawable(Context context, InputStream is) throws PngException {
        Argb8888Processor<Drawable> processor = new Argb8888Processor<Drawable>(new PngViewBuilder(context));
        return PngReadHelper.read(is, new DefaultPngChunkReader<Drawable>(processor));
    }

    public static Drawable readDrawable(Context context, int id) throws PngException, IOException {
        //Bitmap image = BitmapFactory.decodeResource(context.getResources(), resourceId);

        final TypedValue value = new TypedValue();
        InputStream is = null;
        try {
            return readDrawable(context, context.getResources().openRawResource(id, value));
//            Argb8888Processor<Drawable> processor = new Argb8888Processor<Drawable>(new PngViewBuilder(context));
//            return PngReadHelper.read(is, new DefaultPngChunkReader<Drawable>(processor));

        } finally {
            try {
                if (is!=null) {
                    is.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
//        try (InputStream is = context.getResources().openRawResource(id, value)) {
//            Argb8888Processor<Drawable> processor = new Argb8888Processor<Drawable>(new PngViewBuilder(context));
//            return PngReadHelper.read(is, new DefaultPngChunkReader<Drawable>(processor));
//              return readDrawable(context, is);
//        }
    }
}

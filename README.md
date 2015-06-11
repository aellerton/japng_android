# ``japng_android``: Android library for compositing animated PNG files into Drawable objects.

## TL;DR

The [japng](https://github.com/aellerton/japng) library can process PNG and APNG files, including load all frames and
animation information, but the ``japng_android`` library is needed to compose the frames into an Android ``Drawable``
for use in an Android app.

## Simple Usage

    Drawable d = PngAndroid.readDrawable(this, R.drawable.rotating_logo);
    ImageView iv = (ImageView)findViewById(R.id.view_render_image);
    iv.setImageDrawable(drawable);
    if (drawable instanceof AnimationDrawable) {
        ((AnimationDrawable)drawable).start();
    }


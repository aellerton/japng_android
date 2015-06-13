# ``japng_android``: Android library for compositing animated PNG files into Drawable objects.

## TL;DR

If you have animated PNG (APNG) files that you want to display as animations using a ``Drawable`` in an ``ImageView``, this is the library for you.

The underlying PNG processing is done by [japng](https://github.com/aellerton/japng), while ``japng_android`` composites the frames using a ``Canvas`` and produces a final ``Drawable`` to use in a ``View``.

## Simple Usage

    Drawable d = PngAndroid.readDrawable(this, R.drawable.rotating_logo);
    ImageView iv = (ImageView)findViewById(R.id.view_render_image);
    iv.setImageDrawable(drawable);
    if (drawable instanceof AnimationDrawable) {
        ((AnimationDrawable)drawable).start();
    }


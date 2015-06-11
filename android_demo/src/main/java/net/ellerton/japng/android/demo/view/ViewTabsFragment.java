/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.ellerton.japng.android.demo.view;

import android.app.Activity;
import android.app.Fragment;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import net.ellerton.japng.android.api.PngAndroid;
import net.ellerton.japng.android.demo.PngViewActivity;
import net.ellerton.japng.android.demo.R;
import net.ellerton.japng.android.demo.common.SlidingTabLayout;
import net.ellerton.japng.argb8888.Argb8888BitmapSequence;
import net.ellerton.japng.error.PngException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A basic sample which shows how to use {@link SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class ViewTabsFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabs:Fragment";

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    private RequestListener reporter = new RequestListener<Uri, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
            System.out.println("ExceptionXXX");
            if (null==e) {
                System.out.println("No exception!?");

            } else {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            System.out.println("resource ready ok apparently: "+resource.getBounds());
            return false; // let glide contineu
        }
    };
    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of {@link PngViewTabAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        final Activity activity = getActivity();
        final PngViewActivity viewActivity = (activity instanceof PngViewActivity) ? (PngViewActivity)activity : null;
        final Uri uri = viewActivity == null ? null : viewActivity.loadUri;
        Drawable composedAnimation = null;
        Argb8888BitmapSequence frameSequence = null;
        String loadError = null;

        try {
            composedAnimation = viewActivity.getComposedAnimation(uri);
            frameSequence = viewActivity.getBitmapSequence(uri);
        } catch (PngException e) {
            loadError = e.getMessage();
        } catch (IOException e) {
            loadError = e.getMessage();
        }

        mViewPager.setAdapter(new PngViewTabAdapter(composedAnimation, frameSequence, loadError));

//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "Uh-oh: "+e.getMessage(), Toast.LENGTH_LONG);
//            return;
//        }
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class PngViewTabAdapter extends PagerAdapter {

        final Activity activity = getActivity();
        final PngViewActivity viewActivity = (activity instanceof PngViewActivity) ? (PngViewActivity)activity : null;
        final Uri uri = viewActivity == null ? null : viewActivity.loadUri;
        final Drawable drawable;
        final Argb8888BitmapSequence seq;
        final String loadError;

        public PngViewTabAdapter(Drawable drawable, Argb8888BitmapSequence seq, String loadError) {
            this.drawable = drawable;
            this.seq = seq;
            this.loadError = loadError;
        }

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 4;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Composed";
                case 1:
                    return "Info";
                case 2:
                    return "Frames";
                case 3:
                    return "Glide";
                //case 4:
                //    return "Android";
                default:
                    return "Item " + (position + 1); // should never happen
            }
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View tabContent;
            ImageView iv;
            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "] start...");
            switch (position) {
                case 0:
                    if (null==drawable) {
                        tabContent = makeErrorMessage(loadError);
                    } else {
                        tabContent = activity.getLayoutInflater().inflate(R.layout.view_render_item, container, false);
                        iv = (ImageView)tabContent.findViewById(R.id.view_render_image);
                        //Drawable drawable = PngAndroid.readStream(activity, IoHelp.openStream(activity, uri));
                        //Drawable drawable = viewActivity.getComposedAnimation(uri);
                        iv.setImageDrawable(drawable);
                        if (drawable instanceof AnimationDrawable) {
                            ((AnimationDrawable)drawable).start();
                        }
                    }

                    //iv.setBackgroundColor(0xffff0000); // red

//                    try {
//                    } catch (Exception e) {
//                        Log.e(LOG_TAG, "japng failed: "+e.getMessage(), e);
////                        System.out.println("XXX failed japng...");
////                        e.printStackTrace();
//                    }
                    break;
                case 1:
//                    FragmentManager manager = getFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    Fragment fragment = new ViewInfoFragment();
//                    transaction.replace(R.layout.fragment_view_info, fragment);
//                    transaction.commit();
//                    tabContent = fragment.getView();

//                    if ("New Layout".equals(link)) {
//                        fragment = new Fragment1();
//                        transaction.replace(R.id.detailFragment, fragment);
//                        transaction.commit();
//                    }
//                    final Argb8888BitmapSequence seq;
//                    try {
//                        seq = viewActivity.getBitmapSequence(uri);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    if (null == seq) {
                        tabContent = makeErrorMessage(loadError);
                    } else {
                        tabContent = activity.getLayoutInflater().inflate(R.layout.fragment_view_info, container, false);
                        GridView gv = (GridView) tabContent.findViewById(R.id.viewInfoGrid);
                        gv.setAdapter(new InfoGridAdapter(seq));
                        //tabContent = new ViewInfoFragment();
                    }
                    break;
                case 2:
                    if (null == seq) {
                        tabContent = makeErrorMessage(loadError);
                    } else {
                        ListView lv = new ListView(viewActivity);
                        lv.setAdapter(new FrameListAdapter(seq, activity));
                        tabContent = lv;
                    }

//                    //try {
//                        //final Argb8888BitmapSequence seq = viewActivity.getBitmapSequence(uri);
//                        if (seq == null || seq.getAnimationFrames().isEmpty()) {
//                            TextView tv = new TextView(activity);
//                            tv.setText("No frames");
//                            tabContent = tv;
//                        } else {
//
//                            final List<Argb8888BitmapSequence.Frame> frames = seq.getAnimationFrames();
//                            ListView lv = new ListView(viewActivity);
//                            lv.setAdapter(new FrameListAdapter(frames, activity));
//                            tabContent = lv;
//                        }


//                    } catch (Exception e) {
//                        Log.e(LOG_TAG, "japng sequence failed: " + e.getMessage(), e);
//                        TextView tv = new TextView(activity);
//                        tv.setText("japng sequence failed: " + e.getMessage());
//                        tabContent = tv;
//                    }
                    break;
                case 3:
                    //System.out.println("Glide load of "+uri+" ...");
                    tabContent = activity.getLayoutInflater().inflate(R.layout.view_render_item, container, false);
                    iv = (ImageView)tabContent.findViewById(R.id.view_render_image);
                    //iv.setBackgroundColor(0xffff0000);
                    Glide.with(activity).load(uri).listener(reporter).into(iv);
                    //System.out.println("Glide load of " + uri + " KICKED OFF");
                    break;
                default:
                    TextView tv = new TextView(getActivity());
                    tv.setText("This is item "+position);
                    tabContent = tv;

            }
//            // Inflate a new layout from our resources
//            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
//                    container, false);
//            // Add the newly created View to the ViewPager
//            container.addView(view);
//
//            // Retrieve a TextView from the inflated View, and update it's text
//            TextView title = (TextView) view.findViewById(R.id.item_title);
//            title.setText(String.valueOf(position + 1));

            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

            container.addView(tabContent);
//            return view;
            return tabContent;
        }

        private View makeErrorMessage(String message) {
            if (null == message) {
                message = "Unknown error";
            }
            TextView tv = new TextView(activity);
            tv.setText(message);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            return tv;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }

        private class InfoGridAdapter implements ListAdapter {

            final Argb8888BitmapSequence sequence;
            final List<String> items;

            public InfoGridAdapter(Argb8888BitmapSequence sequence) {
                this.sequence = sequence;
                this.items = makeItems(sequence);
            }

            private List<String> makeItems(Argb8888BitmapSequence sequence) {
                List<String> list = new ArrayList<>(8);
                list.add("PNG Type");
                list.add(String.format("%d (%s)", sequence.header.colourType.code, sequence.header.colourType.name));
                list.add("Width");
                list.add(String.format("%d", sequence.header.width));
                list.add("Height");
                list.add(String.format("%d", sequence.header.height));
                list.add("Interlaced");
                list.add(sequence.header.isInterlaced() ? "Yes" : "No");
                list.add("Bit Depth");
                list.add(String.format("%d", sequence.header.bitDepth));
                list.add("Bits-per-pixel");
                list.add(String.format("%d", sequence.header.bitsPerPixel));
                list.add("Bytes-per-row");
                list.add(String.format("%d", sequence.header.bytesPerRow));
                list.add("Animated?");
                list.add(sequence.hasAnimation() ? "Yes" : "No");
                if (sequence.hasAnimation()) {
                    list.add("Default Image part of animation?");
                    list.add(sequence.hasDefaultImage() ? "Yes":"No");
                    list.add("Frames");
                    list.add(""+sequence.getAnimationControl().numFrames);
                    list.add("Repeats");
                    list.add(sequence.getAnimationControl().loopForever() ? "Forever": ""+sequence.getAnimationControl().numPlays);
                }
                return list;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int position) {
                return String.format("getItem %d", position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(getActivity());
                if (position < items.size()) {
                    tv.setText(items.get(position));
                } else {
                    tv.setText(String.format("View Item %d", position));
                }
                if (position % 2 == 1) {
                    tv.setTextAppearance(getActivity(), android.R.style.TextAppearance_Material_Subhead);
                }
                return tv;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        }

        private class FrameListAdapter implements ListAdapter {
            private final Argb8888BitmapSequence seq;
            private final Activity activity;
            private final List<Object> items;

            public FrameListAdapter(Argb8888BitmapSequence seq, Activity activity) {
                this.seq = seq;
                this.activity = activity;
                this.items = itemise(seq);
            }

            public List<Object> itemise(Argb8888BitmapSequence seq) {
                int capacity = seq.getAnimationFrames() == null ? 1: seq.getAnimationFrames().size() * 2;
                List<Object> i = new ArrayList<>(capacity);

                if (seq.hasAnimation()) {
                    if (seq.hasDefaultImage()) {
                        i.add(String.format("Default Image (outside animation): %d x %d", seq.defaultImage.width, seq.defaultImage.height));
                        i.add(PngAndroid.toBitmap(seq.defaultImage));
                    }
                    i.add(String.format("Animation with %d %s", seq.getAnimationFrames().size(), seq.getAnimationFrames().size()==1? "frame" :"frames"));
                    for (Argb8888BitmapSequence.Frame frame : seq.getAnimationFrames()) {
                        i.add(String.format("%d x %d (%d, %d) for %dms dispose=%s, blend=%s",
                                //frame.control.sequenceNumber,
                                frame.control.width, frame.control.height,
                                frame.control.xOffset, frame.control.yOffset,
                                frame.control.getDelayMilliseconds(),
                                frame.control.disposeOp == 0 ? "None" : frame.control.disposeOp == 1 ? "Background" : "Previous",
                                frame.control.blendOp == 0 ? "Source (copy)" : "Over (blend)"
                                ));
                        i.add(PngAndroid.toBitmap(frame.bitmap));
                    }
                } else {
                    if (seq.hasDefaultImage()) {
                        i.add(String.format("Default Image (no animation): %d x %d", seq.defaultImage.width, seq.defaultImage.height));
                        i.add(PngAndroid.toBitmap(seq.defaultImage));
                    } else {
                        i.add("No animation, no default image");
                    }
                }
                return i;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Object i = items.get(position);
                if (i instanceof Bitmap) {
                    ImageView iv = new ImageView(activity);
                    iv.setImageBitmap((Bitmap)i);
                    return iv;
                } else {
                    View v = activity.getLayoutInflater().inflate(R.layout.frame_heading, null);
                    TextView tv = (TextView)v.findViewById(R.id.view_frame_heading);//new TextView(activity);
                    tv.setText(i.toString());
                    //tv.setGravity(Gravity.CENTER_VERTICAL);
                    return v; //tv
                }
//
//                Argb8888BitmapSequence.Frame frame = frames.get(position / 2);
//                if (position %2 == 0) {
//                    // info
//                    TextView tv = new TextView(activity);
//                    tv.setText(String.format("#%d, %d x %d, %d milliseconds",
//                            frame.control.sequenceNumber, frame.control.width, frame.control.height, frame.control.getDelayMilliseconds()));
//                    return tv;
//                }
//                ImageView iv = new ImageView(activity);
//                iv.setImageBitmap(PngAndroid.toBitmap(frame.bitmap));
//                return iv;
            }

            @Override
            public int getItemViewType(int position) {
                return items.get(position) instanceof Bitmap ? 0 : 1;
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        }
    }
}

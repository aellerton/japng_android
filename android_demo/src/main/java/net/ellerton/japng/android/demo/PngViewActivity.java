package net.ellerton.japng.android.demo;

import java.io.IOException;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
//import android.support.v13.app.FragmentPagerAdapter;
////import android.support.v4.view.ViewPager;
//import android.support.v4.app.FragmentTransaction;
////import android.support.v7.app.ActionBarActivity;
////import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;
////import android.support.v4.app.FragmentManager;
////import android.support.v4.app.FragmentTransaction;
////import android.support.v4.app.FragmentPagerAdapter;
////import android.support.v4.view.ViewPager;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import net.ellerton.japng.Png;
import net.ellerton.japng.android.api.PngAndroid;
import net.ellerton.japng.android.demo.util.IoHelp;
import net.ellerton.japng.android.demo.view.ViewTabsFragment;
import net.ellerton.japng.argb8888.Argb8888BitmapSequence;
import net.ellerton.japng.error.PngException;


public class PngViewActivity extends ActionBarActivity {
    public Uri loadUri; //implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    //SectionsPagerAdapter sectionsPagerAdapter;

    TextView status;
    private Uri cachedUri = null;
    private Drawable composedAnimation = null;
    private Argb8888BitmapSequence bitmapSequence = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_png_view);
        //setupTabs();
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            ViewTabsFragment fragment = new ViewTabsFragment();
            transaction.replace(R.id.view_tabs_fragment, fragment);
            transaction.commit();
        }

        status = (TextView)findViewById(R.id.view_status_text);
        processIntent();
    }

    private void processIntent() {
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            //if (type.startsWith("image/")) {
                processSendIntent(intent); // Handle single image being sent
//            } else {
//                Toast.makeText(this, "Unhandled send: " + type, Toast.LENGTH_LONG).show();
//            }
        } else {
            //Toast.makeText(this, "Unhandled action: "+action, Toast.LENGTH_LONG).show();
            status.setText("Unhandled action ["+action+"]");
        }
    }

    void processSendIntent(Intent intent) {
        Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (uri == null) {
            Toast.makeText(this, "send: no URI?", Toast.LENGTH_LONG).show();
        } else {
            // Update UI to reflect image being shared
//            ImageView imageView = (ImageView) findViewById(R.id.glideImageView);
//            Glide.with(this).load(uri).into(imageView);
            //this.loadUri = uri = Uri.parse("file:///android_asset/pngsuite/basn0g01.png");
            this.loadUri = uri;// = Uri.parse("file:///android_asset/other/rotating_earth.gif");

            GridView infoGrid = (GridView)findViewById(R.id.viewInfoGrid);

            Log.i("view", "Uri: "+uri);
            String info=uri.toString();
            //Toast.makeText(this, info, Toast.LENGTH_LONG).show();
            status.setText(info);
        }
    }

    private void setupTabs() {

    }

    /*
    private void setupTabs() {
        // Set up the action bar.
        final ActionBar actionBar = getActionBar(); //getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for section of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < sectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(sectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_png_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
/*
        switch(id) {
            case R.id.action_bg_none:
                ViewSettings.setBackgroundNone();
                break;
            case R.id.action_bg_white:
                ViewSettings.setBackground(Color.WHITE);
                break;
            case R.id.action_bg_grey:
                ViewSettings.setBackground(Color.GRAY);
                break;
            case R.id.action_bg_black:
                ViewSettings.setBackground(Color.BLACK);
                break;
            // TODO: complete
            case R.id.action_delay_none:
                System.out.println("TODO delay none");
                break;
            case R.id.action_delay_2:
                System.out.println("TODO delay 2");
                break;
            case R.id.action_delay_5:
                System.out.println("TODO delay 5");
                break;

            default:
                // NOP
        }
*/
        return super.onOptionsItemSelected(item);
    }

    public Drawable getComposedAnimation(Uri uri) throws IOException, PngException {
        if (uri != null && !uri.equals(this.cachedUri) || this.composedAnimation == null) {
            this.composedAnimation = PngAndroid.readDrawable(this, IoHelp.openStream(this, uri));
            //this.composedAnimation = PngAndroid.readDrawable(this, R.drawable.png_suite_logo); // just testing
            this.cachedUri = uri;
        }
        return this.composedAnimation;
    }

    public Argb8888BitmapSequence getBitmapSequence(Uri uri) throws IOException, PngException {
        if (!uri.equals(this.cachedUri) || this.bitmapSequence == null) {
            this.bitmapSequence = Png.readArgb8888BitmapSequence(IoHelp.openStream(this, uri));
            this.cachedUri = uri;
        }
        return this.bitmapSequence;
    }


    /*
    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }*/


    //    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        // When the given tab is selected, switch to the corresponding page in
//        // the ViewPager.
//        viewPager.setCurrentItem(tab.getPosition());
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    /*
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new ViewRenderFragment();
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1);//.toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2);//.toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3);//.toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4);//.toUpperCase(l);
                case 4:
                    return getString(R.string.title_section5);//.toUpperCase(l);
//                case 2:
//                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
    */

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_png_view, container, false);
            return rootView;
        }
    }

}

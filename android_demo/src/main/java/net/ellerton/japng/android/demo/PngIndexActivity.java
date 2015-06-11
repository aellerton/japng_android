package net.ellerton.japng.android.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import net.ellerton.japng.android.api.PngAndroid;
import net.ellerton.japng.android.demo.index.IndexExpandableListAdapter;
import net.ellerton.japng.android.demo.index.ResourceIndexItem;
import net.ellerton.japng.android.demo.index.PngSuiteIndexItem;
import net.ellerton.japng.android.demo.index.IndexItems;
import net.ellerton.japng.android.demo.index.UrlIndexItem;
import net.ellerton.japng.android.demo.util.IntentInflater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PngIndexActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_png_index);

        try {
            IndexItems.initialisePngSuite(this);//getApplicationContext());
        } catch (IOException e) {
            Toast.makeText(this, "Failed to initialise: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.indexScrollView);

        // setting list adapter
        listView.setAdapter(makeIndexListAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(getClass().toString(), "here");
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Object item = v.getTag(R.id.index_item_object);
                if (item != null && item instanceof IntentInflater) {
                    IntentInflater inflater = (IntentInflater)item;
                    Log.i(getClass().toString(), "click item: "+item);
                    Intent intent = new Intent(PngIndexActivity.this, PngViewActivity.class);
                    //intent.putExtra("name", t);
                    //intent.putExtra("url", description.url);
                    startActivity(inflater.inflate(intent));
                    return true;

                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // AE: no options menu for now
        //getMenuInflater().inflate(R.menu.menu_png_index, menu);
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

        return super.onOptionsItemSelected(item);
    }


    private ExpandableListAdapter makeIndexListAdapter() {
        List<String> listDataHeader;
        Map<String, List<Object>> listDataChild;

        listDataHeader = new ArrayList<String>(4);
        listDataChild = new HashMap<String, List<Object>>();

        // Adding child data
        listDataHeader.add("PNG Suite: Basic");
        listDataHeader.add("Mozilla");
        listDataHeader.add("Other");

        // Adding child data
        List<Object> pngSuite = new ArrayList<Object>();
//        for (PngSuiteIndexItem item: IndexItems.category("Basic")) {
//            //IndexItems.add("Basic", pngSuite);
//            pngSuite.add(item);
//        }

        pngSuite.add(new PngSuiteIndexItem("basn0g01", "1 bit gray (black & white)"));
        pngSuite.add(new PngSuiteIndexItem("basn0g02", "2 bit gray"));
        pngSuite.add(new PngSuiteIndexItem("basn0g04", "4 bit gray"));
        pngSuite.add(new PngSuiteIndexItem("basn0g08", "8 bit gray"));
        pngSuite.add(new PngSuiteIndexItem("basn0g16", "16 bit gray"));
        pngSuite.add(new PngSuiteIndexItem("basn2c08", "8 bit colour"));
        pngSuite.add(new PngSuiteIndexItem("basn2c16", "16 bit colour"));
        pngSuite.add(new PngSuiteIndexItem("basn3p01", "1 bit palette"));
        pngSuite.add(new PngSuiteIndexItem("basn3p02", "2 bit palette"));
        pngSuite.add(new PngSuiteIndexItem("basn3p04", "4 bit palette"));
        pngSuite.add(new PngSuiteIndexItem("basn3p08", "8 bit palette"));
        pngSuite.add(new PngSuiteIndexItem("basn4a08", "8 bit gray+alpha"));
        pngSuite.add(new PngSuiteIndexItem("basn4a16", "16 bit gray+alpha"));
        pngSuite.add(new PngSuiteIndexItem("basn6a08", "8 bit truecolour+alpha"));
        pngSuite.add(new PngSuiteIndexItem("basn6a16", "16 bit truecolour+alpha"));
        pngSuite.add(new PngSuiteIndexItem("basi6a08", "8-bit interlaced truecolour"));
        pngSuite.add(new PngSuiteIndexItem("tm3n3p02", "Transparency 4-panel"));

        List<Object> mozilla = new ArrayList<Object>();
        mozilla.add(new ResourceIndexItem("mozilla/loading_16.png", "Loading 16x16"));
        mozilla.add(new ResourceIndexItem("mozilla/demo-1.png", "Demo 1"));
        mozilla.add(new ResourceIndexItem("mozilla/demo-2-over+background.png", "Circles (Dipose Full)"));
        mozilla.add(new ResourceIndexItem("mozilla/demo-2-over+none.png", "Circles (Dispose None)"));
        mozilla.add(new ResourceIndexItem("mozilla/demo-2-over+previous.png", "Circles (Dispose Previous)"));

        List<Object> other = new ArrayList<Object>();
//
        other.add(new ResourceIndexItem("wikipedia/bouncing_beach_ball.png", "Beach Ball (wikipedia local)"));
        //other.add(new UrlIndexItem("http://upload.wikimedia.org/wikipedia/commons/1/14/Animated_PNG_example_bouncing_beach_ball.png", "Beach Ball (wikipedia remote)"));
        //other.add(new UrlIndexItem(Uri.parse("http://i.giphy.com/QUEFPDQhw89bi.gif"), "Cat (Remote GIF)", "image/gif")); <-- broken
        other.add(new ResourceIndexItem("other/rotating_earth.gif", "Rotating Earth (GIF)", "image/gif"));
        //other.add("Open URL...");
        //other.add("Open Local Picture...");

        listDataChild.put(listDataHeader.get(0), pngSuite); // Header, Child data
        listDataChild.put(listDataHeader.get(1), mozilla);
        listDataChild.put(listDataHeader.get(2), other);

        return new IndexExpandableListAdapter(this, listDataHeader, listDataChild);
    }

}

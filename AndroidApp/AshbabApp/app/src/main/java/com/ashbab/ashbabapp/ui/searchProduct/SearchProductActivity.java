package com.ashbab.ashbabapp.ui.searchProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ashbab.ashbabapp.R;


public class SearchProductActivity extends AppCompatActivity
{
    // Tag to be used for debugging
    private static final String LOG_TAG =  SearchProductActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        Log.v(LOG_TAG, LOG_TAG + "created");

        // set the modified toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar_search_product);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener((view) ->
                onBackPressed());
    }

    /**
     * Inflates the appbar_items_other menu to add items to the action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.appbar_items_other, menu);
        return true;
    }

    /**
     * Action bar item clicks are handled here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_other)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * By using this method other activities can create an intent to start this activity
     * @param context of the previous activity
     * @return the intent required for starting SearchProductActivity
     */
    public static Intent buildIntent(Context context)
    {
        return new Intent(context, SearchProductActivity.class);
    }
}

package com.ashbab.ashbabapp.ui.searchProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class SearchProductActivity extends AppCompatActivity
{
    // Tag to be used for debugging
    private static final String LOG_TAG =  SearchProductActivity.class.getSimpleName();

    SearchRecyclerAdapter searchRecyclerAdapter;

    private static final DatabaseReference PRODUCT_REF =
            FirebaseDatabase.getInstance().getReference().child("/Products");

    private RecyclerView recyclerView;

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

        recyclerView = findViewById(R.id.rv_search_results);

        // The items of the recycler view will be shown in grids
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(PRODUCT_REF, Product.class)
                        .setLifecycleOwner(SearchProductActivity.this)
                        .build();

        searchRecyclerAdapter = new SearchRecyclerAdapter(options);
        // specify the adapter to populate the recyclerView
        recyclerView.setAdapter(searchRecyclerAdapter);
    }

    /**
     * Inflates the appbar_items_other menu to add items to the action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.appbar_items_other, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_other).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Query firebaseSearchQuery = PRODUCT_REF.orderByChild("productName").startAt(query).endAt(query + "\uf8ff");

                Log.v(LOG_TAG, "Search Parameter is: " + query);

                FirebaseRecyclerOptions<Product> options =
                        new FirebaseRecyclerOptions.Builder<Product>()
                                .setQuery(firebaseSearchQuery, Product.class)
                                .setLifecycleOwner(SearchProductActivity.this)
                                .build();

                searchRecyclerAdapter = new SearchRecyclerAdapter(options);
                // specify the adapter to populate the recyclerView
                recyclerView.setAdapter(searchRecyclerAdapter);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
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

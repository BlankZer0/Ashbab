package com.ashbab.ashbabapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.Product;

import com.ashbab.ashbabapp.ui.productPage.ProductDetails;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * This is the Activity for the App home
 * The user is directed to this menu at app launch after the user logs in successfully.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    // Tag to be used for debugging
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MainProductAdapter mainProductAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the modified toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creates a drawer (Hamburger menu) and and set toggle options
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Attaches an Item Selected listener with app drawer's navigation items
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Create a list to hold all the products to be shown in the recycler view
        final ArrayList<Product> products = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_newly_added);
        // improves performance because changes  that changes in content
        // do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // The items of the recycler view will be shown in grids
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mainProductAdapter = new MainProductAdapter(products);
        recyclerView.setAdapter(mainProductAdapter);

        // Selects the viewModel to fetch the data needed for the activity
        // The data feed to the activity are Live Data that automatically updates the UI on data change
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<Product> liveData = viewModel.getProductLiveDataMain();
        liveData.observe(this, product ->
        {
            Log.v(LOG_TAG, "Data change detected");

            if (product != null)
            {
                // updates the UI here
                String productName = product.getProductName();
                float productPrice = product.getProductPrice();
                String imageUrl = product.getImageUrl();

                products.add(new Product(productName, productPrice, imageUrl));
                Log.v(LOG_TAG, productName + " has been added to the list");

                mainProductAdapter = new MainProductAdapter(products);
                recyclerView.setAdapter(mainProductAdapter);
            }
        });

        // Attaches an item listener to the recycler view items
        mainProductAdapter.setOnItemClickListener((view, position) ->
        {
            products.get(position);
            Intent productDetailsIntent = new Intent(MainActivity.this, ProductDetails.class);
            Intent newIntent = new Intent();
            startActivity(productDetailsIntent);
        });
    }

    /**
     * Closes the app drawer when the back button is pressed
     * If it's already opened the back button will work as it normally would have
     */
    @Override
    public void onBackPressed()
    {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    /**
     * Inflates the main menu to add items to the action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle item clicks of the navigation menu
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            // TODO: Handle Home button click action
        }
        else if (id == R.id.nav_chair)
        {
            // TODO: Handle Chair button click action
        }
        else if (id == R.id.nav_table)
        {
            // TODO: Handle Table button click action
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

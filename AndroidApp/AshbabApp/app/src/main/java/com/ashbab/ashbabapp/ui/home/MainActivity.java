package com.ashbab.ashbabapp.ui.home;

import android.os.Bundle;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.Product;

import com.ashbab.ashbabapp.ui.productPage.ProductDetailsActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the modified toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar_main);
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
        ArrayList<Product> gridProducts = new ArrayList<>();

        // Create a list of products that holds every attribute of the product
        ArrayList<Product> completeProducts = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rv_newly_added);
        // improves performance because changes  that changes in content
        // do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // The items of the recycler view will be shown in grids
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // specify the adapter to populate the recyclerView
        mainProductAdapter = new MainProductAdapter(gridProducts);
        recyclerView.setAdapter(mainProductAdapter);

        // Selects the viewModel to fetch the data needed for the activity
        // The data feed to the activity are Live Data that automatically updates the UI on data change
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<Product> liveData = viewModel.getProductLiveDataMain();
        // Listen data change forever
        liveData.observeForever(product ->
        {
            Log.v(LOG_TAG, "Data change detected");

            if (product != null)
            {
                // updates the UI here
                String productName = product.getProductName();
                float productPrice = product.getProductPrice();
                String imageUrl = product.getImageUrl();

                completeProducts.add(product);
                gridProducts.add(new Product(productName, productPrice, imageUrl));
                Log.v(LOG_TAG, productName + " has been added to the list");

                mainProductAdapter.notifyDataSetChanged();
            }
        });

        // Attaches an item listener to the recycler view items
        mainProductAdapter.setOnItemClickListener((view, position) ->
        {
            Log.v(LOG_TAG, "The position of the selected item: " + position);
            // Stop listening to data change before switching to another activity
            liveData.removeObservers(this);

            // Get the product from the user selected location
            Product parceledProduct = completeProducts.get(position);

            Log.v(LOG_TAG, parceledProduct.toString());

            // Open the product details activity and show details of the selected product
            startActivity(ProductDetailsActivity.buildIntent(this, parceledProduct));
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
     * Inflates the appbar_items menu to add items to the action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.appbar_items, menu);
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

package com.ashbab.ashbabapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.Product;

import com.ashbab.ashbabapp.ui.productDetails.ProductDetailsActivity;
import com.ashbab.ashbabapp.ui.searchProduct.SearchProductActivity;
import com.ashbab.ashbabapp.ui.userProfile.UserProfileActivity;
import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is the Activity for the App home
 * The user is directed to this menu at app launch after the user logs in successfully.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    // Tag to be used for debugging
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final DatabaseReference PRODUCT_REF =
            FirebaseDatabase.getInstance().getReference().child("/Products");

    private static final DatabaseReference USER_REF =
            FirebaseDatabase.getInstance().getReference().child("/Users");

    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;

    // Choose authentication providers
    private final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build());

    MainRecyclerAdapter mainRecyclerAdapter;
    SubMenu subMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the Toolbar, AppDrawer and the Navigation Bar
        setDrawerNavigationAndToolbar();

        // Populate the recycler View with recyclerView Adapter Products
        populateRecyclerView();

        setUpSearchBar();

        // Check whether or not the device is connected to the internet
        checkInternetConnectivity();

        // Check whether or not the user have signed in
        listenForAuthentication();

        // Selects the viewModel to fetch the data needed for the activity
        // The data feed to the activity are Live Data that automatically updates the UI on data change
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        LiveData<String> liveData = viewModel.getCategoryLiveData();
        // Listen data change forever
        liveData.observeForever(category ->
        {
            Log.v(LOG_TAG, "Data change detected");

            if (category != null)
            {
                Log.v(LOG_TAG, "Category Found: " + category);

                subMenu.add(category);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (authStateListener != null)
            firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // If the user presses the back button on the login screen,
        // the sign-in process will be cancelled and the user will be able to exit the app
        if (requestCode == RC_SIGN_IN)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(MainActivity.this, R.string.text_signed_in, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(MainActivity.this, R.string.text_sign_in_cancelled, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
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
        } else
        {
            super.onBackPressed();
        }
    }

    /**
     * Inflates the appbar_items_main menu to add items to the action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.appbar_items_main, menu);
        return true;
    }

    /**
     * Action bar item clicks are handled here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_sign_out_main)
        {
            AuthUI.getInstance().signOut(MainActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle item clicks of the navigation menu
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.nav_profile)
        {
            startActivity(UserProfileActivity.buildIntent(this));
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkInternetConnectivity()
    {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo == null || !networkInfo.isConnected())
        {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            ProgressBar loadingIndicator = findViewById(R.id.loading_indicator_main);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            TextView emptyStateText = findViewById(R.id.empty_view_text);
            emptyStateText.setText(R.string.text_loading_failed);
        }
    }

    private void setDrawerNavigationAndToolbar()
    {
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

        final Menu menu = navigationView.getMenu();
        subMenu = menu.addSubMenu(R.string.category_title);
    }

    private void populateRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.rv_newly_added_main);

        // The items of the recycler view will be shown in grids
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(PRODUCT_REF, Product.class)
                        .setLifecycleOwner(this)
                        .build();

        mainRecyclerAdapter = new MainRecyclerAdapter(options);
        // specify the adapter to populate the recyclerView
        recyclerView.setAdapter(mainRecyclerAdapter);

        // Attaches an item listener to the recycler view items
        mainRecyclerAdapter.setOnItemClickListener((view, position) ->
        {
            Log.v(LOG_TAG, "The position of the selected item: " + position);

            String key = mainRecyclerAdapter.getRef(position).getKey();
            Log.v(LOG_TAG, "The key is: " + key);

            // Open the product details activity and show details of the selected product
            startActivity(ProductDetailsActivity.buildIntent(this, key));
        });
    }

    private void setUpSearchBar()
    {
        TextInputEditText searchProduct = findViewById(R.id.search_product_main);

        // Start the searchProductActivity whenever the searchProduct toolbar is clicked
        searchProduct.setOnClickListener(view ->
        {
            Log.v(LOG_TAG, "Search Product Clicked");
            //liveData.removeObservers(this);
            startActivity(SearchProductActivity.buildIntent(this));
        });
    }

    private void listenForAuthentication()
    {
        authStateListener = firebaseAuth ->
        {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user == null)
            {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(providers)
                                .setLogo(R.mipmap.ashbab_icon)
                                .setTheme(R.style.LoginTheme)
                                .build(),
                        RC_SIGN_IN);
            }
            else
            {
                updateNavHeader(user);
            }
        };
    }

    private void updateNavHeader(FirebaseUser user)
    {
        Log.d(LOG_TAG, "updateNavHeader Called");

        for (UserInfo profile : user.getProviderData())
        {
            if (profile.getProviderId().equals("facebook.com")
                    || profile.getProviderId().equals("google.com"))
            {
                Log.d(LOG_TAG, "ProviderID: " + profile.getProviderId());

                //For linked facebook account
                Log.d(LOG_TAG, "User is signed in with Facebook / Google");

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                String photoUrl = Objects.requireNonNull(profile.getPhotoUrl()).toString();

                ImageView userPhoto = findViewById(R.id.profile_image_nav);
                TextView userName = findViewById(R.id.name_text_nav);
                TextView userEmail = findViewById(R.id.email_text_nav);

                if (userPhoto != null && userName != null && userEmail != null)
                {

                    Glide.with(userPhoto.getContext()).load(photoUrl).into(userPhoto);
                    userName.setText(name);
                    userEmail.setText(email);
                }

                Map<String,Object> updates = new HashMap<String,Object>();
                updates.put("uID", uid);
                updates.put("userName", name);
                updates.put("userEmail", email);
                updates.put("userPhotoUrl", photoUrl);

                USER_REF.child(uid).updateChildren(updates);
            }
            else
            {
                // The user's ID, unique to the Firebase project.
                String uid = user.getUid();

                // Name, email address, and profile photo Url
                String name = user.getDisplayName();
                String email = user.getEmail();
                String photoUrl = null;

                if (user.getPhotoUrl() != null)
                    photoUrl = user.getPhotoUrl().toString();

                Map<String,Object> updates = new HashMap<String,Object>();
                updates.put("uID", uid);
                updates.put("userName", name);
                updates.put("userEmail", email);
                updates.put("userPhotoUrl", photoUrl);

                USER_REF.child(uid).updateChildren(updates);
            }
        }
    }
}
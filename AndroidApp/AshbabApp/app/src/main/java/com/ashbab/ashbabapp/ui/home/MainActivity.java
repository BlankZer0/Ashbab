package com.ashbab.ashbabapp.ui.home;

import android.os.Bundle;

import com.ashbab.ashbabapp.R;

import com.ashbab.ashbabapp.data.model.Product;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private MainProductAdapter mainProductAdapter;
    private RecyclerView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("Chair", 10000, R.drawable.family_father));
        products.add(new Product("Table", 20000, R.drawable.family_mother));
        products.add(new Product("Bed", 31500, R.drawable.family_son));
        products.add(new Product("Chair", 10000, R.drawable.family_father));
        products.add(new Product("Table", 20000, R.drawable.family_mother));
        products.add(new Product("Bed", 31500, R.drawable.family_son));
        products.add(new Product("Chair", 10000, R.drawable.family_father));
        products.add(new Product("Table", 20000, R.drawable.family_mother));
        products.add(new Product("Bed", 31500, R.drawable.family_son));
        products.add(new Product("Chair", 10000, R.drawable.family_father));
        products.add(new Product("Table", 20000, R.drawable.family_mother));
        products.add(new Product("Bed", 31500, R.drawable.family_son));
        products.add(new Product("Chair", 10000, R.drawable.family_father));
        products.add(new Product("Table", 20000, R.drawable.family_mother));
        products.add(new Product("Bed", 31500, R.drawable.family_son));
        products.add(new Product("Chair", 10000, R.drawable.family_father));
        products.add(new Product("Table", 20000, R.drawable.family_mother));
        products.add(new Product("Bed", 31500, R.drawable.family_son));
        products.add(new Product("Chair", 10000, R.drawable.family_father));
        products.add(new Product("Table", 20000, R.drawable.family_mother));
        products.add(new Product("Bed", 31500, R.drawable.family_son));
        products.add(new Product("Chair", 10000, R.drawable.family_father));
        products.add(new Product("Table", 20000, R.drawable.family_mother));
        products.add(new Product("Bed", 31500, R.drawable.family_son));

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.rv_newly_added);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a Grid layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mainProductAdapter = new MainProductAdapter(products);
        recyclerView.setAdapter(mainProductAdapter);



        // set the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer and Navigation bar
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        // Handle what happens when the back button is pressed
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
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

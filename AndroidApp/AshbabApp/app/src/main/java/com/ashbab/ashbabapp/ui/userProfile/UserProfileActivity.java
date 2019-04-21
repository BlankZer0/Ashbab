package com.ashbab.ashbabapp.ui.userProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.ui.searchProduct.SearchProductActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class UserProfileActivity extends AppCompatActivity
{
    private static final String LOG_TAG = UserProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Log.v(LOG_TAG, LOG_TAG + " Created");

        // Find the floating action button and listen for clicks
        ExtendedFloatingActionButton exFab = findViewById(R.id.fab_save_user);
        exFab.setOnClickListener((view) ->
        {
            // TODO: Add buy product Logic
        });

        Toolbar toolbar = findViewById(R.id.toolbar_user_profile);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((view) ->
                onBackPressed());


    }

    /**
     * By using this method other activities can create an intent to start this activity
     * @param context of the previous activity
     * @return the intent required for starting UserProfileActivity
     */
    public static Intent buildIntent(Context context)
    {
        return new Intent(context, UserProfileActivity.class);
    }
}

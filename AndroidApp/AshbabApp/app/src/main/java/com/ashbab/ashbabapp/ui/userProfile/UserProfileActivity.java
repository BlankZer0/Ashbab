package com.ashbab.ashbabapp.ui.userProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.User;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The activity for user data
 */
public class UserProfileActivity extends AppCompatActivity
{
    private static final String LOG_TAG = UserProfileActivity.class.getSimpleName();

    private static final DatabaseReference USER_REF =
            FirebaseDatabase.getInstance().getReference().child("/Users");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Log.v(LOG_TAG, LOG_TAG + " Created");

        Toolbar toolbar = findViewById(R.id.toolbar_user_profile);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((view) ->
                onBackPressed());

        String userID = FirebaseAuth.getInstance().getUid();

        // The factory object customizes the viewModel to take in an extra parameter
        // This parameter will be used to conduct database search
        UserProfileViewModelFactory factory = new UserProfileViewModelFactory(userID);

        // Selects the viewModel to fetch the data needed for the activity
        // The data feed to the activity are Live Data that automatically updates the UI on data change
        UserProfileViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(UserProfileViewModel.class);
        LiveData<User> liveData = viewModel.getUserProfileLiveData();

        // Listen to data change forever
        liveData.observeForever(user ->
        {
            Log.v(LOG_TAG, "Data change detected");

            if (user != null)
                updateUserProfileUI(user);
        });
    }

    void updateUserProfileUI(User user)
    {
        Log.v(LOG_TAG,  "updateUserProfileUI Called");

        final ImageView userProfilePic = findViewById(R.id.profile_pic_user);
        final TextInputEditText userName = findViewById(R.id.input_name_user);
        final TextInputEditText userAddress = findViewById(R.id.input_address_user);
        final TextView userEmail = findViewById(R.id.text_email_user);

        // Name, email address, and profile photo Url
        userName.setText(user.getUserName());
        userEmail.setText(user.getUserEmail());
        userAddress.setText(user.getUserAddress());

        if (user.getUserPhotoUrl() != null)
        {
            Glide.with(userProfilePic.getContext()).load(user.getUserPhotoUrl()).into(userProfilePic);
        }

        // Find the floating action button and listen for clicks
        ExtendedFloatingActionButton exFab = findViewById(R.id.fab_save_user);
        exFab.setOnClickListener((view) ->
        {
            user.setUserAddress(userAddress.getEditableText().toString());

            try
            {
                USER_REF.child(user.getuID()).setValue(user);
                Toast.makeText(UserProfileActivity.this, R.string.text_profile_saved, Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(UserProfileActivity.this, R.string.text_profile_save_failed, Toast.LENGTH_LONG).show();
            }
        });
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
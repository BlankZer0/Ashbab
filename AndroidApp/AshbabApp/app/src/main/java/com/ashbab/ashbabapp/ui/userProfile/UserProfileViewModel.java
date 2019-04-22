package com.ashbab.ashbabapp.ui.userProfile;

import com.ashbab.ashbabapp.data.AshbabRepository;
import com.ashbab.ashbabapp.data.model.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * The viewModel class for the Product Details Activity that feed the data to the activity
 * All the data of this class are lifecycle aware
 */
public class UserProfileViewModel extends ViewModel
{

    private LiveData<User> userLiveData;

    public UserProfileViewModel(String key)
    {
        AshbabRepository ashbabRepository = new AshbabRepository();
        userLiveData = ashbabRepository.getLiveDataUser(key);
    }

    /**
     * The activity fetches the data calling this method and the data is observed by the activity
     */
    @NonNull
    public LiveData<User> getUserProfileLiveData()
    {
        return userLiveData;
    }
}

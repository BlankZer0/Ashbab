package com.ashbab.ashbabapp.ui.userProfile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Implementations of this Factory interface are responsible to instantiate UserProfileViewModel.
 */
public class UserProfileViewModelFactory implements ViewModelProvider.Factory
{
    private String extra;

    public UserProfileViewModelFactory(String extra)
    {
        this.extra = extra;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        return (T) new UserProfileViewModel(extra);
    }
}

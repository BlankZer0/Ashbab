package com.ashbab.ashbabapp.ui.home;

import com.ashbab.ashbabapp.data.AshbabRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * The viewModel class for the Main Activity that feed the data to the activity
 * All the data of this class are lifecycle aware
 */
class MainViewModel extends ViewModel
{

    private LiveData<String> categoryLiveData;

    MainViewModel()
    {
        AshbabRepository ashbabRepository = new AshbabRepository();
        categoryLiveData = ashbabRepository.getLiveDataCategory();
    }

    /**
     * The activity fetches the data calling this method and the data is observed by the activity
     */
    @NonNull
    LiveData<String> getCategoryLiveData()
    {
        return categoryLiveData;
    }
}

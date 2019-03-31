package com.ashbab.ashbabapp.ui.home;

import android.util.Log;

import com.ashbab.ashbabapp.data.AshbabRepository;
import com.ashbab.ashbabapp.data.model.Product;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel
{
    private static final String LOG_TAG = MainViewModel.class.getSimpleName();

    private AshbabRepository ashbabRepository;
    private LiveData<Product> productLiveData;

    public MainViewModel()
    {
        ashbabRepository = new AshbabRepository();
        productLiveData = ashbabRepository.getLiveDataForHomeCards();
    }

    @NonNull
    LiveData<Product> getProductLiveDataMain ()
    {
        return productLiveData;
    }
}

package com.ashbab.ashbabapp.ui.productPage;

import com.ashbab.ashbabapp.data.AshbabRepository;
import com.ashbab.ashbabapp.data.model.Product;
import com.ashbab.ashbabapp.ui.home.MainViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProductDetailsViewModel extends ViewModel
{
    private static final String LOG_TAG = ProductDetailsViewModel.class.getSimpleName();

    private AshbabRepository ashbabRepository;
    private LiveData<Product> productLiveData;

    public ProductDetailsViewModel()
    {
        ashbabRepository = new AshbabRepository();
        productLiveData = ashbabRepository.getLiveDataForHomeCards();
    }

    @NonNull
    LiveData<Product> getProductDetailsLiveData ()
    {
        return productLiveData;
    }
}

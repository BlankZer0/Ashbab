package com.ashbab.ashbabapp.ui.productDetails;

import com.ashbab.ashbabapp.data.AshbabRepository;
import com.ashbab.ashbabapp.data.model.Product;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * The viewModel class for the Product Details Activity that feed the data to the activity
 * All the data of this class are lifecycle aware
 */
public class ProductDetailsViewModel extends ViewModel
{

    private LiveData<Product> productLiveData;

    ProductDetailsViewModel(String key)
    {
        AshbabRepository ashbabRepository = new AshbabRepository();
        productLiveData = ashbabRepository.getLiveDataProduct(key);
    }

    /**
     * The activity fetches the data calling this method and the data is observed by the activity
     */
    @NonNull
    LiveData<Product> getProductDetailsLiveData()
    {
        return productLiveData;
    }
}

package com.ashbab.ashbabapp.ui.productDetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Implementations of this Factory interface are responsible to instantiate ProductDetailsViewModel.
 */
class ProductDetailsViewModelFactory implements ViewModelProvider.Factory
{
    private String extra;

    ProductDetailsViewModelFactory(String extra)
    {
        this.extra = extra;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        return (T) new ProductDetailsViewModel(extra);
    }
}

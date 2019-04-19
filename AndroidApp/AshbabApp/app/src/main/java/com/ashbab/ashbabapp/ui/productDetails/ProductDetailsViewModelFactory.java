package com.ashbab.ashbabapp.ui.productDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProductDetailsViewModelFactory implements ViewModelProvider.Factory
{
    private String extra;

    public ProductDetailsViewModelFactory(String extra)
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

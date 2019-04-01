package com.ashbab.ashbabapp.data;

import android.util.Log;

import com.ashbab.ashbabapp.data.database.FirebaseQueryLiveData;
import com.ashbab.ashbabapp.data.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * The single point of truth
 * All the data used for the is has be fetched from this repository class
 */
public class AshbabRepository
{
    private static final String LOG_TAG = AshbabRepository.class.getSimpleName();

    private static final DatabaseReference PRODUCT_REF =
            FirebaseDatabase.getInstance().getReference().child("/Products");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(PRODUCT_REF);

    private final MediatorLiveData<Product> productLiveDataMain = new MediatorLiveData<>();

    /**
     * Set up the MediatorLiveData to convert DataSnapshot objects into Product objects
     */
    private void InitializeData()
    {
        productLiveDataMain.addSource(liveData, dataSnapshot ->
        {
            if (dataSnapshot != null)
            {
                Log.v(LOG_TAG, "Data snapshot is not null");

                ExecutorService executorService = Executors.newSingleThreadExecutor();

                executorService.execute(() ->
                {
                    productLiveDataMain.postValue(dataSnapshot.getValue(Product.class));

                    Log.v(LOG_TAG, Objects.requireNonNull(dataSnapshot.getValue(Product.class)).getProductName() + " has been found");
                });
            }
            else
            {
                Log.v(LOG_TAG, "Data snapshot is null");

                productLiveDataMain.setValue(null);
            }
        });
    }

    /**
     * The Main Activity viewModel will collect it's data by calling this method
     */
    @NonNull
    public LiveData<Product> getLiveDataForHomeCards()
    {
        Log.v(LOG_TAG, PRODUCT_REF.toString());

        InitializeData();
        return productLiveDataMain;
    }
}

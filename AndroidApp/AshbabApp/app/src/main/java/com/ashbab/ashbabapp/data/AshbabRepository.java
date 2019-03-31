package com.ashbab.ashbabapp.data;

import android.util.Log;

import com.ashbab.ashbabapp.data.database.FirebaseQueryLiveData;
import com.ashbab.ashbabapp.data.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class AshbabRepository
{
    private static final String LOG_TAG = AshbabRepository.class.getSimpleName();

    private static final DatabaseReference PRODUCT_REF =
            FirebaseDatabase.getInstance().getReference().child("/Products");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(PRODUCT_REF);

    private final MediatorLiveData<Product> productLiveDataMain = new MediatorLiveData<>();

    private void InitializeData()
    {
        // Set up the MediatorLiveData to convert DataSnapshot objects into HotStock objects
        productLiveDataMain.addSource(liveData, new Observer<DataSnapshot>()
        {
            @Override
            public void onChanged(@Nullable final DataSnapshot dataSnapshot)
            {
                if (dataSnapshot != null)
                {
                    Log.v(LOG_TAG, "Data snapshot is not null");

                    ExecutorService executorService = Executors.newSingleThreadExecutor();

                    executorService.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            productLiveDataMain.postValue(dataSnapshot.getValue(Product.class));

                            Log.v(LOG_TAG, dataSnapshot.getValue(Product.class).getProductName() + " has been found");
                        }
                    });
                }
                else
                {
                    Log.v(LOG_TAG, "Data snapshot is null");

                    productLiveDataMain.setValue(null);
                }
            }
        });
    }

    @NonNull
    public LiveData<Product> getLiveDataForHomeCards()
    {
        Log.v(LOG_TAG, PRODUCT_REF.toString());

        InitializeData();
        return productLiveDataMain;
    }
}

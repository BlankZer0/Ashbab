package com.ashbab.ashbabapp.data;

import android.util.Log;

import com.ashbab.ashbabapp.data.database.FirebaseQueryLiveData;
import com.ashbab.ashbabapp.data.model.Product;
import com.ashbab.ashbabapp.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private static final DatabaseReference CATEGORY_REF =
            FirebaseDatabase.getInstance().getReference().child("/Categories");

    private static final DatabaseReference USER_REF =
            FirebaseDatabase.getInstance().getReference().child("/Users");

    private FirebaseQueryLiveData productLiveData = new FirebaseQueryLiveData(PRODUCT_REF);
    private FirebaseQueryLiveData categoryLiveData = new FirebaseQueryLiveData(CATEGORY_REF);
    private FirebaseQueryLiveData userLiveData = new FirebaseQueryLiveData(USER_REF);

    private MediatorLiveData<Product> productMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<String> categoryMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<User> userMediatorLiveData = new MediatorLiveData<>();

    /**
     * Set up the MediatorLiveData to convert DataSnapshot objects into Product productLiveData
     */
    private void InitializeProductData()
    {
        productMediatorLiveData.addSource(productLiveData, (DataSnapshot dataSnapshot) ->
        {
            if (dataSnapshot != null)
            {
                Log.v(LOG_TAG, "Data snapshot is not null");

                ExecutorService executorService = Executors.newSingleThreadExecutor();

                executorService.execute(() ->
                {
                    Log.v(LOG_TAG, Objects.requireNonNull(dataSnapshot.getValue(Product.class)).getProductName() + " has been found");
                    // Post value is used because it's thread safe
                    productMediatorLiveData.postValue(dataSnapshot.getValue(Product.class));
                });
            }
            else
            {
                Log.v(LOG_TAG, "Data snapshot is null");

                productMediatorLiveData.setValue(null);
            }
        });
    }

    private void getCategoryFromFirebase()
    {
        categoryMediatorLiveData.addSource(categoryLiveData, (DataSnapshot dataSnapshot) ->
        {
            if (dataSnapshot != null)
            {
                Log.v(LOG_TAG, "Data snapshot is not null");

                ExecutorService executorService = Executors.newSingleThreadExecutor();

                executorService.execute(() ->
                {
                    Log.v(LOG_TAG, Objects.requireNonNull(dataSnapshot.getValue(String.class)) + " has been found");
                    // Post value is used because it's thread safe
                    categoryMediatorLiveData.postValue(dataSnapshot.getValue(String.class));
                });
            }
            else
            {
                Log.v(LOG_TAG, "Data snapshot is null");

                categoryMediatorLiveData.setValue(null);
            }
        });
    }

    /**
     * Set up the MediatorLiveData to convert DataSnapshot objects into Product productLiveData
     */
    private void InitializeUserData()
    {
        userMediatorLiveData.addSource(userLiveData, (DataSnapshot dataSnapshot) ->
        {
            if (dataSnapshot != null)
            {
                Log.v(LOG_TAG, "Data snapshot is not null");

                ExecutorService executorService = Executors.newSingleThreadExecutor();

                executorService.execute(() ->
                {
                    Log.v(LOG_TAG, Objects.requireNonNull(dataSnapshot.getValue(User.class)).getUserEmail() + " has been found");
                    // Post value is used because it's thread safe
                    userMediatorLiveData.postValue(dataSnapshot.getValue(User.class));
                });
            }
            else
            {
                Log.v(LOG_TAG, "Data snapshot is null");

                userMediatorLiveData.setValue(null);
            }
        });
    }

    /**
     * The ProductDetails viewModel will collect it's data by calling this method
     */
    public LiveData<Product> getLiveDataProduct(String key)
    {
        productLiveData = new FirebaseQueryLiveData(PRODUCT_REF.orderByKey().equalTo(key));

        InitializeProductData();
        return productMediatorLiveData;
    }

    public LiveData<String> getLiveDataCategory()
    {
        getCategoryFromFirebase();
        return categoryMediatorLiveData;
    }

    public LiveData<User> getLiveDataUser(String key)
    {
        userLiveData = new FirebaseQueryLiveData(USER_REF.orderByKey().equalTo(key));

        InitializeUserData();
        return userMediatorLiveData;
    }
}

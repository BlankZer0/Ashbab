package com.ashbab.ashbabapp.ui.home;

import android.util.Log;

import com.ashbab.ashbabapp.data.AshbabRepository;
import com.ashbab.ashbabapp.data.database.FirebaseQueryLiveData;
import com.ashbab.ashbabapp.data.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel
{
    private static final String LOG_TAG = MainViewModel.class.getSimpleName();

//    private final MutableLiveData<Product> productMutableLiveData;
//    private final AshbabRepository repository;
//
//    public MainViewModel(AshbabRepository repository)
//    {
//        repository = repository;
//        productMutableLiveData = repository.getWeatherByDate(mDate);
//    }
//
//    public LiveData<Product> getWeather() {
//        return mWeather;
//    }

    private static final DatabaseReference PRODUCT_REF =
            FirebaseDatabase.getInstance().getReference().child("/Products");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(PRODUCT_REF);

    private final MediatorLiveData<Product> productLiveDataMain = new MediatorLiveData<>();

    public MainViewModel()
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

                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            productLiveDataMain.postValue(dataSnapshot.getValue(Product.class));

                            Log.v(LOG_TAG, dataSnapshot.getValue(Product.class).getProductName() + " has been found");
                        }
                    }).start();
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
    LiveData<Product> getProductLiveDataMain ()
    {
        Log.v(LOG_TAG, PRODUCT_REF.toString());

        return productLiveDataMain;
    }
}

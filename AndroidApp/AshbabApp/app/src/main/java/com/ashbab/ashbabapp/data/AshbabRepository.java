package com.ashbab.ashbabapp.data;

import com.ashbab.ashbabapp.data.database.FirebaseQueryLiveData;
import com.ashbab.ashbabapp.data.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class AshbabRepository
{
    private static final String LOG_TAG = AshbabRepository.class.getSimpleName();

    private static final DatabaseReference PRODUCT_REF =
            FirebaseDatabase.getInstance().getReference("/Products");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(PRODUCT_REF);

//    public Product getProductsForCards()
//    {
//        return
//    }

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}

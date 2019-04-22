package com.ashbab.ashbabapp.data.database;

import android.os.Handler;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This class turns the dataSnapshot from the Firebase into LiveData that can be observed for any kind of changes
 */
public class FirebaseQueryLiveData extends LiveData<DataSnapshot>
{
    private static final String LOG_TAG = FirebaseQueryLiveData.class.getSimpleName();

    private final Query query;
    private final ChildEventListener myChildEventListener = new MyChildEventListener();
    private boolean listenerRemovePending = false;
    private final Handler handler = new Handler();

    public FirebaseQueryLiveData(DatabaseReference ref)
    {
        this.query = ref;

        // caches the data from firebase locally
        query.keepSynced(true);
    }

    public FirebaseQueryLiveData(Query query)
    {
        this.query = query;

        // caches the data from firebase locally
        query.keepSynced(true);
    }

    private final Runnable removeListener = new Runnable()
    {
        @Override
        public void run()
        {
            query.removeEventListener(myChildEventListener);
            listenerRemovePending = false;
        }
    };

    @Override
    protected void onActive()
    {
        Log.d(LOG_TAG, "onActive");

        // Removes the listener only if it's pending for removal
        if (listenerRemovePending)
        {
            handler.removeCallbacks(removeListener);
        }
        else {
            query.addChildEventListener(myChildEventListener);
        }
        listenerRemovePending = false;
    }

    @Override
    protected void onInactive()
    {
        Log.d(LOG_TAG, "onInactive");

        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }

    /**
     * A child event listener to observe any changes in the child nodes of the query
     */
    private class MyChildEventListener implements ChildEventListener
    {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot,@Nullable String s)
        {
            Log.v(LOG_TAG, "MyChildEventListener: onChildAdded called");
            setValue(dataSnapshot);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {
            Log.v(LOG_TAG, "MyChildEventListener: onChildChanged called");
            //setValue(dataSnapshot);
        }
        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
        {
        }
        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}
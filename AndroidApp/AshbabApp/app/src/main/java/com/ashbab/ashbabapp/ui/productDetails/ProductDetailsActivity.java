package com.ashbab.ashbabapp.ui.productDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.Product;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * Creates the activity to show the specific products of a product.
 */
public class ProductDetailsActivity extends AppCompatActivity
{
    private static final String LOG_TAG = ProductDetailsActivity.class.getSimpleName();
    private static final String BUNDLE_KEY = "parcel_key";  // key to get the parceled product

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Log.v(LOG_TAG, LOG_TAG + " Created");

        // Find the floating action button and listen for clicks
        ExtendedFloatingActionButton exFab = findViewById(R.id.fab_buy_product);
        exFab.setOnClickListener((view) ->
        {
            // TODO: Add buy product Logic
        });

        Toolbar toolbar = findViewById(R.id.toolbar_product_details);

        // Get the product from the received intent
//        Product parceledProduct = Parcels.unwrap(Objects.requireNonNull(getIntent().getExtras()).getParcelable(PARCEL_KEY));

//        Log.v(LOG_TAG, Objects.requireNonNull(parceledProduct).toString());


        // find the layout contents
        final ImageView productImage = findViewById(R.id.product_image_view_details);
        final TextView productName = findViewById(R.id.product_text_view_details);
        final TextView productPrice = findViewById(R.id.price_text_view_details);
        final TextView productCategory = findViewById(R.id.category_tag_details);
        final TextView productDescription = findViewById(R.id.description_details);
        final FloatingActionButton arButton = findViewById(R.id.ar_fab);

        String key = Objects.requireNonNull(getIntent().getExtras()).getString(BUNDLE_KEY);

        ProductDetailsViewModelFactory factory = new ProductDetailsViewModelFactory(key);

        // Selects the viewModel to fetch the data needed for the activity
        // The data feed to the activity are Live Data that automatically updates the UI on data change
        ProductDetailsViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(ProductDetailsViewModel.class);

        LiveData<Product> liveData = viewModel.getProductDetailsLiveData();
        // Listen data change forever
        liveData.observeForever(product ->
        {
            Log.v(LOG_TAG, "Data change detected");

            if (product != null)
            {
                Glide.with(productImage.getContext()).load(product.getImageUrl()).into(productImage);
                productName.setText(product.getProductName());
                productPrice.setText(String.valueOf(product.getProductPrice()));
                productCategory.setText(product.getCategory());
                productDescription.setText(product.getDescription());

                // Open the camera to show the AR model when the AR button has been clicked
                arButton.setOnClickListener(v ->
                {
                    Log.v(LOG_TAG, "AR Button Clicked");
                    startActivity(ArCameraActivity.buildIntent(this, product.getModel3dUrl()));
                });
            }
        });

        toolbar.setNavigationOnClickListener((view) ->
                onBackPressed());
    }

    /**
     * By using this method other activities can create an intent to start this activity
     * @param context of the previous activity
     * @param key sent from the previous activity is the pushID of the product
     *            We will use this pushID to search the product
     * @return the intent required for starting ProductDetailsActivity
     */
    public static Intent buildIntent(Context context, String key)
    {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY, key);
        intent.putExtras(bundle);
        return intent;
    }
}

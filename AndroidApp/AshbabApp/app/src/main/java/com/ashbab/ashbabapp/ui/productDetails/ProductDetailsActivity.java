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
import android.widget.Toast;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.Order;
import com.ashbab.ashbabapp.data.model.Product;
import com.ashbab.ashbabapp.data.model.User;
import com.ashbab.ashbabapp.ui.userProfile.UserProfileViewModel;
import com.ashbab.ashbabapp.ui.userProfile.UserProfileViewModelFactory;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Creates the activity to show the specifications of a product.
 */
public class ProductDetailsActivity extends AppCompatActivity
{
    private static final String LOG_TAG = ProductDetailsActivity.class.getSimpleName();
    private static final String BUNDLE_KEY = "bundle_key";  // key to get the pushID of the product

    private static final DatabaseReference ORDER_REF =
            FirebaseDatabase.getInstance().getReference().child("/Orders");

    Product currentProduct;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Log.v(LOG_TAG, LOG_TAG + " Created");

        // Set the custom toolbar as the activity's toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_product_details);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((view) ->
                onBackPressed());

        String key = Objects.requireNonNull(getIntent().getExtras()).getString(BUNDLE_KEY);

        // The factory object customizes the viewModel to take in an extra parameter
        // This parameter will be used to conduct database search
        ProductDetailsViewModelFactory productFactory = new ProductDetailsViewModelFactory(key);

        // Selects the viewModel to fetch the data needed for the activity
        // The data feed to the activity are Live Data that automatically updates the UI on data change
        ProductDetailsViewModel productDetailsViewModel = ViewModelProviders.of(this, productFactory)
                .get(ProductDetailsViewModel.class);
        LiveData<Product> productLiveData = productDetailsViewModel.getProductDetailsLiveData();

        String userID = FirebaseAuth.getInstance().getUid();

        UserProfileViewModelFactory userFactory = new UserProfileViewModelFactory(userID);
        UserProfileViewModel userProfileViewModel = ViewModelProviders.of(this, userFactory)
                .get(UserProfileViewModel.class);

        LiveData<User> userLiveData = userProfileViewModel.getUserProfileLiveData();

        // Listen to data change forever
        productLiveData.observe(this,  product ->
        {
            Log.v(LOG_TAG, "Product Data change detected");

            if (product != null)
            {
                currentProduct = product;
                updateProductDetailsUI(product);
            }
        });

        userLiveData.observe(this, user ->
        {
            Log.v(LOG_TAG, "User Data change detected");

            if (user != null)
                currentUser = user;
        });

        // Find the floating action button for buying a product and listen for clicks
        ExtendedFloatingActionButton exFab = findViewById(R.id.fab_buy_product);
        exFab.setOnClickListener((view) ->
                placeOrder(currentUser, currentProduct));
    }

    /**
     * @param product is provided as an argument and the UI is updated with the details
     *                about the product
     */
    void updateProductDetailsUI (Product product)
    {
        // Find all the UI items
        final ImageView productImage = findViewById(R.id.product_image_view_details);
        final TextView productName = findViewById(R.id.product_text_view_details);
        final TextView productPrice = findViewById(R.id.price_text_view_details);
        final TextView productCategory = findViewById(R.id.category_tag_details);
        final TextView productDescription = findViewById(R.id.description_details);
        final FloatingActionButton arButton = findViewById(R.id.ar_fab);

        // Set values to the UI items
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

    /**
     * By using this method other activities can create an intent to start this activity
     * @param context of the previous activity
     * @param pushID sent from the previous activity is pushID of the product
     *            We will use this pushID to search the product
     * @return the intent required for starting ProductDetailsActivity
     */
    public static Intent buildIntent(Context context, String pushID)
    {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY, pushID);
        intent.putExtras(bundle);
        return intent;
    }

    private void placeOrder(User buyer, Product product)
    {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        String orderDate = dateFormat.format(date);
        String productID = product.getProductID();
        String productName = product.getProductName();
        float productPrice = product.getProductPrice();
        String buyerName = buyer.getUserName();
        String buyerEmail = buyer.getUserEmail();
        String orderFlag = "Placed";

        if (buyer.getUserAddress() == null)
        {
            Toast.makeText(this, R.string.text_address_missing, Toast.LENGTH_LONG).show();
        }
        else
        {
            String buyerAddress = buyer.getUserAddress();
            Order order = new Order(orderDate, productID, productName, productPrice, buyerName, buyerEmail, buyerAddress, orderFlag);

            ORDER_REF.push().setValue(order);
            Toast.makeText(this, R.string.order_placed, Toast.LENGTH_LONG).show();
        }
    }
}

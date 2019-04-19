package com.ashbab.ashbabapp.ui.searchProduct;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.Product;
import com.ashbab.ashbabapp.ui.home.MainRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * The MainRecyclerAdapter binds a Firebase Query to a RecyclerView.
 * When data is added, removed, or changed these updates are automatically applied to the UI.
 */
public class SearchRecyclerAdapter extends FirebaseRecyclerAdapter<Product, SearchRecyclerAdapter.ProductViewHolder>
{
    private static final String LOG_TAG = MainRecyclerAdapter.class.getSimpleName();

    // Define listener member variable
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener
    {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    void setOnItemClickListener(SearchRecyclerAdapter.OnItemClickListener listener)
    {
        SearchRecyclerAdapter.listener = listener;
    }

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query.
     * @param options is the provided configuration that decided the recyclerView configuration
     */
    SearchRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Product> options)
    {
        super(options);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        // data items
        TextView nameTextView;
        TextView priceTextView;
        ImageView productImageView;

        ProductViewHolder(final View productView)
        {
            super(productView);

            nameTextView = productView.findViewById(R.id.product_text_view_search);
            priceTextView = productView.findViewById(R.id.price_text_view_search);
            productImageView = productView.findViewById(R.id.product_image_view_search);

            // Setup the click listener that will listen for item clicks
            productView.setOnClickListener(v ->
            {
                // Triggers click upwards to the adapter on click
                if (listener != null)
                    listener.onItemClick(productView, getLayoutPosition());
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public SearchRecyclerAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.message for each item
        int layoutForListItem = R.layout.recycler_content_search;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutForListItem, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model)
    {
        Log.v(LOG_TAG, "onBindViewHolder: Called");
        holder.nameTextView.setText(model.getProductName());
        holder.priceTextView.setText(String.valueOf(model.getProductPrice()));

        if (model.getImageUrl() != null)
        {
            ImageView imageView = holder.productImageView;
            Glide.with(imageView.getContext()).load(model.getImageUrl()).into(imageView);
        }
        else
        {
            holder.productImageView.setImageResource(R.mipmap.ic_launcher);
        }
    }
}

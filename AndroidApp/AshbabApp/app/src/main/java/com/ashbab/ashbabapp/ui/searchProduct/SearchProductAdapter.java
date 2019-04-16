package com.ashbab.ashbabapp.ui.searchProduct;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The adapter to populate the recycler view on the Main activity
 */
public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ProductViewHolder>
{
    // Define listener member variable
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener
    {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    void setOnItemClickListener(OnItemClickListener listener)
    {
        SearchProductAdapter.listener = listener;
    }

    private List<Product> productList;

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

    SearchProductAdapter(ArrayList<Product> productList)
    {
        this.productList = productList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public SearchProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        int layoutForListItem = R.layout.recycler_content_search;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutForListItem, parent, false);
        return new ProductViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position)
    {
        Product currentProduct = productList.get(position);

        holder.nameTextView.setText(currentProduct.getProductName());
        holder.priceTextView.setText(String.valueOf(currentProduct.getProductPrice()));

        if (currentProduct.getImageUrl() != null)
        {
            ImageView imageView = holder.productImageView;
            Glide.with(imageView.getContext()).load(currentProduct.getImageUrl()).into(imageView);
        }
        else
        {
            holder.productImageView.setImageResource(R.mipmap.ic_launcher);
        }
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return productList.size();
    }
}

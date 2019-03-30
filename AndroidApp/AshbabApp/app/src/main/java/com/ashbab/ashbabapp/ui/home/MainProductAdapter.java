package com.ashbab.ashbabapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashbab.ashbabapp.R;
import com.ashbab.ashbabapp.data.model.Product;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.ProductViewHolder>
{
    ArrayList<Product> productList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        // data items
        TextView nameTextView;
        TextView priceTextView;
        ImageView productImageView;

        ProductViewHolder(View productView)
        {
            super(productView);

            nameTextView = productView.findViewById(R.id.product_text_view_main);
            priceTextView = productView.findViewById(R.id.price_text_view_main);
            productImageView = productView.findViewById(R.id.product_image_view_main);
        }
    }

    // Constructor
    MainProductAdapter(ArrayList<Product> productList)
    {
        this.productList = productList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        int layoutForListItem = R.layout.product_list_main;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutForListItem, parent, false);
        return new ProductViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position)
    {
        Product currentProduct = productList.get(position);

        holder.nameTextView.setText(currentProduct.getProductName());
        holder.priceTextView.setText(String.valueOf(currentProduct.getProductPrice()));
        holder.productImageView.setImageResource(currentProduct.getImageUrl());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return productList.size();
    }
}

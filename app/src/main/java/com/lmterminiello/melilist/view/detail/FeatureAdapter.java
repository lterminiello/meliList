package com.lmterminiello.melilist.view.detail;

import android.databinding.DataBindingUtil;
import android.databinding.generated.callback.OnClickListener;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lmterminiello.melilist.R;
import com.lmterminiello.melilist.databinding.ProductFeatureItemBinding;
import com.lmterminiello.melilist.model.Attribute;

import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder> {

    public static final int MAX_COLLAPS_ITEMS = 6;

    private List<Attribute> attributes;
    private boolean expand;
    private View.OnClickListener listener;

    public void setAttributes(List<Attribute> attributes, View.OnClickListener listener) {
        this.attributes = attributes;
        this.listener = listener;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductFeatureItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.product_feature_item, parent, false);
        return new FeatureViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {
        holder.productListItemBinding.setAttribure(attributes.get(position));
        holder.productListItemBinding.container.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        if (attributes != null) {
            return attributes.size() <= MAX_COLLAPS_ITEMS || expand ? attributes.size() : MAX_COLLAPS_ITEMS;
        }
        return 0;
    }

    public static class FeatureViewHolder extends RecyclerView.ViewHolder {

        ProductFeatureItemBinding productListItemBinding;

        public FeatureViewHolder(ProductFeatureItemBinding productListItemBinding) {
            super(productListItemBinding.container);
            this.productListItemBinding = productListItemBinding;
        }


    }

    public boolean isExpand() {
        return expand;
    }

    public boolean expandOrCollapse() {
        if (attributes.size() >= MAX_COLLAPS_ITEMS) {
            expand = !expand;
            notifyItemRangeChanged(MAX_COLLAPS_ITEMS, attributes.size());
        }
        return expand;
    }
}

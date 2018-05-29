package com.lmterminiello.melilist.view.list;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lmterminiello.melilist.R;
import com.lmterminiello.melilist.databinding.ProductListItemBinding;
import com.lmterminiello.melilist.databinding.ProgressItemBinding;
import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.utils.Lists;
import com.lmterminiello.melilist.viewmodel.list.ProductListItemViewModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean loading;

    public interface ProductOnClickListener {
        void onClickProduct(Result result);
    }

    private List<Result> resultList;

    private ProductOnClickListener listener;

    public ProductAdapter(ProductOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_LOADING) {
            ProgressItemBinding progressItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                    , R.layout.progress_item, parent, false);
            return new LoadingViewHolder(progressItemBinding);
        }
        ProductListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.product_list_item, parent, false);
        binding.setListener(listener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.productImage.setClipToOutline(true);
        }
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {
            ((ProductViewHolder) holder).bindProduct(resultList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return resultList != null ? resultList.size() : 0;
    }

    public void initLoading() {
        if (!loading && !Lists.isNullOrEmpty(resultList)) {
            resultList.add(null);
            loading = true;
            notifyItemInserted(resultList.size());
        }
    }

    public void dismissLoading() {
        if (loading && !Lists.isNullOrEmpty(resultList)) {
            loading = false;
            resultList.remove(resultList.size()-1);
            notifyItemRemoved(resultList.size() + 1);
        }
    }

    public void setResultList(List<Result> results) {
        if (results != null) {
            if (resultList == null) {
                resultList = results;
                notifyDataSetChanged();
            } else {
                int oldSize = getItemCount();
                resultList.addAll(results);
                notifyItemRangeChanged(oldSize, getItemCount());
            }
        }
    }

    public void clearItems(){
        if (resultList != null) {
            resultList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ProductListItemBinding productListItemBinding;

        public ProductViewHolder(ProductListItemBinding productListItemBinding) {
            super(productListItemBinding.productContainer);
            this.productListItemBinding = productListItemBinding;
        }

        void bindProduct(Result result) {
            if (productListItemBinding.getViewModel() == null) {
                productListItemBinding.setViewModel(
                        new ProductListItemViewModel(result));
            } else {
                productListItemBinding.getViewModel().setResult(result);
            }
        }

    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressItemBinding progressItemBinding;

        public LoadingViewHolder(ProgressItemBinding progressItemBinding) {
            super(progressItemBinding.container);
        }
    }


}

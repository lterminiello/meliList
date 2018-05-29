package com.lmterminiello.melilist.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.lmterminiello.melilist.usecase.product.ProductUseCaseFactory;
import com.lmterminiello.melilist.viewmodel.detail.ProductDetailViewModel;
import com.lmterminiello.melilist.viewmodel.list.ProductListViewModel;

public class ProductViewModelFactory implements ViewModelProvider.Factory {

    private ProductUseCaseFactory productUseCaseFactory;
    private Context context;

    public ProductViewModelFactory(ProductUseCaseFactory productUseCaseFactory, Context context) {
        this.productUseCaseFactory = productUseCaseFactory;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProductListViewModel.class)){
            return (T)new ProductListViewModel(context,productUseCaseFactory.getProductListUseCase());
        } else if (modelClass.isAssignableFrom(ProductDetailViewModel.class)){
            return (T)new ProductDetailViewModel(context,productUseCaseFactory.getProductDetailUseCase());
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}

package com.lmterminiello.melilist.viewmodel.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.lmterminiello.melilist.R;
import com.lmterminiello.melilist.datasource.RemoteProductDataSourceImp;
import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.repository.ProductRepositoryImp;
import com.lmterminiello.melilist.retrofit.RetrofitProductServiceFactory;
import com.lmterminiello.melilist.usecase.DefaultUseCaseCallback;
import com.lmterminiello.melilist.usecase.product.ProductListUseCase;
import com.lmterminiello.melilist.usecase.UseCaseUtils;
import com.lmterminiello.melilist.utils.Lists;

import java.util.List;

public class ProductListViewModel extends ViewModel implements DefaultUseCaseCallback<List<Result>> {

    private ProductListUseCase productListUseCase;
    private Context context;
    private MutableLiveData<List<Result>> productsData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessageData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private ObservableBoolean availabilityItems = new ObservableBoolean(false);
    private ObservableBoolean newSearch = new ObservableBoolean(false);
    private ObservableBoolean infoLabelEnable = new ObservableBoolean(true);
    private ObservableField<String> title = new ObservableField<>();


    public ProductListViewModel(Context context, ProductListUseCase productListUseCase) {
        this.context = context;
        this.productListUseCase = productListUseCase;
        this.productListUseCase.setDefaultUseCaseCallback(this);
        loading.postValue(false);
    }

    public void search(String message) {
        productsData.setValue(null);
        productListUseCase.cancel();
        productListUseCase.resetPage();
        productListUseCase.setProductName(message);
        UseCaseUtils.execute(productListUseCase);
    }

    public void nextPage() {
        if (!loading.getValue() && !productListUseCase.isLastPage()) {
            UseCaseUtils.execute(productListUseCase);
        }
    }

    public ObservableBoolean getNewSearch() {
        return newSearch;
    }

    public ObservableBoolean getAvailabilityItems() {
        return availabilityItems;
    }

    @Override
    public void onStart() {
        loading.setValue(true);
        title.set(productListUseCase.getProductName());
        infoLabelEnable.set(false);
        availabilityItems.set(!Lists.isNullOrEmpty(productsData.getValue()));
        newSearch.set(!availabilityItems.get() && loading.getValue());
    }

    @Override
    public void onSuccess(List<Result> products) {
        if (!Lists.isNullOrEmpty(products)) {
            loading.setValue(false);
            productsData.setValue(products);
            availabilityItems.set(!Lists.isNullOrEmpty(productsData.getValue()));
        } else {
            onError(context.getResources().getString(R.string.errorLabel));
        }
    }

    @Override
    public void onError(String message) {
        loading.setValue(false);
        infoLabelEnable.set(true);
        newSearch.set(false);
        errorMessageData.setValue(message);
    }

    public LiveData<List<Result>> getProducts() {
        return productsData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageData;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public ObservableBoolean getInfoLabelEnable() {
        return infoLabelEnable;
    }

    public ObservableField<String> getTitle() {
        return title;
    }
}

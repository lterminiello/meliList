package com.lmterminiello.melilist.viewmodel.detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lmterminiello.melilist.MeliApp;
import com.lmterminiello.melilist.model.Attribute;
import com.lmterminiello.melilist.model.Picture;
import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.usecase.DefaultUseCaseCallback;
import com.lmterminiello.melilist.usecase.UseCaseUtils;
import com.lmterminiello.melilist.usecase.product.ProductDetailUseCase;
import com.lmterminiello.melilist.usecase.product.ProductListUseCase;
import com.lmterminiello.melilist.utils.Lists;
import com.lmterminiello.melilist.utils.StringUtils;
import com.lmterminiello.melilist.view.detail.FeatureAdapter;

import java.text.NumberFormat;
import java.util.List;

public class ProductDetailViewModel extends ViewModel implements DefaultUseCaseCallback<Result> {

    private ProductDetailUseCase productDetailUseCase;
    private Context context;
    private MutableLiveData<Boolean> finishLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<List<String>> imagersUrl = new MutableLiveData<>();
    private MutableLiveData<List<Attribute>> attributeList = new MutableLiveData<>();
    private Result result;


    public ProductDetailViewModel(Context context, ProductDetailUseCase productDetailUseCase) {
        this.context = context;
        this.productDetailUseCase = productDetailUseCase;
        this.productDetailUseCase.setDefaultUseCaseCallback(this);
    }

    public void execute(String productId) {
        productDetailUseCase.setProductId(productId);
        UseCaseUtils.execute(productDetailUseCase);
    }

    @Override
    public void onStart() {
        finishLoading.setValue(false);
    }

    @Override
    public void onSuccess(Result response) {
        result = response;
        List<String> urls = Lists.newArrayList();
        for (Picture picture:response.getPictures()){
            urls.add(picture.getUrl());
        }
        imagersUrl.setValue(urls);
        attributeList.setValue(result.getAttributes());
        finishLoading.setValue(true);
    }

    @Override
    public void onError(String message) {
        error.setValue(true);
    }
    public boolean getAttributes(){
        return !Lists.isNullOrEmpty(result.getAttributes());
    }

    public boolean getArrowShow(){
        return !Lists.isNullOrEmpty(result.getAttributes()) && result.getAttributes().size() > FeatureAdapter.MAX_COLLAPS_ITEMS;
    }

    public MutableLiveData<Boolean> getFinishLoading() {
        return finishLoading;
    }

    public String getDescription() {
        return result.getDescription();
    }

    public String getTitle() {
        return result.getTitle();
    }

    public String getPrice() {
        String currency = MeliApp.getCurrency(result.getCurrencyId());
        return currency + StringUtils.SPACE + NumberFormat.getInstance().format(result.getPrice());
    }


    public boolean getFreeShipping(){
        return result.getShipping().getFreeShipping();
    }

    public MutableLiveData<List<String>> getImagersUrl() {
        return imagersUrl;
    }

    public void setImagersUrl(MutableLiveData<List<String>> imgersUrl) {
        this.imagersUrl = imgersUrl;
    }

    public MutableLiveData<List<Attribute>> getAttributeList() {
        return attributeList;
    }

    public boolean getAvailabylitiDescription(){
        return StringUtils.isNotBlank(result.getDescription());
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }
}

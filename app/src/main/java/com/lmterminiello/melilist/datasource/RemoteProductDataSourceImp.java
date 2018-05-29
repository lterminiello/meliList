package com.lmterminiello.melilist.datasource;

import com.lmterminiello.melilist.MeliApp;
import com.lmterminiello.melilist.model.Description;
import com.lmterminiello.melilist.model.ProductResponse;
import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.retrofit.RetrofitProductService;
import com.lmterminiello.melilist.retrofit.RetrofitProductServiceFactory;

import io.reactivex.Single;

public class RemoteProductDataSourceImp implements RemoteProductDataSource{

    private static RemoteProductDataSourceImp instance;

    public static RemoteProductDataSourceImp getInstance(){
        if (instance == null){
            instance = new RemoteProductDataSourceImp(RetrofitProductServiceFactory.getInstance().getRetrofitProductService());
        }
        return instance;
    }

    private RetrofitProductService retrofitProductService;

    public RemoteProductDataSourceImp(RetrofitProductService retrofitProductService) {
        this.retrofitProductService = retrofitProductService;
    }

    @Override
    public Single<ProductResponse> getProductResponse(String productName,int totalItemArchived) {
        Single<ProductResponse> observable = retrofitProductService.getProducts(MeliApp.getCurrentSite(),productName,totalItemArchived);
        return observable;
    }

    @Override
    public Single<Result> getProductDetail(String productId) {
        Single<Result> observable = retrofitProductService.getProductDetail(productId);
        return observable;
    }

    @Override
    public Single<Description> getProductDescription(String productId) {
        Single<Description> observable = retrofitProductService.getProductDescription(productId);
        return observable;
    }
}

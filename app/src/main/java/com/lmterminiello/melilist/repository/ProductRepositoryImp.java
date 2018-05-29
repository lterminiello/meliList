package com.lmterminiello.melilist.repository;

import com.lmterminiello.melilist.datasource.RemoteProductDataSource;
import com.lmterminiello.melilist.datasource.RemoteProductDataSourceImp;
import com.lmterminiello.melilist.model.Description;
import com.lmterminiello.melilist.model.ProductResponse;
import com.lmterminiello.melilist.model.Result;

import io.reactivex.Single;

public class ProductRepositoryImp implements ProductRepository {

    private static ProductRepositoryImp instance;

    public static ProductRepositoryImp getInstance(){
        if (instance == null){
            instance = new ProductRepositoryImp(RemoteProductDataSourceImp.getInstance());
        }

        return instance;
    }


    private RemoteProductDataSource remoteProductDataSource;

    public ProductRepositoryImp(RemoteProductDataSource remoteProductDataSource) {
        this.remoteProductDataSource = remoteProductDataSource;
    }

    @Override
    public Single<ProductResponse> getProductResponse(String productName,int totalItemArchived) {
        Single<ProductResponse> productResponseSingle = remoteProductDataSource.getProductResponse(productName,totalItemArchived);
        return productResponseSingle;
    }

    @Override
    public Single<String> getProductDescription(String productId) {
        Single<Description> resultSingle = remoteProductDataSource.getProductDescription(productId);
        return resultSingle.flatMap(description -> Single.just(description.getPlainText()));
    }

    @Override
    public Single<Result> getProductDetail(String productId) {
        Single<Result> resultSingle = remoteProductDataSource.getProductDetail(productId);
        return resultSingle;
    }
}

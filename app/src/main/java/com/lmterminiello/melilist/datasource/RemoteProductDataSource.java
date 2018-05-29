package com.lmterminiello.melilist.datasource;

import com.lmterminiello.melilist.model.Description;
import com.lmterminiello.melilist.model.ProductResponse;
import com.lmterminiello.melilist.model.Result;

import io.reactivex.Single;

public interface RemoteProductDataSource {

    Single<ProductResponse> getProductResponse(String productName,int totalItemArchived);

    Single<Result> getProductDetail(String productId);

    Single<Description> getProductDescription(String productId);


}

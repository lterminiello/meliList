package com.lmterminiello.melilist.repository;


import com.lmterminiello.melilist.model.ProductResponse;
import com.lmterminiello.melilist.model.Result;


import io.reactivex.Single;

public interface ProductRepository {

    Single<ProductResponse> getProductResponse(String productName,int totalItemArchived);

    Single<Result> getProductDetail(String productId);

    Single<String> getProductDescription(String productId);

}

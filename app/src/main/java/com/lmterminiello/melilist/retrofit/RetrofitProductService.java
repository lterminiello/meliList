package com.lmterminiello.melilist.retrofit;


import com.lmterminiello.melilist.model.Description;
import com.lmterminiello.melilist.model.ProductResponse;
import com.lmterminiello.melilist.model.Result;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitProductService {

    @GET("sites/{site}/search")
    Single<ProductResponse> getProducts(@Path("site") String site, @Query("q") String itemName, @Query("offset") int offset);

    @GET("items/{productId}")
    Single<Result> getProductDetail(@Path("productId") String productId);

    @GET("items/{productId}/description")
    Single<Description> getProductDescription(@Path("productId") String productId);
}

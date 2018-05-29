package com.lmterminiello.melilist.usecase.product;

import com.lmterminiello.melilist.repository.ProductRepositoryImp;
import com.lmterminiello.melilist.usecase.product.ProductDetailUseCase;
import com.lmterminiello.melilist.usecase.product.ProductListUseCase;

public class ProductUseCaseFactory {

    public ProductListUseCase getProductListUseCase(){
        return new ProductListUseCase(ProductRepositoryImp.getInstance());
    }

    public ProductDetailUseCase getProductDetailUseCase(){
        return new ProductDetailUseCase(ProductRepositoryImp.getInstance());
    }
}

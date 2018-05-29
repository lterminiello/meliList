package com.lmterminiello.melilist.usecase.product;

import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.repository.ProductRepository;

import com.lmterminiello.melilist.usecase.AbstractPaginateUseCase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductListUseCase extends AbstractPaginateUseCase<List<Result>> {

    private Disposable productListUseCaseDisposable;
    private ProductRepository productRepository;

    private String productName;

    public ProductListUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Disposable doExecute() {
        if (productListUseCaseDisposable == null || productListUseCaseDisposable.isDisposed()) {
            getDefaultUseCaseCallback().onStart();
            productListUseCaseDisposable = productRepository.getProductResponse(productName, getCurrentOffset())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            productResponse -> {
                                calculatePagination(productResponse.getResults().size(), productResponse.getPaging().getTotal());
                                getDefaultUseCaseCallback().onSuccess(productResponse.getResults());
                            },
                            throwable -> getDefaultUseCaseCallback().onError(throwable.getMessage())
                    );
        }
        return productListUseCaseDisposable;
    }


    @Override
    public void cancel() {
        if (productListUseCaseDisposable != null && !productListUseCaseDisposable.isDisposed()) {
            productListUseCaseDisposable.dispose();
        }
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}

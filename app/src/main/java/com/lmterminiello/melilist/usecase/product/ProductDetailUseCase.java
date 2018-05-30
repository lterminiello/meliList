package com.lmterminiello.melilist.usecase.product;

import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.repository.ProductRepository;
import com.lmterminiello.melilist.usecase.AbstractBaseUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailUseCase extends AbstractBaseUseCase<Result>{

    private ProductRepository productRepository;
    private Disposable productDetailUseCaseDisposable;
    private Result result;

    private String productId;

    public ProductDetailUseCase(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Disposable doExecute() {
        if (productDetailUseCaseDisposable == null || productDetailUseCaseDisposable.isDisposed()) {
            getDefaultUseCaseCallback().onStart();
            productDetailUseCaseDisposable = productRepository.getProductDetail(productId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            productDetail -> getDescription(productDetail),
                            throwable -> getDefaultUseCaseCallback().onError(throwable.getMessage())
                    );
        }
        return productDetailUseCaseDisposable;
    }

    public void getDescription(Result result){
        this.result = result;
        productDetailUseCaseDisposable = productRepository.getProductDescription(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        description -> {result.setDescription(description);
                        getDefaultUseCaseCallback().onSuccess(result);},
                        throwable -> getDefaultUseCaseCallback().onSuccess(result)
                );
    }

    @Override
    public void cancel() {
        if (productDetailUseCaseDisposable != null && !productDetailUseCaseDisposable.isDisposed()) {
            productDetailUseCaseDisposable.dispose();
        }
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

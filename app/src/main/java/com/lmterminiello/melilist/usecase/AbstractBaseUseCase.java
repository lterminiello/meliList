package com.lmterminiello.melilist.usecase;

import io.reactivex.disposables.Disposable;

public abstract class AbstractBaseUseCase<T> {

    private DefaultUseCaseCallback<T> defaultUseCaseCallback;

    public abstract Disposable doExecute();
    public abstract void cancel();


    public void setDefaultUseCaseCallback(DefaultUseCaseCallback<T> defaultUseCaseCallback) {
        this.defaultUseCaseCallback = defaultUseCaseCallback;
    }

    protected DefaultUseCaseCallback<T> getDefaultUseCaseCallback() {
        return defaultUseCaseCallback;
    }


}

package com.lmterminiello.melilist.retrofit;

public class RetrofitProductServiceFactory extends AbstractRetrofitServiceFactory {

    private static RetrofitProductServiceFactory instance;

    public static RetrofitProductServiceFactory getInstance(){
        if (instance == null){
            instance = new RetrofitProductServiceFactory();
        }
        return instance;
    }

    public RetrofitProductService getRetrofitProductService() {
        return getRetrofit().create(RetrofitProductService.class);
    }

    @Override
    protected String getBaseUrl() {
        return "https://api.mercadolibre.com";
    }
}

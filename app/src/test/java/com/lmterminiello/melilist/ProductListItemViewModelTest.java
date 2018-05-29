package com.lmterminiello.melilist;



import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.model.Shipping;
import com.lmterminiello.melilist.viewmodel.list.ProductListItemViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductListItemViewModelTest {

    private static final String PRODUCT_TITLE = "Prueba 123";
    private static final double PRODUCT_PRICE = 6000;
    private static final String PRODUCT_CURRENCY = "ARS";
    private static final String PRODUCT_URL_IMAGE ="https://http2.mlstatic.com/D_NQ_NP_606390-MLA27337350114_052018-K.webp";
    private static final boolean PEOPLE_FREE_SHIPPING = true;
    private static final String PRODUCT_EXPECTED_PRICE = "$ 6.000";

    @Test
    public void shouldGetProductTitle() {
        Result result = new Result();
        result.setTitle(PRODUCT_TITLE);
        ProductListItemViewModel viewModel = new ProductListItemViewModel(result);
        assertEquals(result.getTitle(), viewModel.getTitle());
    }


    @Test
    public void shouldGetProductPicture() {
        Result result = new Result();
        result.setThumbnail(PRODUCT_URL_IMAGE);
        ProductListItemViewModel viewModel = new ProductListItemViewModel(result);
        assertEquals(result.getThumbnail(), viewModel.getImageUrl());
    }

    @Test
    public void shouldGetProductFreeShipping() {
        Result result = new Result();
        result.setShipping(Mockito.mock(Shipping.class));
        result.getShipping().setFreeShipping(PEOPLE_FREE_SHIPPING);
        ProductListItemViewModel viewModel = new ProductListItemViewModel(result);
        assertEquals(result.getShipping().getFreeShipping(), viewModel.getFreeShipping());
    }

    @Test
    public void shouldGetProductPrice() {
        Result result = new Result();
        result.setPrice(PRODUCT_PRICE);
        result.setCurrencyId(PRODUCT_CURRENCY);
        ProductListItemViewModel viewModel = new ProductListItemViewModel(result);
        assertEquals(PRODUCT_EXPECTED_PRICE, viewModel.getPrice());
    }

}
package com.lmterminiello.melilist;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.test.mock.MockContext;

import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.usecase.product.ProductListUseCase;
import com.lmterminiello.melilist.utils.StringUtils;
import com.lmterminiello.melilist.viewmodel.list.ProductListViewModel;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductListViewModelTest {

    private static final String ERROR = "Error";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MockContext mockContext;
    private ProductListViewModel viewModel;


    @Before
    public void setUpVieModel() {
        viewModel = new ProductListViewModel(mockContext, Mockito.mock(ProductListUseCase.class));
    }

    @Test
    public void simulateFirstStartUseCase(){
        viewModel.onStart();
        assertTrue(viewModel.isLoading().getValue());
        assertFalse(viewModel.getInfoLabelEnable().get());
        assertFalse(viewModel.getAvailabilityItems().get());
        assertTrue(viewModel.getNewSearch().get());
    }

    @Test
    public void simulateOnFinishFailUseCase(){
        viewModel.onError(ERROR);
        assertFalse(viewModel.isLoading().getValue());
        assertTrue(viewModel.getInfoLabelEnable().get());
        assertFalse(viewModel.getAvailabilityItems().get());
        assertFalse(viewModel.getNewSearch().get());
        assertEquals(ERROR,viewModel.getErrorMessage().getValue());
    }

    @Test
    public void simulateOnFinishSuccessUseCase(){
        List<Result> list = new ArrayList<Result>();
        list.add(new Result());
        viewModel.onSuccess(list);
        assertFalse(viewModel.isLoading().getValue());
        assertTrue(viewModel.getAvailabilityItems().get());
        assertNotEquals(ERROR,viewModel.getErrorMessage().getValue());
    }

    @Ignore
    public void simulateOnFinishEmptyUseCase(){
        List<Result> list = new ArrayList<Result>();
        viewModel.onSuccess(list);
        assertFalse(viewModel.isLoading().getValue());
        assertTrue(viewModel.getInfoLabelEnable().get());
        assertFalse(viewModel.getAvailabilityItems().get());
        assertFalse(viewModel.getNewSearch().get());
        assertEquals(mockContext.getResources().getString(R.string.errorLabel),viewModel.getErrorMessage().getValue());
    }
}

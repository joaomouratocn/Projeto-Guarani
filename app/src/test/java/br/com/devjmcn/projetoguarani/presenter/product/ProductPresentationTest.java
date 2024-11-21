package br.com.devjmcn.projetoguarani.presenter.product;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import static br.com.devjmcn.projetoguarani.presenter.product.ProductContracts.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import br.com.devjmcn.projetoguarani.model.Repository;
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

class ProductPresentationTest {

    @Mock
    private Repository repository;

    @Mock
    private ProductViewInterface productViewInterface;

    private ProductPresentation productPresentation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        productPresentation = new ProductPresentation(repository);
        productPresentation.attachView(productViewInterface);
    }

    @AfterEach
    void tearDown() {
        RxAndroidPlugins.reset();
    }

    @Test
    void loadProductStatus_shouldLoadSpinner_whenStatusLoadSucceeds() {
        // Given
        when(repository.getProductStatus()).thenReturn(Observable.just(Collections.emptyList()));

        // When
        productPresentation.loadProductStatus();

        // Then
        verify(productViewInterface).showLoad(true);
        verify(productViewInterface).loadSpinner(Collections.emptyList());
        verify(productViewInterface).showLoad(false);
        verifyNoMoreInteractions(productViewInterface);
    }

    @Test
    void loadProductStatus_shouldShowToast_whenStatusLoadFails() {
        // Given
        String errorMessage = "Status load failed";
        when(repository.getProductStatus()).thenReturn(Observable.error(new Exception(errorMessage)));

        // When
        productPresentation.loadProductStatus();

        // Then
        verify(productViewInterface).showLoad(true);
        verify(productViewInterface).showToast(errorMessage);
        verify(productViewInterface).showLoad(false);
        verifyNoMoreInteractions(productViewInterface);
    }

    @Test
    void searchProduct_shouldSetProductsAdapter_whenSearchSucceeds() {
        // Given
        String search = "Product1";
        String selectedStatus = "Available";
        when(repository.getProductsByName(selectedStatus, search))
                .thenReturn(Observable.just(Collections.emptyList()));

        // When
        productPresentation.searchProduct(search, selectedStatus);

        // Then
        verify(productViewInterface).showLoad(true);
        verify(productViewInterface).setProductsAdapter(Collections.emptyList());
        verify(productViewInterface).showLoad(false);
        verifyNoMoreInteractions(productViewInterface);
    }

    @Test
    void searchProduct_shouldShowToast_whenSearchFails() {
        // Given
        String search = "Product1";
        String selectedStatus = "Available";
        String errorMessage = "Search failed";
        when(repository.getProductsByName(selectedStatus, search))
                .thenReturn(Observable.error(new Exception(errorMessage)));

        // When
        productPresentation.searchProduct(search, selectedStatus);

        // Then
        verify(productViewInterface).showLoad(true);
        verify(productViewInterface).showToast(errorMessage);
        verify(productViewInterface).showLoad(false);
        verifyNoMoreInteractions(productViewInterface);
    }

    @Test
    void detachView_shouldAvoidInteractionsWithView() {
        // Given
        productPresentation.attachView(productViewInterface);

        // When
        productPresentation.detachView();

        // Then
        productPresentation.loadProductStatus();
        productPresentation.searchProduct("Product1", "Available");
        verifyNoInteractions(productViewInterface);
    }
}

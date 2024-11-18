package br.com.devjmcn.projetoguarani.presenter.product;

import static org.mockito.Mockito.*;

import br.com.devjmcn.projetoguarani.model.Repository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

class ProductPresentationTest {

    @Mock
    private Repository repository;

    @Mock
    private ProductContracts.ProductViewInterface productViewInterface;

    private ProductPresentation productPresentation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        productPresentation = new ProductPresentation(repository);
        productPresentation.attachView(productViewInterface);
    }

    @Test
    void loadProductStatus_shouldLoadSpinner_whenStatusLoadSucceeds() {
        // Arrange
        when(repository.getProductStatus()).thenReturn(Flowable.just(Collections.emptyList()));

        // Act
        productPresentation.loadProductStatus();

        // Assert
        verify(productViewInterface).showLoad(true);
        verify(productViewInterface).loadSpinner(Collections.emptyList());
        verify(productViewInterface).showLoad(false);
        verifyNoMoreInteractions(productViewInterface);
    }

    @Test
    void loadProductStatus_shouldShowToast_whenStatusLoadFails() {
        // Arrange
        String errorMessage = "Status load failed";
        when(repository.getProductStatus()).thenReturn(Flowable.error(new Exception(errorMessage)));

        // Act
        productPresentation.loadProductStatus();

        // Assert
        verify(productViewInterface).showLoad(true);
        verify(productViewInterface).showToast(errorMessage);
        verify(productViewInterface).showLoad(false);
        verifyNoMoreInteractions(productViewInterface);
    }

    @Test
    void searchProduct_shouldSetProductsAdapter_whenSearchSucceeds() {
        // Arrange
        String search = "Product1";
        String selectedStatus = "Available";
        when(repository.getProductsByName(selectedStatus, search))
                .thenReturn(Flowable.just(Collections.emptyList()));

        // Act
        productPresentation.searchProduct(search, selectedStatus);

        // Assert
        verify(productViewInterface).showLoad(true);
        verify(productViewInterface).setProductsAdapter(Collections.emptyList());
        verify(productViewInterface).showLoad(false);
        verifyNoMoreInteractions(productViewInterface);
    }

    @Test
    void searchProduct_shouldShowToast_whenSearchFails() {
        // Arrange
        String search = "Product1";
        String selectedStatus = "Available";
        String errorMessage = "Search failed";
        when(repository.getProductsByName(selectedStatus, search))
                .thenReturn(Flowable.error(new Exception(errorMessage)));

        // Act
        productPresentation.searchProduct(search, selectedStatus);

        // Assert
        verify(productViewInterface).showLoad(true);
        verify(productViewInterface).showToast(errorMessage);
        verify(productViewInterface).showLoad(false);
        verifyNoMoreInteractions(productViewInterface);
    }

    @Test
    void detachView_shouldAvoidInteractionsWithView() {
        // Act
        productPresentation.detachView();

        // Assert
        productPresentation.loadProductStatus(); // Deve evitar interações com a View
        productPresentation.searchProduct("Product1", "Available");
        verifyNoInteractions(productViewInterface);
    }
}

package br.com.devjmcn.projetoguarani.presenter.consultClient;

import static org.mockito.Mockito.*;

import br.com.devjmcn.projetoguarani.model.Repository;
import br.com.devjmcn.projetoguarani.model.models.Client;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

class ConsultPresenterTest {

    @Mock
    private Repository repository;

    @Mock
    private ConsultClientContracts.ConsultClientView consultClientView;

    private ConsultPresenter consultPresenter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        consultPresenter = new ConsultPresenter(repository);
        consultPresenter.attachView(consultClientView);
    }

    @Test
    void searchClient_shouldSetClientsAdapter_whenSearchSucceeds() {
        // Arrange
        String searchName = "John";
        String typeSearch = "name";
        when(repository.searchClient(searchName, typeSearch))
                .thenReturn(Flowable.just(Collections.emptyList()));

        // Act
        consultPresenter.searchClient(searchName, typeSearch);

        // Assert
        verify(consultClientView).showLoad(true);
        verify(consultClientView).setClientsAdapter(Collections.emptyList());
        verify(consultClientView).showLoad(false);
        verifyNoMoreInteractions(consultClientView);
    }

    @Test
    void searchClient_shouldShowToast_whenSearchFails() {
        // Arrange
        String searchName = "John";
        String typeSearch = "name";
        String errorMessage = "Search failed";
        when(repository.searchClient(searchName, typeSearch))
                .thenReturn(Flowable.error(new Exception(errorMessage)));

        // Act
        consultPresenter.searchClient(searchName, typeSearch);

        // Assert
        verify(consultClientView).showLoad(true);
        verify(consultClientView).showToast(errorMessage);
        verify(consultClientView).showLoad(false);
        verifyNoMoreInteractions(consultClientView);
    }

    @Test
    void deleteClient_shouldCallDeleteSuccess_whenDeleteSucceeds() {
        // Arrange
        Client client = new Client(); // Configure o objeto conforme necessário
        when(repository.deleteClient(client)).thenReturn(Completable.complete());

        // Act
        consultPresenter.deleteClient(client);

        // Assert
        verify(consultClientView).showLoad(true);
        verify(consultClientView).deleteSuccess();
        verify(consultClientView).showLoad(false);
        verifyNoMoreInteractions(consultClientView);
    }

    @Test
    void deleteClient_shouldShowToast_whenDeleteFails() {
        // Arrange
        Client client = new Client(); // Configure o objeto conforme necessário
        String errorMessage = "Delete failed";
        when(repository.deleteClient(client)).thenReturn(Completable.error(new Exception(errorMessage)));

        // Act
        consultPresenter.deleteClient(client);

        // Assert
        verify(consultClientView).showLoad(true);
        verify(consultClientView).showToast(errorMessage);
        verify(consultClientView).showLoad(false);
        verifyNoMoreInteractions(consultClientView);
    }

    @Test
    void detachView_shouldAvoidInteractionWithView() {
        // Act
        consultPresenter.detachView();

        // Assert
        consultPresenter.searchClient("John", "name"); // Deve evitar interações com a View
        consultPresenter.deleteClient(new Client());   // Deve evitar interações com a View
        verifyNoInteractions(consultClientView);
    }
}

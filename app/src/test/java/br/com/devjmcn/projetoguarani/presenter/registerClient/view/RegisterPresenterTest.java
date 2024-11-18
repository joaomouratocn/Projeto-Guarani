package br.com.devjmcn.projetoguarani.presenter.registerClient.view;

import static org.mockito.Mockito.*;

import br.com.devjmcn.projetoguarani.model.Repository;
import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterContracts;
import br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterPresenter;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RegisterPresenterTest {

    @Mock
    private Repository repository;

    @Mock
    private RegisterContracts.RegisterViewInterface registerViewInterface;

    private RegisterPresenter registerPresenter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        registerPresenter = new RegisterPresenter(repository);
        registerPresenter.attachView(registerViewInterface);
    }

    @Test
    void editClient_shouldCallResultSuccess_whenUpdateSucceeds() {
        // Arrange
        Client client = new Client(); // Configure o objeto conforme necessário
        when(repository.updateClient(client)).thenReturn(Completable.complete());

        // Act
        registerPresenter.editClient(client);

        // Assert
        verify(registerViewInterface).resultSuccess();
        verifyNoMoreInteractions(registerViewInterface);
    }

    @Test
    void editClient_shouldShowToast_whenUpdateFails() {
        // Arrange
        Client client = new Client(); // Configure o objeto conforme necessário
        String errorMessage = "Update failed";
        when(repository.updateClient(client)).thenReturn(Completable.error(new Exception(errorMessage)));

        // Act
        registerPresenter.editClient(client);

        // Assert
        verify(registerViewInterface).showToast(errorMessage);
        verifyNoMoreInteractions(registerViewInterface);
    }

    @Test
    void insertClient_shouldCallResultSuccess_whenInsertSucceeds() {
        // Arrange
        Client client = new Client(); // Configure o objeto conforme necessário
        when(repository.insertClient(client)).thenReturn(Completable.complete());

        // Act
        registerPresenter.insertClient(client);

        // Assert
        verify(registerViewInterface).resultSuccess();
        verifyNoMoreInteractions(registerViewInterface);
    }

    @Test
    void insertClient_shouldShowToast_whenInsertFails() {
        // Arrange
        Client client = new Client(); // Configure o objeto conforme necessário
        String errorMessage = "Insert failed";
        when(repository.insertClient(client)).thenReturn(Completable.error(new Exception(errorMessage)));

        // Act
        registerPresenter.insertClient(client);

        // Assert
        verify(registerViewInterface).showToast(errorMessage);
        verifyNoMoreInteractions(registerViewInterface);
    }

    @Test
    void detachView_shouldSetViewToNull() {
        // Act
        registerPresenter.detachView();

        // Assert
        registerPresenter.editClient(new Client()); // Deve evitar interações com a View
        verifyNoInteractions(registerViewInterface);
    }
}

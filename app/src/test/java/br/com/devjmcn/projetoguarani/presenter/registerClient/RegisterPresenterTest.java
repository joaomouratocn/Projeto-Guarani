package br.com.devjmcn.projetoguarani.presenter.registerClient;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.devjmcn.projetoguarani.model.Repository;
import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.presenter.registerClient.RegisterContracts.RegisterViewInterface;
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterPresenterTest {

    @Mock
    private Repository repository;

    @Mock
    private RegisterViewInterface registerViewInterface;

    private RegisterPresenter registerPresenter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        registerPresenter = new RegisterPresenter(repository);
        registerPresenter.attachView(registerViewInterface);
    }

    @Test
    void testInsertClient_shouldCallResultSuccessWhenClientInserted() {
        // Given
        Client newClient = createClient();
        when(repository.insertClient(newClient))
                .thenReturn(Observable.just(true));

        // When
        registerPresenter.insertClient(newClient);

        // Then
        verify(registerViewInterface).resultSuccess();
    }

    @Test
    void testInsertClient_shouldShowErrorWhenInsertFails() {
        // Given
        Client newClient = createClient();
        when(repository.insertClient(newClient))
                .thenReturn(Observable.error(new Throwable("Insert failed")));

        // When
        registerPresenter.insertClient(newClient);

        // Then
        verify(registerViewInterface).showToast("Insert failed");
    }

    @Test
    void testEditClient_shouldCallResultSuccessWhenClientEdited() {
        // Given
        Client editedClient = createClient();
        when(repository.updateClient(editedClient))
                .thenReturn(Observable.just(true));

        // When
        registerPresenter.editClient(editedClient);

        // Then
        verify(registerViewInterface).resultSuccess();
    }

    @Test
    void testEditClient_shouldShowErrorWhenEditFails() {
        // Given
        Client editedClient = createClient();
        when(repository.updateClient(editedClient))
                .thenReturn(Observable.error(new Throwable("Edit failed")));

        // When
        registerPresenter.editClient(editedClient);

        // Then
        verify(registerViewInterface).showToast("Edit failed");
    }

    @Test
    void testDetachView_shouldAvoidInteractionsWithView() {
        // Given
        registerPresenter.detachView();

        // When
        Client newClient = createClient();
        registerPresenter.insertClient(newClient);

        // Then
        verifyNoInteractions(registerViewInterface);
    }

    private Client createClient(){
        return new Client(
                "1",
                "reason",
                "cnpjCpf",
                "address",
                "number",
                "complement",
                "cep",
                "district",
                "dtRegister",
                "fantasyName",
                "email",
                "secEmail"
        );
    }
}

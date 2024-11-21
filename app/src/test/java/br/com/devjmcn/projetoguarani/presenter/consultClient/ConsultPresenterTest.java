package br.com.devjmcn.projetoguarani.presenter.consultClient;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import br.com.devjmcn.projetoguarani.model.Repository;
import br.com.devjmcn.projetoguarani.model.models.Client;
import br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultClientContracts.ConsultClientViewInterface;
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConsultPresenterTest {

    @Mock
    private Repository repository;

    @Mock
    private ConsultClientViewInterface consultClientViewInterface;

    private ConsultPresenter consultPresenter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        consultPresenter = new ConsultPresenter(repository);
        consultPresenter.attachView(consultClientViewInterface);
    }

    @AfterEach
    void tearDown() {
        RxAndroidPlugins.reset();
    }

    @Test
    void testSearchClient_shouldDisplayClientsWhenSuccessful() {
        // Given
        String typeSearch = "NAME";
        Client client = createClient();
        when(repository.searchClient(client.getReason(), typeSearch))
                .thenReturn(Observable.just(Collections.singletonList(client)));

        // When
        consultPresenter.searchClient(client.getReason(), typeSearch);

        // Then
        verify(consultClientViewInterface).showLoad(true);
        verify(consultClientViewInterface).setClientsAdapter(Collections.singletonList(client));
        verify(consultClientViewInterface).showLoad(false);
    }

    @Test
    void testSearchClient_shouldShowErrorWhenFailed() {
        // Given
        String searchName = "João";
        String typeSearch = "NAME";
        when(repository.searchClient(searchName, typeSearch))
                .thenReturn(Observable.error(new Throwable("Error occurred")));

        // When
        consultPresenter.searchClient(searchName, typeSearch);

        // Then
        verify(consultClientViewInterface).showLoad(true);
        verify(consultClientViewInterface).showToast("Error occurred");
        verify(consultClientViewInterface).showLoad(false);
    }

    @Test
    void testDeleteClient_shouldCallSuccessCallbackWhenDeleted() {
        // Given
        Client client = createClient();
        when(repository.deleteClient(client))
                .thenReturn(Observable.just(true));

        // When
        consultPresenter.deleteClient(client);

        // Then
        verify(consultClientViewInterface).showLoad(true);
        verify(consultClientViewInterface).deleteSuccess();
        verify(consultClientViewInterface).showLoad(false);
    }

    @Test
    void testDeleteClient_shouldShowErrorWhenDeleteFails() {
        // Given
        Client client = createClient();
        when(repository.deleteClient(client))
                .thenReturn(Observable.error(new Throwable("Delete failed")));

        // When
        consultPresenter.deleteClient(client);

        // Then
        verify(consultClientViewInterface).showLoad(true);
        verify(consultClientViewInterface).showToast("Delete failed");
        verify(consultClientViewInterface).showLoad(false);
    }

    @Test
    void testDetachView_shouldAvoidInteractionsWithView() {
        // Given
        consultPresenter.detachView();

        // When
        consultPresenter.searchClient("John", "name");

        // Then
        verifyNoInteractions(consultClientViewInterface);
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

package br.com.devjmcn.projetoguarani.presenter.consultClient;

import static br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultClientContracts.ConsultClientPresenterInterface;
import static br.com.devjmcn.projetoguarani.presenter.consultClient.ConsultClientContracts.ConsultClientViewInterface;

import javax.inject.Inject;

import br.com.devjmcn.projetoguarani.model.Repository;
import br.com.devjmcn.projetoguarani.model.models.Client;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConsultPresenter implements ConsultClientPresenterInterface {
    private ConsultClientViewInterface consultClientViewInterface;
    private Disposable disposable;
    private final Repository repository;

    @Inject
    public ConsultPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void attachView(ConsultClientContracts.ConsultClientViewInterface consultClientViewInterface) {
        this.consultClientViewInterface = consultClientViewInterface;
    }

    @Override
    public void detachView() {
        consultClientViewInterface = null;
    }

    @Override
    public void searchClient(String searchName, String typeSearch) {
        if (viewIsNull()){return;}
        consultClientViewInterface.showLoad(true);
        disposable = repository.searchClient(searchName, typeSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> consultClientViewInterface.showLoad(false))
                .subscribe(
                        clients -> {
                            consultClientViewInterface.setClientsAdapter(clients);
                        },
                        throwable -> {
                            consultClientViewInterface.showToast(throwable.getMessage());
                        }
                );
    }

    @Override
    public void deleteClient(Client client) {
        if (viewIsNull()){return;}
        consultClientViewInterface.showLoad(true);
        disposable = repository.deleteClient(client)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> consultClientViewInterface.showLoad(false))
                .subscribe(
                        result -> {
                            consultClientViewInterface.deleteSuccess();
                        },
                        throwable -> {
                            consultClientViewInterface.showToast(throwable.getMessage());
                        }
                );

    }

    private Boolean viewIsNull(){
        return consultClientViewInterface == null;
    }
}
